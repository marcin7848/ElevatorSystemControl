package com.elevatorsystemcontrol.controller;

import com.elevatorsystemcontrol.exceptions.MessageException;
import com.elevatorsystemcontrol.model.Elevator;
import com.elevatorsystemcontrol.model.ElevatorFloor;
import com.elevatorsystemcontrol.service.ElevatorFloorService;
import com.elevatorsystemcontrol.service.ElevatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elevator")
@CrossOrigin("http://localhost:4200")
@Validated
public class ElevatorController {

    final ElevatorService elevatorService;
    final ElevatorFloorService elevatorFloorService;

    @Autowired
    public ElevatorController(ElevatorService elevatorService, ElevatorFloorService elevatorFloorService) {
        this.elevatorService = elevatorService;
        this.elevatorFloorService = elevatorFloorService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(this.elevatorService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewElevator(){
        Elevator elevator = this.elevatorService.addNewElevator();
        this.elevatorService.createThreadForElevator(elevator);
        return new ResponseEntity<>(elevator, HttpStatus.OK);
    }

    @DeleteMapping ("/{id}/delete")
    public ResponseEntity<?> deleteElevator(@PathVariable("id") Long id){
        return new ResponseEntity<>(this.elevatorService.deleteElevator(id), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/elevatorFloor/add")
    public ResponseEntity<?> addNewElevatorFloor(@PathVariable("id") Long elevatorId,
                                                 @Valid @RequestBody ElevatorFloor elevatorFloor) {
        return new ResponseEntity<>(this.elevatorFloorService.addNewElevatorFloor(elevatorFloor, elevatorId), HttpStatus.OK);
    }

}
