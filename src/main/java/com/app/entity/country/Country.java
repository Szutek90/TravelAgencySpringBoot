package com.app.entity.country;

import com.app.dto.country.GetCountryDto;
import com.app.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "countries")
@Entity
@Setter
@Getter
public class Country extends BaseEntity {
    protected String name;

    public GetCountryDto toGetCountryDto() {
        return new GetCountryDto(name);
    }
}
