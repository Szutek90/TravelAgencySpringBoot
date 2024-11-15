package com.app.repository;

import com.app.entity.tour.Tour;
import com.app.repository.generic.CrudRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TourRepository extends CrudRepository<Tour, Integer> {
    List<Tour> getByCountryName(String countryName);

    List<Tour> getInPriceRange(BigDecimal from, BigDecimal to);

    List<Tour> getLessThanGivenPrice(BigDecimal to);

    List<Tour> getMoreExpensiveThanGivenPrice(BigDecimal from);
    List<Tour> getInDateRange(LocalDate from, LocalDate to);
    List<Tour> getBeforeGivenDate(LocalDate to);
    List<Tour> getAfterGivenDate(LocalDate from);
    List<Tour> getByAgency(int agencyId);
}
