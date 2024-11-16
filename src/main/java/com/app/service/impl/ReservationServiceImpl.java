package com.app.service.impl;

import com.app.dto.reservation.CreateReservationDto;
import com.app.dto.reservation.GetReservationDto;
import com.app.entity.TourWithClosestAvgPriceByAgency;
import com.app.entity.agency.TravelAgencyEntity;
import com.app.entity.agency.TravelAgencyEntityMapper;
import com.app.entity.country.CountryEntity;
import com.app.entity.reservation.ReservationEntity;
import com.app.entity.reservation.ReservationEntityMapper;
import com.app.entity.tour.TourEntity;
import com.app.entity.tour.TourEntityMapper;
import com.app.repository.*;
import com.app.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final TourRepository tourRepository;
    private final PersonRepository personRepository;
    private final TravelAgencyRepository travelAgencyRepository;
    private final CountryRepository countryRepository;
    private final ReservationComponentRepository componentRepository;

    @Override
    public void makeReservation(CreateReservationDto createReservationDto) {
        if (tourRepository.findById(TourEntityMapper.toId.applyAsInt(createReservationDto.tourEntity())).isEmpty()) {
            throw new IllegalStateException("tour not found");
        }
        if (personRepository
                .findById(PersonMapper.toId.applyAsInt(createReservationDto.customer())).isEmpty()) {
            throw new IllegalStateException("person not found");
        }

        var reservation = reservationRepository.save(
                ReservationEntity.builder()
                        .tourId(createReservationDto.tourEntity().getId())
                        .customerId(createReservationDto.customer().getId())
                        .agencyId(TravelAgencyEntityMapper.toId.applyAsInt(createReservationDto.travelAgencyEntity()))
                        .quantityOfPeople(createReservationDto.quantityOfPeople())
                        .discount(createReservationDto.discount())
                        .build());
        var reservationId = ReservationEntityMapper.toId.applyAsInt(reservation);
        var components = createReservationDto.reservationComponents();
        for (var component : components) {
            componentRepository.save(reservationId, component);
        }
    }

    @Override
    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<GetReservationDto> getAllReservations() {
        var allReservations = reservationRepository.findAll();
        return allReservations
                .stream()
                .map(this::toGetReservationDto)
                .toList();
    }

    @Override
    public List<TravelAgencyEntity> getAgencyWithMostOrganizedTrips() {
        return reservationRepository.findAll().stream()
                .collect(Collectors.groupingBy(ReservationEntityMapper.toId::applyAsInt,
                        Collectors.mapping(e ->
                                        travelAgencyRepository.findById(ReservationEntityMapper.toAgencyId.applyAsInt(e))
                                                .orElseThrow(() ->
                                                        new IllegalStateException("Travel Agency not found")),
                                Collectors.toList())))
                .entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow();
    }

    @Override
    public List<TravelAgencyEntity> getAgencyEarnMostMoney() {
        return reservationRepository.findAll().stream()
                .collect(Collectors.groupingBy(e -> TourEntityMapper.toPrice
                                .apply(tourRepository.findById(ReservationEntityMapper.toTourId.applyAsInt(e))
                                        .orElseThrow())
                                .multiply(BigDecimal.valueOf(ReservationEntityMapper.toQuantityOfPeople
                                        .applyAsInt(e))),
                        Collectors.mapping(e ->
                                        travelAgencyRepository.findById(ReservationEntityMapper.toAgencyId.applyAsInt(e))
                                                .orElseThrow(() ->
                                                        new IllegalStateException("Travel Agency not found")),
                                Collectors.toList())))
                .entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow();
    }

    @Override
    public List<CountryEntity> getMostVisitedCountries() {
        var countriesWithChoicesNum = reservationRepository.findAll().stream()
                .collect(Collectors.groupingBy(e -> countryRepository
                                .findById(TourEntityMapper.toCountryId.applyAsInt(tourRepository
                                        .findById(ReservationEntityMapper.toTourId.applyAsInt(e)).orElseThrow())).orElseThrow(),
                        Collectors.counting()));
        return countriesWithChoicesNum.entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue,
                        Collectors.mapping(
                                Map.Entry::getKey,
                                Collectors.toList()
                        ))).entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow();
    }

    @Override
    public Map<TravelAgencyEntity, TourWithClosestAvgPriceByAgency> getSummaryByTourAvgPrice() {
        var averagePriceByAgency = tourRepository.findAll().stream()
                .collect(Collectors.groupingBy(TourEntityMapper.toAgencyId::applyAsInt,
                        Collectors.mapping(TourEntityMapper.toPrice, Collectors.toList())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .divide(new BigDecimal(e.getValue().size()), RoundingMode.HALF_UP)));
        return averagePriceByAgency.entrySet().stream()
                .collect(Collectors.toMap(e -> travelAgencyRepository.findById(e.getKey()).orElseThrow(), e -> {
                    var closest = tourRepository.findAll().stream()
                            .filter(t -> TourEntityMapper.toAgencyId.applyAsInt(t) == e.getKey())
                            .min(Comparator.comparing(t -> TourEntityMapper.toPrice.apply(t).subtract(e.getValue()).abs()))
                            .orElseThrow();
                    return new TourWithClosestAvgPriceByAgency(e.getValue(), closest);
                }));
    }

    @Override
    public List<TourEntity> getToursTakingPlaceInGivenCountry(List<String> countryNames) {
        var tours = new ArrayList<TourEntity>();
        for (String countryName : countryNames) {
            tours.addAll(tourRepository.getByCountryName(countryName));
        }
        return tours;
    }

    @Override
    public GetReservationDto toGetReservationDto(ReservationEntity reservationEntity) {
        return new GetReservationDto(
                tourRepository.findById(ReservationEntityMapper.toTourId.applyAsInt(reservationEntity))
                        .orElseThrow(() -> new IllegalArgumentException("No tour with given id")),
                travelAgencyRepository.findById(ReservationEntityMapper.toAgencyId.applyAsInt(reservationEntity))
                        .orElseThrow(() -> new IllegalArgumentException("No agency with given id")),
                personRepository.findById(ReservationEntityMapper.toPersonId.applyAsInt(reservationEntity))
                        .orElseThrow(() -> new IllegalArgumentException("No person with given id")),
                ReservationEntityMapper.toQuantityOfPeople.applyAsInt(reservationEntity),
                ReservationEntityMapper.toDiscount.applyAsInt(reservationEntity),
                componentRepository
                        .findByReservationId(ReservationEntityMapper.toId.applyAsInt(reservationEntity)));
    }

}