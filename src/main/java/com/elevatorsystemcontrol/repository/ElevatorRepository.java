package com.elevatorsystemcontrol.repository;

import com.elevatorsystemcontrol.model.Elevator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElevatorRepository extends CrudRepository<Elevator, Long> {

}
