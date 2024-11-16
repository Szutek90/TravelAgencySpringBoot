package com.app.controller;

import com.app.controller.dto.ResponseDto;
import com.app.dto.CountryDto;
import com.app.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/country")
public class CountryController {
    private final CountryService countryService;

    @GetMapping
    public ResponseDto<List<CountryDto>> getAllCountries() {
        return new ResponseDto<>(countryService.getAllCountries());
    }

    @PostMapping
    public ResponseDto<CountryDto> createCountry(@RequestBody CountryDto createCountryDto) {
        return new ResponseDto<>(countryService.addCountry(createCountryDto));
    }
}
