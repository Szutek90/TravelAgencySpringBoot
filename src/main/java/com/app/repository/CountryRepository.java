package com.app.repository;

import com.app.entity.country.Country;
import com.app.repository.generic.CrudRepository;

import java.util.Optional;

public interface CountryRepository extends CrudRepository<Country, Integer> {
    Optional<Country> findByCountry(String country);
}
