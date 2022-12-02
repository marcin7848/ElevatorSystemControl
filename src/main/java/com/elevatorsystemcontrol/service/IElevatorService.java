package com.elevatorsystemcontrol.service;

import com.elevatorsystemcontrol.model.Elevator;

import java.util.List;

public interface IElevatorService {
    List<Elevator> getAll();
    Elevator addNewElevator();

    boolean deleteElevator(Long id);

    void manageElevatorsTask();
    void createThreadForElevator(Elevator elevator);

}
