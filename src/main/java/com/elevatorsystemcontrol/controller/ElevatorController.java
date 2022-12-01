package com.elevatorsystemcontrol.controller;

import com.elevatorsystemcontrol.service.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elevator")
@CrossOrigin("http://localhost:4200")
public class ElevatorController {

    final ElevatorService elevatorService;

    @Autowired
    public ElevatorController(ElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> test(){
        return new ResponseEntity<>(this.elevatorService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewElevator(){
        return new ResponseEntity<>(this.elevatorService.addNewElevator(), HttpStatus.OK);
    }

}
