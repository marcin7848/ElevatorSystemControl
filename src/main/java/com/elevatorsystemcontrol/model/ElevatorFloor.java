package com.elevatorsystemcontrol.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.sql.Timestamp;

@Entity
@Table(name = "elevator_floors")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ElevatorFloor {

    /*
    @param MIN_FLOOR    Define minimum floor available for the elevator
    @param MAX_FLOOR    Define maximum floor available for the elevator
    */
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

    @Column(name = "floor_pick_time")
    private Timestamp floorPickTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_elevator")
    @JsonBackReference
    private Elevator elevator;

}
