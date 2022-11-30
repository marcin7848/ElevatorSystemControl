package com.elevatorsystemcontrol.service;

import com.elevatorsystemcontrol.repository.ElevatorFloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElevatorFloorService {

    final ElevatorFloorRepository elevatorFloorRepository;

    @Autowired
    public ElevatorFloorService(ElevatorFloorRepository elevatorFloorRepository) {
        this.elevatorFloorRepository = elevatorFloorRepository;
    }
    
}
