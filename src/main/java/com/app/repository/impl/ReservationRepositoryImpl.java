package com.app.repository.impl;

import com.app.entity.reservation.Reservation;
import com.app.repository.ReservationRepository;
import com.app.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepositoryImpl extends AbstractCrudRepository<Reservation, Integer> implements ReservationRepository {
    public ReservationRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }
}
