package com.app.repository;

import com.app.entity.tour.TourEntity;
import com.app.repository.generic.CrudRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TourRepository extends CrudRepository<TourEntity, Integer> {
    List<TourEntity> getByCountryName(String countryName);

    List<TourEntity> getInPriceRange(BigDecimal from, BigDecimal to);

    List<TourEntity> getLessThanGivenPrice(BigDecimal to);

    List<TourEntity> getMoreExpensiveThanGivenPrice(BigDecimal from);
    List<TourEntity> getInDateRange(LocalDate from, LocalDate to);
    List<TourEntity> getBeforeGivenDate(LocalDate to);
    List<TourEntity> getAfterGivenDate(LocalDate from);
    List<TourEntity> getByAgency(int agencyId);
}
