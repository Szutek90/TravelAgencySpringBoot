package com.app.repository;

import com.app.entity.agency.TravelAgencyEntity;

import java.util.List;
import java.util.Optional;

public interface TravelAgencyRepository {
    Optional<TravelAgencyEntity> findById(int id);

    Optional<TravelAgencyEntity> findByName(String name);

    List<TravelAgencyEntity> findByCity(String city);
}
