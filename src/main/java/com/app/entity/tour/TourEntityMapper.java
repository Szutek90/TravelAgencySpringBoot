package com.app.entity.tour;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public interface TourEntityMapper {
    ToIntFunction<TourEntity> toAgencyId = t -> t.agencyId;
    ToIntFunction<TourEntity> toCountryId = t -> t.countryId;
    Function<TourEntity, BigDecimal> toPrice = t -> t.pricePerPerson;
}
