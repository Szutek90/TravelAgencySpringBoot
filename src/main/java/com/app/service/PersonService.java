package com.app.service;

import com.app.dto.PersonDto;

import java.util.List;

public interface PersonService {
    PersonDto addPerson(PersonDto personDto);
    PersonDto updatePerson(PersonDto updatePersonDto, Integer id);
    PersonDto getPersonById(int id);
    PersonDto getPersonByNameAndSurname(String name, String surname);
    List<PersonDto> getAllPersons();
}
