package com.app.repository;

import com.app.entity.agency.TravelAgencyEntity;
import com.app.repository.generic.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TravelAgencyRepository extends CrudRepository<TravelAgencyEntity, Integer> {
    Optional<TravelAgencyEntity> findById(int id);

    Optional<TravelAgencyEntity> findByName(String name);

    List<TravelAgencyEntity> findByCity(String city);
}
