package com.app.service.impl;

import com.app.converter.agencies.FileToAgenciesConverter;
import com.app.dto.TravelAgencyDto;
import com.app.entity.TravelAgencyEntity;
import com.app.repository.TravelAgencyRepository;
import com.app.service.TravelAgencyService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TravelAgencyServiceImpl implements TravelAgencyService {
    private final TravelAgencyRepository travelAgencyRepository;
    private final ApplicationContext context;

    @Value("${agencies.file}")
    private String filename;

    @Value("${agencies.format}")
    private String format;

    @PostConstruct
    public void init() {
        var converter = context.getBean("%sFileToAgenciesConverterImpl".formatted(format),
                FileToAgenciesConverter.class);
        if (travelAgencyRepository.count() == 0) {
            travelAgencyRepository.saveAll(converter.convert(filename));
        }
    }

    public List<TravelAgencyDto> getAllTravelAgency() {
        return travelAgencyRepository.findAll().stream()
                .map(TravelAgencyEntity::toTravelAgencyDto)
                .toList();
    }

    @Override
    public TravelAgencyDto getTravelAgencyById(int id) {
        return travelAgencyRepository.getTravelAgencyEntityById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("There is no Travel Agency with given id"))
                .toTravelAgencyDto();
    }

    @Override
    public TravelAgencyDto getTravelAgencyByName(String name) {
        return travelAgencyRepository.getTravelAgencyEntityByName(name)
                .orElseThrow(() ->
                        new NoSuchElementException("There is no Travel Agency with given id"))
                .toTravelAgencyDto();
    }

    @Override
    public List<TravelAgencyDto> getAllTravelAgenciesByCity(String city) {
        return travelAgencyRepository.getTravelAgencyEntitiesByCity(city).stream()
                .map(TravelAgencyEntity::toTravelAgencyDto)
                .toList();
    }

    @Override
    public TravelAgencyDto addTravelAgency(TravelAgencyDto travelAgencyDto) {
        if (travelAgencyRepository.getTravelAgencyEntityByName(travelAgencyDto.name()).isPresent()) {
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
