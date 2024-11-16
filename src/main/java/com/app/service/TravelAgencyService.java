package com.app.service;

import com.app.dto.TravelAgencyDto;

import java.util.List;

public interface TravelAgencyService {
    List<TravelAgencyDto> getAllTravelAgency();
    TravelAgencyDto getTravelAgencyById(int id);

    TravelAgencyDto getTravelAgencyByName(String name);

    List<TravelAgencyDto> getAllTravelAgenciesByCity(String city);

    TravelAgencyDto addTravelAgency(TravelAgencyDto travelAgencyDto);
}
