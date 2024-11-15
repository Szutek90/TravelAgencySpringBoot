package com.app.dto.reservation;

import com.app.entity.ReservationComponent;
import com.app.entity.agency.TravelAgencyEntity;
import com.app.entity.person.PersonEntity;
import com.app.entity.tour.TourEntity;

import java.util.List;

public record GetReservationDto(TourEntity tourEntity, TravelAgencyEntity travelAgencyEntity, PersonEntity customer, int quantityOfPeople
        , int discount, List<ReservationComponent> reservationComponents) {
}
