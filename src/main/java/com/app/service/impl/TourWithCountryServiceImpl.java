package com.app.service.impl;

import com.app.dto.TourDto;
import com.app.entity.country.CountryEntityMapper;
import com.app.entity.tour.TourEntity;
import com.app.repository.CountryRepository;
import com.app.repository.TourRepository;
import com.app.service.TourWithCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TourWithCountryServiceImpl implements TourWithCountryService {
    private final TourRepository tourRepository;
    private final CountryRepository countryRepository;

    @Override
    public TourEntity getById(int id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("There is no Tour with given id"));
    }

    @Override
    public List<TourEntity> getByCountry(String country) {
        return tourRepository.getByCountryName(country);
    }

    @Override
    public List<TourEntity> getToursInPriceRange(BigDecimal from, BigDecimal to) {
        return tourRepository.getInPriceRange(from, to);
    }

    @Override
    public List<TourEntity> getToursCheaperThan(BigDecimal priceTo) {
        return tourRepository.getLessThanGivenPrice(priceTo);
    }

    @Override
    public List<TourEntity> getToursMoreExpensiveThan(BigDecimal priceFrom) {
        return tourRepository.getMoreExpensiveThanGivenPrice(priceFrom);
    }

    @Override
    public List<TourEntity> getToursInRange(LocalDate from, LocalDate to) {
        return tourRepository.getInDateRange(from, to);
    }

    @Override
    public List<TourEntity> getToursAfterDate(LocalDate from) {
        return tourRepository.getAfterGivenDate(from);
    }

    @Override
    public List<TourEntity> getToursBeforeDate(LocalDate to) {
        return tourRepository.getBeforeGivenDate(to);
    }

    @Override
    public TourEntity createTour(TourDto tourDto) {
        var countryToAdd = countryRepository.findByCountry(tourDto.countryName())
                .orElseThrow(() -> new IllegalArgumentException("There is no country with given name"));

        var tourToSave = tourRepository.save(TourEntity.builder()
                .countryId(CountryEntityMapper.toId.applyAsInt(countryToAdd))
                .pricePerPerson(tourDto.pricePerPerson())
                .startDate(tourDto.startDate())
                .endDate(tourDto.endDate())
                .build());
        tourRepository.save(tourToSave);
        return tourToSave;
    }
}
