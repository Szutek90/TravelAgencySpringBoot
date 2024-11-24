package com.app.service.impl;

import com.app.dto.CountryDto;
import com.app.dto.TourDto;
import com.app.dto.TravelAgencyDto;
import com.app.dto.ReservationDto;
import com.app.entity.*;
import com.app.repository.*;
import com.app.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final TourRepository tourRepository;
    private final PersonRepository personRepository;
    private final TravelAgencyRepository travelAgencyRepository;
    private final CountryRepository countryRepository;

    @Override
    public void makeReservation(ReservationDto reservationDto) {
        if (tourRepository.findById(reservationDto.tourId()).isEmpty()) {
            throw new IllegalStateException("tour not found");
        }
        var travelAgency = travelAgencyRepository.getTravelAgencyEntityByName(reservationDto.agencyName())
                .orElseThrow(() ->
                        new IllegalArgumentException("There is no Travel Agency with given name"));
        var customer = personRepository.getPersonEntityByEmail(reservationDto.person().email())
                .orElseThrow(() -> new IllegalArgumentException("There is no Customer with given email"));

        reservationRepository.save(
                ReservationEntity.builder()
                        .tourEntity(tourRepository.findById(reservationDto.tourId())
                                .orElseThrow((() -> new IllegalArgumentException("There is no tour with given id"))))
                        .agencyId(travelAgency.getId())
                        .customerId(customer.getId())
                        .quantityOfPeople(reservationDto.quantityOfPeople())
                        .discount(reservationDto.discount())
                        .components(reservationDto.reservationComponents())
                        .build());
    }

    @Override
    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        var allReservations = reservationRepository.findAll();
        return allReservations
                .stream()
                .map(ReservationEntity::toReservationDto)
                .toList();
    }

    @Override
    public List<TravelAgencyDto> getAgencyWithMostOrganizedTrips() {
        return reservationRepository.findAll().stream()
                .map(reservation -> travelAgencyRepository.findById(reservation.getAgencyId())
                        .orElseThrow(() -> new IllegalStateException("Travel Agency not found")))
                .collect(Collectors.groupingBy(
                        TravelAgencyEntity::getId, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .flatMap(travelAgencyRepository::findById)
                .stream()
                .map(TravelAgencyEntity::toTravelAgencyDto)
                .toList();
    }

    @Override
    public List<TravelAgencyDto> getAgencyEarnMostMoney() {
        return reservationRepository.findAll().stream()
                .collect(Collectors.groupingBy(e -> e.getTourEntity().getPricePerPerson()
                                .multiply(BigDecimal.valueOf(e.getQuantityOfPeople())),
                        Collectors.mapping(e ->
                                        travelAgencyRepository.findById(e.getAgencyId())
                                                .orElseThrow(() ->
                                                        new IllegalStateException("Travel Agency not found")),
                                Collectors.toList())))
                .entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow()
                .stream()
                .map(TravelAgencyEntity::toTravelAgencyDto)
                .toList();
    }

    @Override
    public List<CountryDto> getMostVisitedCountries() {
        var countriesWithChoicesNum = reservationRepository.findAll().stream()
                .collect(Collectors.groupingBy(e -> countryRepository
                        .findById((tourRepository.findById(e.getTourEntity().getId()).orElseThrow()).getCountryEntity().getId())
                        .orElseThrow(), Collectors.counting()));
        return countriesWithChoicesNum.entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList()
                        ))).entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow()
                .stream()
                .map(CountryEntity::toCountryDto)
                .toList();
    }

    @Override
    public Map<TravelAgencyDto, TourWithClosestAvgPriceByAgency> getSummaryByTourAvgPrice() {
        var averagePriceByAgency = tourRepository.findAll().stream()
                .collect(Collectors.groupingBy(e->e.getTravelAgencyEntity().getId(),
                        Collectors.mapping(TourEntity::getPricePerPerson,
                                Collectors.toList())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .divide(new BigDecimal(e.getValue().size()), RoundingMode.HALF_UP)));
        return averagePriceByAgency.entrySet().stream()
                .collect(Collectors.toMap(e -> travelAgencyRepository.findById(e.getKey())
                        .orElseThrow().toTravelAgencyDto(), e -> {
                    var closest = tourRepository.findAll().stream()
                            .filter(t -> Objects.equals(t.getTravelAgencyEntity().getId(), e.getKey()))
                            .min(Comparator.comparing(t -> t.getPricePerPerson()
                                    .subtract(e.getValue()).abs()))
                            .orElseThrow();
                    return new TourWithClosestAvgPriceByAgency(e.getValue(), closest);
                }));
    }

    @Override
    public List<TourDto> getToursTakingPlaceInGivenCountry(List<String> countryNames) {
        var tours = new ArrayList<TourDto>();
        for (String countryName : countryNames) {
            tours.addAll(tourRepository.getTourEntitiesByCountryEntityName(countryName).stream()
                    .map(TourEntity::toTourDto)
                    .toList());
        }
        return tours;
    }

    @Override
    public ReservationComponent saveComponent(Integer id, ReservationComponent item) {
        var reservation = reservationRepository.findById(id).orElseThrow();
        reservation.getComponents().add(item);
        reservationRepository.save(reservation);
        return item;
    }
}