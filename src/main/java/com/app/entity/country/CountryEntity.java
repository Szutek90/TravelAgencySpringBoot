package com.app.entity.country;

import com.app.dto.CountryDto;
import com.app.entity.BaseEntity;
import com.app.entity.tour.TourEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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
public class CountryEntity extends BaseEntity {
    protected String name;

    public CountryDto toGetCountryDto() {
        return new CountryDto(name);
    }

    @OneToOne(mappedBy = "country")
    private TourEntity tourEntity;
}
