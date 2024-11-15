package com.app.service;

import com.app.dto.TourDto;
import com.app.entity.tour.TourEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TourWithCountryService {
    TourEntity getById(int id);

    List<TourEntity> getByCountry(String country);

    List<TourEntity> getToursInPriceRange(BigDecimal from, BigDecimal to);

    List<TourEntity> getToursCheaperThan(BigDecimal priceTo);

    List<TourEntity> getToursMoreExpensiveThan(BigDecimal priceFrom);

    List<TourEntity> getToursInRange(LocalDate from, LocalDate to);

    List<TourEntity> getToursAfterDate(LocalDate from);

    List<TourEntity> getToursBeforeDate(LocalDate to);

    TourEntity createTour(TourDto tourDto);
}
