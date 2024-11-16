package com.app.service;

import com.app.dto.CountryDto;
import com.app.dto.TourDto;
import com.app.dto.TravelAgencyDto;
import com.app.dto.ReservationDto;
import com.app.entity.TourWithClosestAvgPriceByAgency;

import java.util.List;
import java.util.Map;

public interface ReservationService {
    void makeReservation(ReservationDto createReservationDto);

    void deleteReservation(int id);
    List<ReservationDto> getAllReservations();

    List<TravelAgencyDto> getAgencyWithMostOrganizedTrips();

    List<TravelAgencyDto> getAgencyEarnMostMoney();

    List<CountryDto> getMostVisitedCountries();

    Map<TravelAgencyDto, TourWithClosestAvgPriceByAgency> getSummaryByTourAvgPrice();

    List<TourDto> getToursTakingPlaceInGivenCountry(List<String> countryNames);
}
