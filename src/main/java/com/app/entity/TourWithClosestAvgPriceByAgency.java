package com.app.entity;

import com.app.entity.tour.TourEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class TourWithClosestAvgPriceByAgency {
    private final BigDecimal averagePrice;
    private final TourEntity tourEntity;
}
