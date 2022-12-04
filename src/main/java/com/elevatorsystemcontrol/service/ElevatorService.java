package com.elevatorsystemcontrol.service;

import com.elevatorsystemcontrol.exceptions.MessageException;
import com.elevatorsystemcontrol.model.Elevator;
import com.elevatorsystemcontrol.repository.ElevatorRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ElevatorService implements IElevatorService  {

    //@param MAX_ELEVATORS  define the maximum number of elevators controlled by the system
    private final int MAX_ELEVATORS = 16;
    final ElevatorRepository elevatorRepository;

    @Autowired
    public ElevatorService(ElevatorRepository elevatorRepository) {
        this.elevatorRepository = elevatorRepository;
    }

    /*
     * Gets all elevators from the database, no params available
     * @return  A List<Elevators> with all elevators
     * */
    public List<Elevator> getAll(){
        return (List<Elevator>) this.elevatorRepository.findAll();
    }

    /*
     * Get elevator object of the given ID
     * @param id   elevator's Long ID
     * @exception       throws ResponseEntity exception if the elevator with the specified ID doesn't exist
     * @return          Elevator object
     * */
    public Elevator getElevator(Long id){
        return elevatorRepository.findById(id)
                .orElseThrow(() -> new MessageException("Elevator does not exist!"));
    }

    /*
    * Adds a new elevator to the system, no params available
    * After adding the elevator to the database, it starts a thread for managing this elevator
    * @exception    throws ResponseEntity exception if the database already contains a maximum number of elevators
    *               defined in MAX_ELEVATORS
    * @return       Elevator object created in the database
    * */
    public Elevator addNewElevator(){
        if(this.elevatorRepository.count() >= this.MAX_ELEVATORS){
            throw new MessageException("You cannot add more elevators! Maximum reached.");
        }
        Elevator elevator = this.elevatorRepository.save(new Elevator());
        Executors.newSingleThreadExecutor().submit(() -> this.createThreadForElevator(elevator));

        return elevator;
    }

    /*
     * Deletes the elevator of the given ID
     * @param id   elevator's Long ID to delete
     * @exception       throws exception in getElevator method if the elevator to delete doesn't exist
     * @return boolean  true if the elevator has been deleted, false if it has not
     * */
    public boolean deleteElevator(Long id){
        Elevator elevator = this.getElevator(id);
        try{
            this.elevatorRepository.delete(elevator);
            this.getElevator(id);
            return false;
        }catch (Exception e){
            return true;
        }
    }

    @Async
    public void createThreadForElevator(Elevator elevator){
        //TODO: Create one thread for each elevator
        //Updating each elevator inside every thread separately

        try {
            Thread.sleep((int)(Math.random()*5000+1));
            System.out.println("test " + elevator.getId());
            this.createThreadForElevator(elevator);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
