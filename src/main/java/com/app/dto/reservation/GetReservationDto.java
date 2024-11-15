package com.app.dto.reservation;

import com.app.entity.ReservationComponent;
import com.app.entity.agency.TravelAgency;
import com.app.entity.person.Person;
import com.app.entity.tour.Tour;

import java.util.List;

public record GetReservationDto(Tour tour, TravelAgency travelAgency, Person customer, int quantityOfPeople
        , int discount, List<ReservationComponent> reservationComponents) {
}
