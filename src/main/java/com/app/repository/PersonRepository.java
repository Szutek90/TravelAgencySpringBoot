package com.app.repository;

import com.app.entity.person.PersonEntity;
import com.app.repository.generic.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<PersonEntity, Integer> {
    List<PersonEntity> findBySurname(String surname);

    Optional<PersonEntity> findByNameAndSurname(String name, String surname);

    Optional<PersonEntity> findByEmail(String email);
}
