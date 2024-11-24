package com.app.controller;

import com.app.controller.dto.ResponseDto;
import com.app.dto.TourDto;
import com.app.service.TourWithCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tour")
@RequiredArgsConstructor
public class TourWithCountryController {
    private final TourWithCountryService service;

    @GetMapping("/{id}")
    public ResponseDto<TourDto> getById(@PathVariable Integer id) {
        return new ResponseDto<>(service.getById(id));
    }

    @GetMapping("/{countryName}")
    public ResponseDto<List<TourDto>> getByCountry(@PathVariable String countryName) {
        return new ResponseDto<>(service.getByCountry(countryName));
    }

    @GetMapping("/price-range")
    public ResponseDto<List<TourDto>> getByPriceRange(@RequestParam BigDecimal from, @RequestParam BigDecimal to) {
        return new ResponseDto<>(service.getToursInPriceRange(from, to));
    }

    @GetMapping("/price-from")
    public ResponseDto<List<TourDto>> getByPriceFrom(@RequestParam BigDecimal from) {
        return new ResponseDto<>(service.getToursMoreExpensiveThan(from));
    }

    @GetMapping("/price-to")
    public ResponseDto<List<TourDto>> getByPriceTo(@RequestParam BigDecimal to) {
        return new ResponseDto<>(service.getToursCheaperThan(to));
    }

    @GetMapping("/date")
    public ResponseDto<List<TourDto>> getInDateRange(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return new ResponseDto<>(service.getToursInRange(from, to));
    }

    @GetMapping("/date/after")
    public ResponseDto<List<TourDto>> getAfterDate(@RequestParam LocalDate from) {
        return new ResponseDto<>(service.getToursAfterDate(from));
    }

    @GetMapping("/date/before")
    public ResponseDto<List<TourDto>> getBeforeDate(@RequestParam LocalDate to) {
        return new ResponseDto<>(service.getToursBeforeDate(to));
    }
}
