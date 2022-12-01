package com.elevatorsystemcontrol.service;

import com.elevatorsystemcontrol.exceptions.MessageException;
import com.elevatorsystemcontrol.model.Elevator;
import com.elevatorsystemcontrol.repository.ElevatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElevatorService implements IElevatorService {

    //@param MAX_ELEVATORS - define the maximum number of elevators controlled by the system
    private final int MAX_ELEVATORS = 16;
    final ElevatorRepository elevatorRepository;

    @Autowired
    public ElevatorService(ElevatorRepository elevatorRepository) {
        this.elevatorRepository = elevatorRepository;
    }

    /*
     * Gets all elevators from the database, no params available
     * @return List<Elevators> with all elevators
     * */
    public List<Elevator> getAll(){
        return (List<Elevator>) this.elevatorRepository.findAll();
    }

    /*
    * Adds a new elevator to the system, no params available
    * @exception throws ResponseEntity exception if the database already contains a maximum number of elevators
    * defined in MAX_ELEVATORS
    * @return Elevator object created in the database
    * */
    public Elevator addNewElevator(){
        if(this.elevatorRepository.count() >= this.MAX_ELEVATORS){
            throw new MessageException("You cannot add more elevators! Maximum reached.");
        }
        return this.elevatorRepository.save(new Elevator());
    }

}
