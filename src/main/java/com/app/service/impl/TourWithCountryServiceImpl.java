package com.app.service.impl;

import com.app.converter.tours.FileToToursConverter;
import com.app.dto.TourDto;
import com.app.entity.CountryEntity;
import com.app.entity.TourEntity;
import com.app.entity.TravelAgencyEntity;
import com.app.repository.CountryRepository;
import com.app.repository.TourRepository;
import com.app.repository.TravelAgencyRepository;
import com.app.service.TourWithCountryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Transactional
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
        var readedTours = converter.convert(filename);
        if (tourRepository.count() == 0) {
            for (var tour : readedTours) {
                CountryEntity countryEntity = null;
                TravelAgencyEntity travelAgencyEntity = null;
                var travelAgencyName = tour.getTravelAgencyEntity().getName();
                var travelAgencyCity = tour.getTravelAgencyEntity().getCity();
                var countryName = tour.getCountryEntity().getName();
                if (countryRepository.count() == 0 || countryRepository.getCountryEntityByName(countryName).isEmpty()) {
                    countryEntity = countryRepository.save(CountryEntity
                            .builder()
                            .name(countryName)
                            .build());
                } else {
                    countryEntity = countryRepository.getCountryEntityByName(countryName).orElseThrow();
                }
                if (travelAgencyRepository.count() == 0 || travelAgencyRepository
                        .getTravelAgencyEntityByNameAndCity(travelAgencyName, travelAgencyCity).isEmpty()) {
                    travelAgencyEntity = travelAgencyRepository.save(TravelAgencyEntity
                            .builder()
                            .name(travelAgencyName)
                            .city(travelAgencyCity)
                            .phoneNumber(tour.getTravelAgencyEntity().getPhoneNumber())
                            .build());
                } else {
                    travelAgencyEntity = travelAgencyRepository
                            .getTravelAgencyEntityByNameAndCity(travelAgencyName, travelAgencyCity).orElseThrow();
                }
                var tourEn = TourEntity
                        .builder()
                        .id(0)
                        .pricePerPerson(tour.getPricePerPerson())
                        .startDate(tour.getStartDate())
                        .endDate(tour.getEndDate())
                        .countryEntity(countryEntity)
                        .travelAgencyEntity(travelAgencyEntity)
                        .build();
                tourRepository.save(tourEn);
            }
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
        return tourRepository.getTourEntitiesByCountryEntityName(country).stream()
                .map(TourEntity::toTourDto)
                .toList();
    }

    @Override
    public List<TourDto> getToursInPriceRange(BigDecimal from, BigDecimal to) {
        return tourRepository.getTourEntitiesByPricePerPersonBetween(from, to).stream()
                .map(TourEntity::toTourDto)
                .toList();
    }

    @Override
    public List<TourDto> getToursCheaperThan(BigDecimal priceTo) {
        return tourRepository.getTourEntitiesByPricePerPersonBefore(priceTo).stream()
                .map(TourEntity::toTourDto)
                .toList();
    }

    @Override
    public List<TourDto> getToursMoreExpensiveThan(BigDecimal priceFrom) {
        return tourRepository.getTourEntitiesByPricePerPersonAfter(priceFrom).stream()
                .map(TourEntity::toTourDto)
                .toList();
    }

    @Override
    public List<TourDto> getToursInRange(LocalDate from, LocalDate to) {
        return tourRepository.getTourEntitiesByStartDateAfterAndEndDateBefore(from, to).stream()
                .map(TourEntity::toTourDto)
                .toList();
    }

    @Override
    public List<TourDto> getToursAfterDate(LocalDate from) {
        return tourRepository.getTourEntitiesByStartDateAfter(from).stream()
                .map(TourEntity::toTourDto)
                .toList();
    }

    @Override
    public List<TourDto> getToursBeforeDate(LocalDate to) {
        return tourRepository.getTourEntitiesByEndDateBefore(to).stream()
                .map(TourEntity::toTourDto)
                .toList();
    }

    @Override
    public TourDto createTour(TourDto tourDto) {
        var agency = travelAgencyRepository.getTravelAgencyEntityByName(tourDto.agencyName())
                .orElseThrow(() -> new IllegalArgumentException("There is no Travel Agency with given name"));
        var country = countryRepository.getCountryEntityByName(tourDto.countryName())
                .orElseThrow(() -> new IllegalArgumentException("There is no country with given name"));

        var tourToSave = tourRepository.save(TourEntity.builder()
                .travelAgencyEntity(agency)
                .countryEntity(country)
                .pricePerPerson(tourDto.pricePerPerson())
                .startDate(tourDto.startDate())
                .endDate(tourDto.endDate())
                .build());
        tourRepository.save(tourToSave);
        return tourToSave.toTourDto();
    }
}
