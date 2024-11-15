package com.app.repository.impl;

import com.app.entity.reservation.ReservationEntity;
import com.app.repository.ReservationRepository;
import com.app.repository.generic.AbstractCrudRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepositoryImpl extends AbstractCrudRepository<ReservationEntity, Integer> implements ReservationRepository {

    public ReservationRepositoryImpl(EntityManagerFactory emf) {
        super(emf);
    }
}
