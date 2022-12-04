package com.elevatorsystemcontrol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ElevatorThreadStart {

    final
    ElevatorService elevatorService;

    @Autowired
    public ElevatorThreadStart(ElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void manageElevatorsTask() {
        this.elevatorService.getAll().forEach(elevatorService::createThreadForElevator);
    }
}
