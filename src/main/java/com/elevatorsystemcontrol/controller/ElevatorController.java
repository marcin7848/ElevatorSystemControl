package com.elevatorsystemcontrol.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class ElevatorController {

    @GetMapping("/test")
    public ResponseEntity<?> test(){

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
