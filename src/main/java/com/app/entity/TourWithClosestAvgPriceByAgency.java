package com.app.entity;

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
