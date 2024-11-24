package com.app.repository;

import com.app.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {
    Optional<CountryEntity> getCountryEntityByName(String countryName);
}
