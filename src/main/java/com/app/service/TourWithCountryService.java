package com.app.service;

import com.app.dto.TourDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TourWithCountryService {
    TourDto getById(int id);

    List<TourDto> getByCountry(String country);

    List<TourDto> getToursInPriceRange(BigDecimal from, BigDecimal to);

    List<TourDto> getToursCheaperThan(BigDecimal priceTo);

    List<TourDto> getToursMoreExpensiveThan(BigDecimal priceFrom);

    List<TourDto> getToursInRange(LocalDate from, LocalDate to);

    List<TourDto> getToursAfterDate(LocalDate from);

    List<TourDto> getToursBeforeDate(LocalDate to);

    TourDto createTour(TourDto tourDto);
}
