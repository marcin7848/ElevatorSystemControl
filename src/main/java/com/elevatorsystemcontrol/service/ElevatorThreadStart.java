package com.elevatorsystemcontrol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ElevatorThreadStart {

    final
    ElevatorService elevatorService;

    @Autowired
    public ElevatorThreadStart(ElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }

    //@EventListener(ApplicationReadyEvent.class)
    @Scheduled(initialDelay = 1000, fixedDelay=1000)
    public void manageElevatorsTask() {
        this.elevatorService.getAll().forEach(elevatorService::createThreadForElevator);
    }
}