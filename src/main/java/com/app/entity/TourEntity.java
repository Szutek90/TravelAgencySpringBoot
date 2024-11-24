package com.app.entity;

import com.app.dto.TourDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Table(name = "tours")
@Entity
@Setter
@Getter
public class TourEntity extends BaseEntity {
    protected Integer agencyId;
    protected BigDecimal pricePerPerson;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "country_id", unique = true)
    private CountryEntity countryEntity;

    @OneToOne(mappedBy = "tourEntity")
    private ReservationEntity reservationEntity;

    @ManyToOne
    @JoinColumn(name = "travel_agency_id")
    private TravelAgencyEntity travelAgencyEntity;

    public TourDto toTourDto() {
        return new TourDto(travelAgencyEntity.getName(), countryEntity.getName(), pricePerPerson, startDate, endDate);
    }
}