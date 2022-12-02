package com.elevatorsystemcontrol.service;

import com.elevatorsystemcontrol.exceptions.MessageException;
import com.elevatorsystemcontrol.model.Elevator;
import com.elevatorsystemcontrol.model.ElevatorFloor;
import com.elevatorsystemcontrol.repository.ElevatorFloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class ElevatorFloorService implements IElevatorFloorService {

    final ElevatorFloorRepository elevatorFloorRepository;
    final ElevatorService elevatorService;

    @Autowired
    public ElevatorFloorService(ElevatorFloorRepository elevatorFloorRepository, ElevatorService elevatorService) {
        this.elevatorFloorRepository = elevatorFloorRepository;
        this.elevatorService = elevatorService;
    }

    /*
     * Adds a new elevator floor destination to specified Elevator object
     * @param   elevatorFloor   The object should contain floor and direction params
     * @param   elevatorId      The ID of existed elevator in the database
     * @exception    throws ResponseEntity exception if the database already contains a maximum number of elevators
     *               defined in MAX_ELEVATORS
     * @return       ElevatorFloor object created in the database
     * */
    public ElevatorFloor addNewElevatorFloor(ElevatorFloor elevatorFloor, Long elevatorId){
        Elevator elevator = this.elevatorService.getElevator(elevatorId);
        elevatorFloor.setFloorPickTime(Timestamp.from(Instant.now()));
        elevatorFloor.setElevator(elevator);
        return this.elevatorFloorRepository.save(elevatorFloor);
    }

    
}
