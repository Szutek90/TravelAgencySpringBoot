package com.app.dto;

import com.app.entity.ReservationComponent;
import com.app.entity.agency.TravelAgencyEntity;
import com.app.entity.person.PersonEntity;
import com.app.entity.tour.TourEntity;

import java.util.List;

public record ReservationDto(Integer tourId, String agencyName, PersonDto person, int quantityOfPeople,
                             int discount, List<ReservationComponent> reservationComponents) {
}
