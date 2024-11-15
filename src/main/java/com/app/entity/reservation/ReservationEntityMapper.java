package com.app.entity.reservation;

import java.util.function.ToIntFunction;

public interface ReservationEntityMapper {
    ToIntFunction<ReservationEntity> toTourId = r -> r.tourId;
    ToIntFunction<ReservationEntity> toAgencyId = r -> r.agencyId;
    ToIntFunction<ReservationEntity> toPersonId = r -> r.customerId;
    ToIntFunction<ReservationEntity> toQuantityOfPeople = r -> r.quantityOfPeople;
    ToIntFunction<ReservationEntity> toDiscount = r -> r.discount;
}
