package com.app.service.impl;

import com.app.dto.PersonDto;
import com.app.entity.PersonEntity;
import com.app.repository.PersonRepository;
import com.app.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonDto addPerson(PersonDto personDto) {
        if (personRepository.getPersonEntityByEmail(personDto.email()).isPresent()) {
            throw new IllegalArgumentException("Person with given email already exist");
        }
        var personToSave = personRepository.save(PersonEntity.builder()
                .name(personDto.name())
                .surname(personDto.surname())
                .email(personDto.email())
                .build());
        return personToSave.toPersonDto();
    }

    @Override
    public PersonDto updatePerson(PersonDto personDto, Integer id) {
        var personToUpdate = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no Person with given Id"));
        personToUpdate.setName(personDto.name());
        personToUpdate.setSurname(personDto.surname());
        personToUpdate.setEmail(personDto.email());
        return personRepository.save(personToUpdate).toPersonDto();
    }

    @Override
    public PersonDto getPersonById(int id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no Person with given id"))
                .toPersonDto();
    }

    @Override
    public PersonDto getPersonByNameAndSurname(String name, String surname) {
        return personRepository.getPersonEntityByNameAndSurname(name, surname)
                .orElseThrow(() -> new IllegalArgumentException("There is no Person with given id"))
                .toPersonDto();
    }

    @Override
    public List<PersonDto> getAllPersons() {
        return personRepository.findAll()
                .stream()
                .map(PersonEntity::toPersonDto)
                .toList();
    }
}
