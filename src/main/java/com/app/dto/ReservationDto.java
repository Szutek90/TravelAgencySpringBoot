package com.app.dto;

import com.app.entity.ReservationComponent;
import java.util.List;

public record ReservationDto(Integer tourId, String agencyName, PersonDto person, int quantityOfPeople,
                             int discount, List<ReservationComponent> reservationComponents) {
}
