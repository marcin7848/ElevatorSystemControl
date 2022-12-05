package com.elevatorsystemcontrol.repository;

import com.elevatorsystemcontrol.model.Elevator;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ElevatorRepository extends JpaRepository<Elevator, Long> {

    @Query("DELETE FROM Elevator e WHERE e.id = :id")
    @Modifying
    void deleteElevatorById(@Param("id") Long id);
}
