package com.app.repository;

import com.app.entity.agency.TravelAgencyEntity;

import java.util.List;
import java.util.Optional;

public interface TravelAgencyRepository {
    Optional<TravelAgencyEntity> findById(int id);

    Optional<TravelAgencyEntity> findByName(String name);

    List<TravelAgencyEntity> findByCity(String city);

    List<TravelAgencyEntity> getAll();

    int save(TravelAgencyEntity travelAgencyEntity);

    List<TravelAgencyEntity> saveAll(List<TravelAgencyEntity> travelAgencies);

    TravelAgencyEntity delete(int id);
}
