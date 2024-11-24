package com.app.repository;

import com.app.entity.TravelAgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TravelAgencyRepository extends JpaRepository<TravelAgencyEntity, Integer> {
    Optional<TravelAgencyEntity> findById(int id);

    Optional<TravelAgencyEntity> findByName(String name);

    List<TravelAgencyEntity> findByCity(String city);
}
