package com.app.repository;

import com.app.entity.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TourRepository extends JpaRepository<TourEntity, Integer> {
    List<TourEntity> getByCountryEntityName(String countryName);

    List<TourEntity> getInPriceRange(BigDecimal from, BigDecimal to);

    List<TourEntity> getLessThanGivenPrice(BigDecimal to);

    List<TourEntity> getMoreExpensiveThanGivenPrice(BigDecimal from);

    List<TourEntity> getInDateRange(LocalDate from, LocalDate to);

    List<TourEntity> getBeforeGivenDate(LocalDate to);

    List<TourEntity> getAfterGivenDate(LocalDate from);

    List<TourEntity> getById(int agencyId);
}
