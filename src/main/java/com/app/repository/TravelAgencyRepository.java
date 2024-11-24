package com.app.repository;

import com.app.entity.TravelAgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TravelAgencyRepository extends JpaRepository<TravelAgencyEntity, Integer> {
    Optional<TravelAgencyEntity> getTravelAgencyEntityById(int id);

    Optional<TravelAgencyEntity> getTravelAgencyEntityByName(String name);

    Optional<TravelAgencyEntity> getTravelAgencyEntityByNameAndCity(String name, String city);

    List<TravelAgencyEntity> getTravelAgencyEntitiesByCity(String city);
}
