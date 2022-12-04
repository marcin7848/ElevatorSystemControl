package com.elevatorsystemcontrol.service;

import com.elevatorsystemcontrol.model.Elevator;

import java.util.List;

public interface IElevatorService {
    List<Elevator> getAll();

    Elevator getElevator(Long id);

    Elevator addNewElevator();

    boolean deleteElevator(Long id);

    void createThreadForElevator(Elevator elevator);

}
