package com.app.controller;

import com.app.controller.dto.ResponseDto;
import com.app.dto.country.CreateCountryDto;
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
    public List<ResponseDto<CountryDto>> getAllCountries() {
        return countryService.getAllCountries().stream()
                .map(ResponseDto::new)
                .toList();
    }

    @PostMapping
    public ResponseDto<CountryDto> createCountry(@RequestBody CreateCountryDto createCountryDto) {
        return new ResponseDto<>(countryService.addCountry(createCountryDto));
    }
}
