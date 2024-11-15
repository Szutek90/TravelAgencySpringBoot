package com.app.repository;

import com.app.entity.country.CountryEntity;
import com.app.repository.generic.CrudRepository;

import java.util.Optional;

public interface CountryRepository extends CrudRepository<CountryEntity, Integer> {
    Optional<CountryEntity> findByCountry(String country);
}
