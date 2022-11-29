package com.elevatorsystemcontrol.repository;

import com.elevatorsystemcontrol.model.ElevatorFloor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElevatorFloorRepository extends CrudRepository<ElevatorFloor, Long> {

}
