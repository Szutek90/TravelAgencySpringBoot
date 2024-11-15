package com.app.repository;

import com.app.entity.reservation.Reservation;
import com.app.repository.generic.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
}
