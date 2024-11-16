package com.app.controller;

import com.app.controller.dto.ResponseDto;
import com.app.dto.PersonDto;
import com.app.service.PersonService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    private final Gson gson;

    @GetMapping
    public ResponseDto<List<PersonDto>> getAllPersons() {
        return new ResponseDto<>(personService.getAllPersons());
    }

    @PostMapping
    public ResponseDto<PersonDto> createPerson(@RequestBody PersonDto personDto) {
        return new ResponseDto<>(personService.addPerson(personDto));
    }

    @GetMapping("/{id}")
    public ResponseDto<PersonDto> getPersonById(@PathVariable Integer id) {
        return new ResponseDto<>(personService.getPersonById(id));
    }

    @GetMapping()
    public ResponseDto<PersonDto> getPersonByNameAndSurname(@RequestParam String name, @RequestParam String surname) {
        return new ResponseDto<>(personService.getPersonByNameAndSurname(name, surname));
    }

    @PutMapping("/{id}")
    public ResponseDto<PersonDto> updatePerson(@RequestBody PersonDto updatePersonDto, @RequestParam Integer id) {
        return new ResponseDto<>(personService.updatePerson(updatePersonDto, id));
    }
}
