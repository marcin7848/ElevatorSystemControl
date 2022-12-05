package com.elevatorsystemcontrol.service;

import com.elevatorsystemcontrol.exceptions.FieldNameAndMessageException;
import com.elevatorsystemcontrol.exceptions.MessageException;
import com.elevatorsystemcontrol.model.Elevator;
import com.elevatorsystemcontrol.model.ElevatorFloor;
import com.elevatorsystemcontrol.repository.ElevatorRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ElevatorService implements IElevatorService  {

    /**
     * MAX_ELEVATORS  define the maximum number of elevators controlled by the system
     */
    private final int MAX_ELEVATORS = 16;
    final ElevatorRepository elevatorRepository;

    @Autowired
    public ElevatorService(ElevatorRepository elevatorRepository) {
        this.elevatorRepository = elevatorRepository;
    }

    /**
     * Gets all elevators from the database, no params available
     * @return  A List<Elevators> with all elevators
     */
    public List<Elevator> getAll(){
        return this.elevatorRepository.findAll();
    }

    /**
     * Get elevator object of the given ID
     * @param id                    elevator's Long ID
     * @exception MessageException  throws ResponseEntity exception if the elevator with the specified ID doesn't exist
     * @return                      Elevator object
     */
    public Elevator getElevator(Long id){
        return elevatorRepository.findById(id)
                .orElseThrow(() -> new MessageException("Elevator does not exist!"));
    }

    /**
     * Adds a new elevator to the system, no params available
     * After adding the elevator to the database, it starts a thread for managing this elevator
     * @exception MessageException  throws ResponseEntity exception if the database already contains a maximum number of elevators
     *                              defined in MAX_ELEVATORS
     * @return                      Elevator object created in the database
     */
    public Elevator addNewElevator(){
        if(this.elevatorRepository.count() >= this.MAX_ELEVATORS){
            throw new MessageException("You cannot add more elevators! Maximum reached.");
        }
        Elevator elevator = this.elevatorRepository.save(new Elevator());
        Executors.newSingleThreadExecutor().submit(() -> this.manageThreadForElevator(elevator));

        return elevator;
    }

    /**
     * Deletes the elevator of the given ID
     * @param id                        elevator's Long ID to delete
     * @exception MessageException      throws an exception if List<SelectedFloor> is not empty
     * @return                          true if the elevator has been deleted, false if it has not
     */
    public boolean deleteElevator(Long id){
        Elevator elevator = this.getElevator(id);
        if(elevator.getSelectedFloors().size() > 0){
            throw new MessageException("The elevator must stop and " +
                    "selected floors list must be empty to delete the elevator");
        }
        try{
            this.elevatorRepository.deleteElevatorById(elevator.getId());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Manages the asynchronous thread for an elevator. Exceptions are ignored, after catching the thread for
     * the particular elevator stops.
     * The currentStatus of the Elevator can be:
     * - 0          Waiting on the floor: {elevator.currentFloor}
     * - 1          Going to the floor: {elevator.selectedFloors.get(0)}
     * - 2          Opening doors
     * - 3          Closing doors
     * Note that after a change in the current status there is a delay before changing it again.
     * For the currentStatus==1 the delay is calculated as follows:
     * 3 seconds per floor between the current floor (startFloor) and the selected floor (endFloor)
     * if the selected floor was changed during elevator's movement, changing current floor stops
     * and status is not updated until the next iteration
     *
     * @param el    The elevator object to start and manage the thread for it
     */
    @Async
    public void manageThreadForElevator(Elevator el){
        try {
            Elevator elevator = this.getElevator(el.getId());
            switch (elevator.getCurrentStatus()) {
                case 0 -> {
                    if (elevator.getSelectedFloors().size() > 0) {
                        setElevatorStatus(1, elevator);
                    } else {
                        Thread.sleep(500);
                    }
                }
                case 1 -> {
                    elevator = this.sortSelectedFloors(elevator);
                    int startFloor = elevator.getCurrentFloor();
                    int endFloor = elevator.getSelectedFloors().get(0).getFloor();
                    int[] floorsBetween = IntStream.range(startFloor, endFloor).toArray();

                    if(startFloor > endFloor){
                        startFloor = endFloor+1;
                        endFloor = elevator.getCurrentFloor()+1;
                        floorsBetween = IntStream.range(startFloor, endFloor).boxed().
                                sorted(Comparator.reverseOrder()).mapToInt(i -> i).toArray();
                    }

                    Long selectedElevatorFloorId = elevator.getSelectedFloors().get(0).getId();

                    for(int currFloor : floorsBetween){
                        elevator.setCurrentFloor(currFloor);
                        this.elevatorRepository.save(elevator);
                        Thread.sleep(3000L);

                        elevator = this.sortSelectedFloors(elevator);

                        if(!selectedElevatorFloorId.equals(this.getElevator(elevator.getId()).getSelectedFloors().get(0).getId())){
                            break;
                        }
                    }

                    if(selectedElevatorFloorId.equals(this.getElevator(elevator.getId()).getSelectedFloors().get(0).getId())){
                        setElevatorStatus(2, elevator);
                    }
                }
                case 2 -> {
                    elevator.setCurrentFloor(elevator.getSelectedFloors().get(0).getFloor());
                    this.elevatorRepository.save(elevator);
                    Thread.sleep(5000);
                    setElevatorStatus(3, elevator);
                }
                case 3 -> {
                    Thread.sleep(3000);
                    if (elevator.getSelectedFloors().size() > 0) {
                        int removeFloor = elevator.getSelectedFloors().get(0).getFloor();
                        elevator.getSelectedFloors().removeAll(
                                elevator.getSelectedFloors().stream().filter(x -> x.getFloor() == removeFloor).toList());
                    }
                    setElevatorStatus(0, elevator);
                }
            }

            this.manageThreadForElevator(elevator);

        } catch (InterruptedException | MessageException ignored) {}
    }

    /**
     * Allows to set and save in the database the current status for an elevator
     * @param currentStatus The status of the elevator to be saved as the next current one
     * @param elevator      Elevator object for which the status should change
     */
    private void setElevatorStatus(int currentStatus, Elevator elevator){
        elevator.setCurrentStatus(currentStatus);
        this.elevatorRepository.save(elevator);
    }


    /**
     * Sorts the selectedFloors for the specified elevator object
     * Managing the next selected floor for an elevator works as follows:
     * - if there is no selected floors, the elevator waits on the last floor where it was
     * - if there is only 1 selected floor, the elevator should go there
     * - is there are 2 or more selected floors, the algorithm proceeds as follows:
     *  for the selected floors inside the elevator:
     *  - go to the first selected floor unless there is another selected floor chosen INSIDE the elevator BETWEEN
     *    the current floor and the current selected floor - if so: change the current selected floor to the one in-between
     *  for the selected floors outside the elevator:
     *  - go to the first selected floor unless there is another selected floor chosen OUTSIDE the elevator BETWEEN
     *    the current floor and the current selected floor and the DIRECTION of the chosen floor outside the elevator is
     *    the SAME as the current elevator movement - if so: change the current selected floor to the one in between
     *
     * @param elevator  Elevator object for which selectedFloors are sorted
     * @return          Updated elevator object with selectedFloors sorted by position field
     */
    private Elevator sortSelectedFloors(Elevator elevator){
        Elevator updatedElevator = this.getElevator(elevator.getId());
        if(updatedElevator.getSelectedFloors().size() < 1)
            return elevator;

        int direction = updatedElevator.getCurrentFloor() <= updatedElevator.getSelectedFloors().get(0).getFloor()? 1 : -1;

        if(direction == 1)
            updatedElevator.getSelectedFloors().sort(Comparator.comparing(ElevatorFloor::getFloor));
        else
            updatedElevator.getSelectedFloors().sort(Comparator.comparing(ElevatorFloor::getFloor).reversed());

        List<Long> elevatorFloorIdsBetween = new ArrayList<>();
        updatedElevator.getSelectedFloors().forEach(tf -> {
            if(direction == 1 && tf.getFloor() >= updatedElevator.getCurrentFloor()
                    && (tf.getDirection() == 0 || tf.getDirection() == direction)){
                elevatorFloorIdsBetween.add(tf.getId());
            }
            else if(direction == -1 && tf.getFloor() <= updatedElevator.getCurrentFloor()
                    && (tf.getDirection() == 0 || tf.getDirection() == direction)){
                elevatorFloorIdsBetween.add(tf.getId());
            }
        });

        int[] order = {0, elevatorFloorIdsBetween.size()};
        updatedElevator.getSelectedFloors().forEach(selectedFloor -> {
            if(elevatorFloorIdsBetween.contains(selectedFloor.getId())){
                selectedFloor.setPosition(order[0]);
                order[0] = order[0]+1;
            } else {
                selectedFloor.setPosition(order[1]);
                order[1] = order[1]+1;
            }
        });

        Elevator resortedElevator = this.elevatorRepository.save(updatedElevator);
        resortedElevator.getSelectedFloors().sort(Comparator.comparing(ElevatorFloor::getPosition));
        return resortedElevator;
    }

}
