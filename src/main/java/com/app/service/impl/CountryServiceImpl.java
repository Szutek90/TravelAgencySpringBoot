package com.app.service.impl;

import com.app.dto.CountryDto;
import com.app.entity.country.CountryEntity;
import com.app.repository.CountryRepository;
import com.app.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public CountryDto addCountry(CountryDto countryDto) {
        if (countryRepository.findByCountry(countryDto.name()).isPresent()) {
            throw new IllegalArgumentException("Country already exists");
        }

        var addedCountry = countryRepository.save(CountryEntity.builder()
                .name(countryDto.name())
                .build());
        return addedCountry.toCountryDto();
    }

    public List<CountryDto> getAllCountries() {
        return countryRepository.findAll()
                .stream()
                .map(CountryEntity::toCountryDto)
                .toList();
    }
}
