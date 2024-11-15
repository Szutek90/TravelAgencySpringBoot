package com.app.service;

import com.app.dto.country.CreateCountryDto;
import com.app.dto.CountryDto;

import java.util.List;

public interface CountryService {
    CountryDto addCountry(CreateCountryDto countryDto);
    List<CountryDto> getAllCountries();
}
