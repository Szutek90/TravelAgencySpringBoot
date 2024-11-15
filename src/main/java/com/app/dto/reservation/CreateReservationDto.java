package com.app.dto.reservation;

import com.app.entity.ReservationComponent;
import com.app.entity.agency.TravelAgency;
import com.app.entity.agency.TravelAgencyMapper;
import com.app.entity.person.Person;
import com.app.entity.reservation.Reservation;
import com.app.entity.tour.Tour;

import java.util.List;

public record CreateReservationDto(Person customer,
                                   Tour tour,
                                   TravelAgency travelAgency,
                                   int quantityOfPeople,
                                   int discount,
                                   List<ReservationComponent> reservationComponents) {

    public Reservation toReservation() {
        return Reservation.builder()
                .tourId(tour.getId())
                .customerId(customer.getId())
                .quantityOfPeople(quantityOfPeople)
                .discount(discount)
                .agencyId(TravelAgencyMapper.toId.applyAsInt(travelAgency))
                .build();
    }
}
