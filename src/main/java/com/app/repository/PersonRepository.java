package com.app.repository;

import com.app.entity.person.Person;
import com.app.repository.generic.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    List<Person> findBySurname(String surname);

    Optional<Person> findByNameAndSurname(String name, String surname);

    Optional<Person> findByEmail(String email);
}
