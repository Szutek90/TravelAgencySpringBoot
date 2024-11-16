package com.app.controller;

import com.app.controller.dto.ResponseDto;
import com.app.dto.CountryDto;
import com.app.dto.TravelAgencyDto;
import com.app.dto.ReservationDto;
import com.app.entity.TourWithClosestAvgPriceByAgency;
import com.app.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    public ResponseDto<List<ReservationDto>> getAllReservations() {
        return new ResponseDto<>(reservationService.getAllReservations());
    }

    @PostMapping
    public void createReservation(@RequestBody ReservationDto createReservationDto) {
        reservationService.makeReservation(createReservationDto);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Integer id) {
        reservationService.deleteReservation(id);
    }

    @GetMapping("/most/trips")
    public ResponseDto<List<CountryDto>> getMostVisitedCountries() {
        return new ResponseDto<>(reservationService.getMostVisitedCountries());
    }

    @GetMapping("/most/trips")
    public ResponseDto<List<TravelAgencyDto>> getMostOrganizedTrips() {
        return new ResponseDto<>(reservationService.getAgencyWithMostOrganizedTrips());
    }

    @GetMapping("most/money")
    public ResponseDto<List<TravelAgencyDto>> getAgenciesEarnMostMoney() {
        return new ResponseDto<>(reservationService.getAgencyEarnMostMoney());
    }

    @GetMapping("/summary")
    public ResponseDto<Map<TravelAgencyDto, TourWithClosestAvgPriceByAgency>> getSummary() {
        return new ResponseDto<>(reservationService.getSummaryByTourAvgPrice());
    }

}

