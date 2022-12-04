package com.elevatorsystemcontrol.repository;

import com.elevatorsystemcontrol.model.ElevatorFloor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ElevatorFloorRepository extends JpaRepository<ElevatorFloor, Long> {

}
