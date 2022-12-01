package com.elevatorsystemcontrol.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Entity
@Table(name = "elevators")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Elevator {

    @Id
    @Column(name = "id_elevator")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "elevator_seq")
    @NotNull
    private Long id;

    @Column(name = "current_floor")
    private int currentFloor = 0;

    @Column(name = "current_status")
    private int currentStatus = 0;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "elevator", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ElevatorFloor> targetFloors;



}
