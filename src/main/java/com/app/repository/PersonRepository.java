package com.app.repository;

import com.app.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {
    List<PersonEntity> getPersonEntitiesBySurname(String surname);

    Optional<PersonEntity> getPersonEntityByNameAndSurname(String name, String surname);

    Optional<PersonEntity> getPersonEntityByEmail(String email);
}
