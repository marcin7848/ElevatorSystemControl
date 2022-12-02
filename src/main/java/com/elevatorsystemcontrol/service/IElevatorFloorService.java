package com.elevatorsystemcontrol.service;

import com.elevatorsystemcontrol.model.ElevatorFloor;

public interface IElevatorFloorService {
    ElevatorFloor addNewElevatorFloor(ElevatorFloor elevatorFloor, Long elevatorId);
}
