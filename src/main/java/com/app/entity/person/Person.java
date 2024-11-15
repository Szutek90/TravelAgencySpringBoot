package com.app.entity.person;

import com.app.dto.person.GetPersonDto;
import com.app.dto.person.UpdatePersonDto;
import com.app.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "persons")
@Entity
@Setter
@Getter
public class Person extends BaseEntity {
    private String name;
    private String surname;
    private String email;

    public Person(UpdatePersonDto updatePersonDto) {
        name = updatePersonDto.name();
        surname = updatePersonDto.surname();
        email = updatePersonDto.email();
    }

    public GetPersonDto toGetPersonDto() {
        return new GetPersonDto(name, surname, email);
    }
}
