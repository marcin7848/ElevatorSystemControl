package com.elevatorsystemcontrol.repository;

import com.elevatorsystemcontrol.model.ElevatorFloor;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ElevatorFloorRepository extends CrudRepository<ElevatorFloor, Long> {

}
