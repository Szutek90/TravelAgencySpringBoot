package com.app.entity;

import com.app.dto.CountryDto;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Table(name = "countries")
@Entity
@Setter
@Getter
public class CountryEntity extends BaseEntity {
    protected String name;

    @OneToOne(mappedBy = "countryEntity")
    private TourEntity tourEntity;

    public CountryDto toCountryDto() {
        return new CountryDto(name);
    }
}
