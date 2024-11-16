package com.app.entity.person;

import com.app.dto.PersonDto;
import com.app.entity.BaseEntity;
import com.app.entity.reservation.ReservationEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
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
