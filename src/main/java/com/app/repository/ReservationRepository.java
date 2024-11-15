package com.app.repository;

import com.app.entity.reservation.ReservationEntity;
import com.app.repository.generic.CrudRepository;

public interface ReservationRepository extends CrudRepository<ReservationEntity, Integer> {
}
