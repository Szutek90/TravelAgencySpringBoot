package com.app.controller;

import com.app.controller.dto.ResponseDto;
import com.app.dto.CountryDto;
import com.app.dto.reservation.CreateReservationDto;
import com.app.dto.reservation.GetReservationDto;
import com.app.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spark.ResponseTransformer;

import java.util.List;

import static spark.Spark.*;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    public List<ResponseDto<GetReservationDto>> getAllReservations() {
        return reservationService.getAllReservations().stream()
                .map(ResponseDto::new)
                .toList();
    }

    @PostMapping
    public void createReservation(@RequestBody CreateReservationDto createReservationDto) {
        reservationService.makeReservation(createReservationDto);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Integer id) {
        reservationService.deleteReservation(id);
    }

    @GetMapping("/most/trips")
    public List<ResponseDto<CountryDto>> getMostVisitedCountries() {
        return reservationService.getMostVisitedCountries().stream()
                .map(ResponseDto::new)
                .toList();
    }

    @GetMapping("/most/trips")


    public void routes() {
        path("/reservation", () -> {
            path("/most", () -> {
                get("/trips", (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return new ResponseDto<>(reservationService.getAgencyWithMostOrganizedTrips());
                        },
                        responseTransformer);
                get("/money", (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return new ResponseDto<>(reservationService.getAgencyEarnMostMoney());
                        },
                        responseTransformer);
            });
            path("/summary", () -> get("", (request, response) -> {
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(reservationService.getSummaryByTourAvgPrice());
                    },
                    responseTransformer));
        });
    }
}
