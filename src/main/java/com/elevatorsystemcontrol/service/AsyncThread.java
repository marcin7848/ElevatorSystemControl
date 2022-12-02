package com.elevatorsystemcontrol.service;

import com.elevatorsystemcontrol.model.Elevator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncThread {
    
    @Async
    public void createThreadForElevator(Elevator elevator){
        //TODO: Create one thread for each elevator
        //Updating each elevator inside every thread separately

        try {
            Thread.sleep((int)(Math.random()*5000+1));
            System.out.println("test " + elevator.getId());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
