package com.app.repository;

import com.app.entity.ReservationComponent;
import com.app.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {
    @Query("SELECT c FROM ReservationEntity r JOIN r.components c WHERE r.id = :reservationId")
    List<ReservationComponent> findComponentsByReservationId(@Param("reservationId") Integer reservationId);
}
