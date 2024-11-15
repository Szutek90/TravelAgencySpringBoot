package com.app.service;

import com.app.dto.CountryDto;
import com.app.dto.reservation.CreateReservationDto;
import com.app.dto.reservation.GetReservationDto;
import com.app.dto.travel_agency.GetTravelAgencyDto;
import com.app.entity.TourWithClosestAvgPriceByAgency;
import com.app.entity.agency.TravelAgencyEntity;
import com.app.entity.reservation.ReservationEntity;
import com.app.entity.tour.TourEntity;

import java.util.List;
import java.util.Map;

public interface ReservationService {
    void makeReservation(CreateReservationDto createReservationDto);

    void deleteReservation(int id);
    List<GetReservationDto> getAllReservations();

    List<GetTravelAgencyDto> getAgencyWithMostOrganizedTrips();

    List<GetTravelAgencyDto> getAgencyEarnMostMoney();

    List<CountryDto> getMostVisitedCountries();

    Map<TravelAgencyEntity, TourWithClosestAvgPriceByAgency> getSummaryByTourAvgPrice();

    List<TourEntity> getToursTakingPlaceInGivenCountry(List<String> countryNames);

    GetReservationDto toGetReservationDto(ReservationEntity reservationEntity);
}
