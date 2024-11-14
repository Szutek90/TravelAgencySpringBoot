package com.app.repository;

import com.app.model.reservation.Reservation;
import com.app.repository.generic.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
}
