package com.app.dto.reservation;

import com.app.entity.ReservationComponent;
import com.app.entity.agency.TravelAgencyEntity;
import com.app.entity.person.PersonEntity;
import com.app.entity.reservation.ReservationEntity;
import com.app.entity.tour.TourEntity;

import java.util.List;

public record CreateReservationDto(PersonEntity customer,
                                   TourEntity tourEntity,
                                   TravelAgencyEntity travelAgencyEntity,
                                   int quantityOfPeople,
                                   int discount,
                                   List<ReservationComponent> reservationComponents) {

    public ReservationEntity toReservation() {
        return ReservationEntity.builder()
                .tourId(tourEntity.getId())
                .customerId(customer.getId())
                .quantityOfPeople(quantityOfPeople)
                .discount(discount)
                .build();
    }
}
