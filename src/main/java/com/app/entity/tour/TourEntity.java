package com.app.entity.tour;

import com.app.entity.BaseEntity;
import com.app.entity.agency.TravelAgencyEntity;
import com.app.entity.country.CountryEntity;
import com.app.entity.reservation.ReservationEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "tours")
@Entity
@Setter
@Getter
public class TourEntity extends BaseEntity {
    protected Integer agencyId;
    protected Integer countryId;
    protected BigDecimal pricePerPerson;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tour_id", unique = true)
    private CountryEntity countryEntity;

    @OneToOne(mappedBy = "tour")
    private ReservationEntity reservationEntity;

    @ManyToOne
    @JoinColumn(name = "travel_agency_id")
    private TravelAgencyEntity travelAgencyEntity;
}