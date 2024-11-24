package com.app.service.impl;

import com.app.converter.countries.FileToCountriesConverter;
import com.app.dto.CountryDto;
import com.app.entity.CountryEntity;
import com.app.repository.CountryRepository;
import com.app.service.CountryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final ApplicationContext context;

    @Value("${countries.file}")
    String filename;

    @Value("${countries.format}")
    String format;

    @PostConstruct
    public void init() {
        var converter = context.getBean("%sFileToCountriesConverterImpl".formatted(format)
                , FileToCountriesConverter.class);
        if (countryRepository.count() == 0) {
            countryRepository.saveAll(converter.convert(filename));
        }
    }

    public CountryDto addCountry(CountryDto countryDto) {
        if (countryRepository.findByName(countryDto.name()).isPresent()) {
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
