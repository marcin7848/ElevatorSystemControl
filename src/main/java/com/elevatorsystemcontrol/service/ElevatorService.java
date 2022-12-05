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
     * @exception MessageException      throws an exception if List<TargetFloor> is not empty
     * @return                          true if the elevator has been deleted, false if it has not
     */
    public boolean deleteElevator(Long id){
        Elevator elevator = this.getElevator(id);
        if(elevator.getTargetFloors().size() > 0){
            throw new MessageException("The elevator must stop and " +
                    "target floors list must be empty to delete the elevator");
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
     * - 1          Going to the floor: {elevator.targetFloors.get(0)}
     * - 2          Opening doors
     * - 3          Closing doors
     * Note that after a change in the current status there is a delay before changing it again.
     * For the currentStatus==1 the delay is calculated as follows:
     * 3 seconds per floor between the current floor (startFloor) and the target floor (endFloor)
     * if the target floor was changed during elevator's movement, changing current floor stops
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
                    if (elevator.getTargetFloors().size() > 0) {
                        setElevatorStatus(1, elevator);
                    } else {
                        Thread.sleep(500);
                    }
                }
                case 1 -> {
                    elevator = this.sortTargetFloors(elevator);
                    int startFloor = elevator.getCurrentFloor();
                    int endFloor = elevator.getTargetFloors().get(0).getFloor();
                    int[] floorsBetween = IntStream.range(startFloor, endFloor).toArray();

                    if(startFloor > endFloor){
                        startFloor = endFloor+1;
                        endFloor = elevator.getCurrentFloor()+1;
                        floorsBetween = IntStream.range(startFloor, endFloor).boxed().
                                sorted(Comparator.reverseOrder()).mapToInt(i -> i).toArray();
                    }

                    Long targetElevatorFloorId = elevator.getTargetFloors().get(0).getId();

                    for(int currFloor : floorsBetween){
                        elevator.setCurrentFloor(currFloor);
                        this.elevatorRepository.save(elevator);
                        Thread.sleep(3000L);

                        elevator = this.sortTargetFloors(elevator);

                        if(!targetElevatorFloorId.equals(this.getElevator(elevator.getId()).getTargetFloors().get(0).getId())){
                            break;
                        }
                    }

                    if(targetElevatorFloorId.equals(this.getElevator(elevator.getId()).getTargetFloors().get(0).getId())){
                        setElevatorStatus(2, elevator);
                    }
                }
                case 2 -> {
                    elevator.setCurrentFloor(elevator.getTargetFloors().get(0).getFloor());
                    this.elevatorRepository.save(elevator);
                    Thread.sleep(5000);
                    setElevatorStatus(3, elevator);
                }
                case 3 -> {
                    Thread.sleep(3000);
                    if (elevator.getTargetFloors().size() > 0) {
                        elevator.getTargetFloors().remove(0);
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
     * Sorts target floors for the specified elevator
     * @param elevator Elevator object
     */
    private Elevator sortTargetFloors(Elevator elevator){
        Elevator updatedElevator = this.getElevator(elevator.getId());
        if(updatedElevator.getTargetFloors().size() < 1)
            return elevator;

        ElevatorFloor elevatorFloor = this.getClosestElevatorFloorInElevator(updatedElevator);
        if(elevatorFloor == null)
            return elevator;

        int[] order = {2};
        updatedElevator.getTargetFloors().forEach(targetFloor -> {
            if(targetFloor.getId().equals(elevatorFloor.getId())){
                targetFloor.setPosition(0);
            } else if (targetFloor.getId().equals(updatedElevator.getTargetFloors().get(0).getId())) {
                targetFloor.setPosition(1);
            } else{
                targetFloor.setPosition(order[0]);
                order[0] = order[0] + 1;
            }
        });

        return this.elevatorRepository.save(updatedElevator);
    }

    private ElevatorFloor getClosestElevatorFloorInElevator(Elevator elevator){
        if(elevator.getTargetFloors().isEmpty())
            return null;

        int currentFloor = elevator.getCurrentFloor();
        ElevatorFloor currentTargetFloor = elevator.getTargetFloors().get(0);

        int range = (currentFloor + currentTargetFloor.getFloor()) / 2;
        List<ElevatorFloor> elevatorFloorsInRange = elevator.getTargetFloors().stream().filter(ef ->
                Math.abs(ef.getFloor() - range) <= Math.abs(currentFloor - range)
                && !ef.getId().equals(elevator.getTargetFloors().get(0).getId())
        ).toList();

        if(elevatorFloorsInRange.isEmpty())
            return null;

        return elevator.getTargetFloors().stream().min(
                Comparator.comparingInt(f -> Math.abs(f.getFloor() - currentFloor))
        ).get();

    }

}
