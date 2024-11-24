package com.app.repository;

import com.app.entity.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TourRepository extends JpaRepository<TourEntity, Integer> {
    List<TourEntity> getTourEntitiesByCountryEntityName(String countryName);

    List<TourEntity> getTourEntitiesByPricePerPersonBetween(BigDecimal from, BigDecimal to);

    List<TourEntity> getTourEntitiesByPricePerPersonBefore(BigDecimal to);

    List<TourEntity> getTourEntitiesByPricePerPersonAfter(BigDecimal from);

    List<TourEntity> getTourEntitiesByStartDateAfterAndEndDateBefore(LocalDate from, LocalDate to);

    List<TourEntity> getTourEntitiesByEndDateBefore(LocalDate to);

    List<TourEntity> getTourEntitiesByStartDateAfter(LocalDate from);

    List<TourEntity> getTourEntitiesByAgencyId(int agencyId);
}
