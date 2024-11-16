package com.app.service;

import com.app.dto.CountryDto;

import java.util.List;

public interface CountryService {
    CountryDto addCountry(CountryDto countryDto);
    List<CountryDto> getAllCountries();
}
