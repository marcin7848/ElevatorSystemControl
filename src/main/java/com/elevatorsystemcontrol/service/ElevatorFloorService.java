package com.elevatorsystemcontrol.service;

import com.elevatorsystemcontrol.model.Elevator;
import com.elevatorsystemcontrol.model.ElevatorFloor;
import com.elevatorsystemcontrol.repository.ElevatorFloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;

@Service
public class ElevatorFloorService implements IElevatorFloorService {

    final ElevatorFloorRepository elevatorFloorRepository;
    final ElevatorService elevatorService;

    @Autowired
    public ElevatorFloorService(ElevatorFloorRepository elevatorFloorRepository, ElevatorService elevatorService) {
        this.elevatorFloorRepository = elevatorFloorRepository;
        this.elevatorService = elevatorService;
    }


    /**
     * Gets the highest position of ElevatorFloor for the specified elevator object
     * @param elevator  Elevator object to find list of ElevatorFloor for it
     * @return          The highest int position of the elevatorFloor
     *                  -1 if the elevator does not contain any elevatorFloors
     */
    public int getHighestElevatorFloorPositionByElevator(Elevator elevator){
        Elevator elevator1 = this.elevatorService.getElevator(elevator.getId());
        if(elevator1.getTargetFloors().size() == 0)
            return -1;

        elevator1.getTargetFloors().sort(Comparator.comparing(ElevatorFloor::getPosition).reversed());
        return elevator1.getTargetFloors().get(0).getPosition();
    }

    /**
     * Adds a new elevator floor destination to specified Elevator object
     * @param elevatorFloor     The object should contain floor and direction params
     * @param elevatorId        The ID of existed elevator in the database
     * @return                  ElevatorFloor object created in the database
     */
    public ElevatorFloor addNewElevatorFloor(ElevatorFloor elevatorFloor, Long elevatorId){
        Elevator elevator = this.elevatorService.getElevator(elevatorId);
        elevatorFloor.setFloorPickTime(Timestamp.from(Instant.now()));
        elevatorFloor.setElevator(elevator);
        elevatorFloor.setPosition(this.getHighestElevatorFloorPositionByElevator(elevator)+1);
        System.out.println(elevatorFloor.getPosition());
        return this.elevatorFloorRepository.save(elevatorFloor);
    }

    
}
