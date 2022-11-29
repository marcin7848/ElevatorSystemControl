package com.elevatorsystemcontrol.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

@Entity
@Table(name = "elevator_floors")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ElevatorFloor {

//  Define MIN_FLOOR and MAX_FLOOR available for elevators
    private static final int MIN_FLOOR = -2;
    private static final int MAX_FLOOR = 8;

    @Id
    @Column(name = "id_elevator_floor")
    @SequenceGenerator(name = "elevator_floor_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "elevator_floor_seq")
    @NotNull
    private Long id;

    @Column(name = "floor")
    @Range(from=MIN_FLOOR,to=MAX_FLOOR)
    private int floor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_elevator")
    @JsonBackReference
    private Elevator elevator;

}
