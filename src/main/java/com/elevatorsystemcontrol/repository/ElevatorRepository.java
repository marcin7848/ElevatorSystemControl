package com.elevatorsystemcontrol.repository;

import com.elevatorsystemcontrol.model.Elevator;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ElevatorRepository extends CrudRepository<Elevator, Long> {

}
