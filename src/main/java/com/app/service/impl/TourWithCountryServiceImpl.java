package com.app.service.impl;

import com.app.converter.tours.FileToToursConverter;
import com.app.dto.TourDto;
import com.app.entity.TourEntity;
import com.app.repository.CountryRepository;
import com.app.repository.TourRepository;
import com.app.repository.TravelAgencyRepository;
import com.app.service.TourWithCountryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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
    private final TravelAgencyRepository travelAgencyRepository;
    private final ApplicationContext context;

    @Value("${tours.file}")
    String filename;

    @Value("${tours.format}")
    String format;

    @PostConstruct
    public void init() {
        var converter = context.getBean("%sFileToToursConverterImpl".formatted(format),
                FileToToursConverter.class);
        if (tourRepository.count() == 0) {
            tourRepository.saveAll(converter.convert(filename));
        }
    }

    @Override
    public TourDto getById(int id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("There is no Tour with given id"))
                .toTourDto();
    }

    @Override
    public List<TourDto> getByCountry(String country) {
        return tourRepository.getByCountryEntityName(country).stream()
                .map(TourEntity::toTourDto)
                .toList();
    }

    @Override
    public List<TourDto> getToursInPriceRange(BigDecimal from, BigDecimal to) {
        return tourRepository.getInPriceRange(from, to).stream()
                .map(TourEntity::toTourDto)
                .toList();
    }

    @Override
    public List<TourDto> getToursCheaperThan(BigDecimal priceTo) {
        return tourRepository.getLessThanGivenPrice(priceTo).stream()
                .map(TourEntity::toTourDto)
                .toList();
    }

    @Override
    public List<TourDto> getToursMoreExpensiveThan(BigDecimal priceFrom) {
        return tourRepository.getMoreExpensiveThanGivenPrice(priceFrom).stream()
                .map(TourEntity::toTourDto)
                .toList();
    }

    @Override
    public List<TourDto> getToursInRange(LocalDate from, LocalDate to) {
        return tourRepository.getInDateRange(from, to).stream()
                .map(TourEntity::toTourDto)
                .toList();
    }

    @Override
    public List<TourDto> getToursAfterDate(LocalDate from) {
        return tourRepository.getAfterGivenDate(from).stream()
                .map(TourEntity::toTourDto)
                .toList();
    }

    @Override
    public List<TourDto> getToursBeforeDate(LocalDate to) {
        return tourRepository.getBeforeGivenDate(to).stream()
                .map(TourEntity::toTourDto)
                .toList();
    }

    @Override
    public TourDto createTour(TourDto tourDto) {
        var agency = travelAgencyRepository.findByName(tourDto.agencyName())
                .orElseThrow(() -> new IllegalArgumentException("There is no Travel Agency with given name"));
        var country = countryRepository.findByName(tourDto.countryName())
                .orElseThrow(() -> new IllegalArgumentException("There is no country with given name"));

        var tourToSave = tourRepository.save(TourEntity.builder()
                .agencyId(agency.getId())
                .countryId(country.getId())
                .pricePerPerson(tourDto.pricePerPerson())
                .startDate(tourDto.startDate())
                .endDate(tourDto.endDate())
                .build());
        tourRepository.save(tourToSave);
        return tourToSave.toTourDto();
    }
}
