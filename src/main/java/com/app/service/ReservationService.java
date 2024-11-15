package com.app.service;

import com.app.dto.reservation.CreateReservationDto;
import com.app.dto.reservation.GetReservationDto;
import com.app.entity.TourWithClosestAvgPriceByAgency;
import com.app.entity.agency.TravelAgency;
import com.app.entity.country.Country;
import com.app.entity.reservation.Reservation;
import com.app.entity.tour.Tour;

import java.util.List;
import java.util.Map;

public interface ReservationService {
    void makeReservation(CreateReservationDto createReservationDto);

    void deleteReservation(int id);
    List<GetReservationDto> getAllReservations();

    List<TravelAgency> getAgencyWithMostOrganizedTrips();

    List<TravelAgency> getAgencyEarnMostMoney();

    List<Country> getMostVisitedCountries();

    Map<TravelAgency, TourWithClosestAvgPriceByAgency> getSummaryByTourAvgPrice();

    List<Tour> getToursTakingPlaceInGivenCountry(List<String> countryNames);

    GetReservationDto toGetReservationDto(Reservation reservation);
}
