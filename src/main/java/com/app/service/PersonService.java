package com.app.service;

import com.app.dto.PersonDto;
import com.app.entity.PersonEntity;

import java.util.List;

public interface PersonService {
    PersonDto addPerson(PersonDto personDto);
    PersonDto updatePerson(PersonEntity person);
    PersonDto getPersonById(int id);
    PersonDto getPersonByNameAndSurname(String name, String surname);
    List<PersonDto> getAllPersons();
}
