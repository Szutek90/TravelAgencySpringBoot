package com.app.controller;

import com.app.controller.dto.ResponseDto;
import com.app.dto.PersonDto;
import com.app.dto.person.GetPersonDto;
import com.app.dto.person.UpdatePersonDto;
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
    public List<ResponseDto<PersonDto>> getAllPersons() {
        return personService.getAllPersons().stream()
                .map(ResponseDto::new)
                .toList();
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
        return ResponseDto<>(personService.updatePerson(PersonDto, id));
    }

//    public void routes() {
//            put(
//                    "/:id",
//                    (request, response) -> {
//                        var id = Integer.parseInt(request.params("id"));
//                        var updatePersonDto
//                                = gson.fromJson(request.body(), UpdatePersonDto.class);
//                        response.header("Content-Type", "application/json;charset=utf-8");
//                        return new ResponseDto<>(personService.updatePerson(updatePersonDto, id));
//                    },
//                    responseTransformer
//            );
//        });
//    }
}
