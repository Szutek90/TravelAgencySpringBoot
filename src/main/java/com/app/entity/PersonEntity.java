package com.app.entity;

import com.app.dto.PersonDto;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Table(name = "persons")
@Entity
@Setter
@Getter
public class PersonEntity extends BaseEntity {
    private String name;
    private String surname;
    private String email;

    public PersonEntity(PersonDto personDto) {
        name = personDto.name();
        surname = personDto.surname();
        email = personDto.email();
    }

    public PersonDto toPersonDto() {
        return new PersonDto(name, surname, email);
    }

    @ManyToMany(mappedBy = "persons")
    private List<ReservationEntity> reservationEntities = new ArrayList<>();
}
