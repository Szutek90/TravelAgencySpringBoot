package com.app.service.impl;

import com.app.dto.TravelAgencyDto;
import com.app.entity.agency.TravelAgencyEntity;
import com.app.repository.TravelAgencyRepository;
import com.app.service.TravelAgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TravelAgencyServiceImpl implements TravelAgencyService {
    private final TravelAgencyRepository travelAgencyRepository;

    public List<TravelAgencyDto> getAllTravelAgency() {
        return travelAgencyRepository.findAll().stream()
                .map(TravelAgencyEntity::toTravelAgencyDto)
                .toList();
    }

    @Override
    public TravelAgencyDto getTravelAgencyById(int id) {
        return travelAgencyRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("There is no Travel Agency with given id"))
                .toTravelAgencyDto();
    }

    @Override
    public TravelAgencyDto getTravelAgencyByName(String name) {
        return travelAgencyRepository.findByName(name)
                .orElseThrow(() ->
                        new NoSuchElementException("There is no Travel Agency with given id"))
                .toTravelAgencyDto();
    }

    @Override
    public List<TravelAgencyDto> getAllTravelAgenciesByCity(String city) {
        return travelAgencyRepository.findByCity(city).stream()
                .map(TravelAgencyEntity::toTravelAgencyDto)
                .toList();
    }

    @Override
    public TravelAgencyDto addTravelAgency(TravelAgencyDto travelAgencyDto) {
        if (travelAgencyRepository.findByName(travelAgencyDto.name()).isPresent()) {
            throw new IllegalArgumentException("There is already a Travel Agency with given name");
        }
        var agencyToSave = TravelAgencyEntity.builder()
                .name(travelAgencyDto.name())
                .city(travelAgencyDto.city())
                .phoneNumber(travelAgencyDto.phoneNumber())
                .build();
        travelAgencyRepository.save(agencyToSave);
        return agencyToSave.toTravelAgencyDto();
    }

}
