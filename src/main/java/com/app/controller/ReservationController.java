package com.app.controller;

import com.app.controller.dto.ResponseDto;
import com.app.dto.reservation.CreateReservationDto;
import com.app.service.ReservationService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spark.ResponseTransformer;

import static spark.Spark.*;

@Component
@RequiredArgsConstructor
public class ReservationController {
    private final ResponseTransformer responseTransformer;
    private final ReservationService reservationService;
    private final Gson gson;

    public void routes() {
        path("/reservation", () -> {
            get("", (request, response) -> {
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(reservationService.getAllReservations());
                    },
                    responseTransformer);

            post("", (request, response) -> {
                        var reservationToAdd = gson
                                .fromJson(request.body(), CreateReservationDto.class);
                        response.header("Content-Type", "application/json;charset=utf-8");
                        response.status(204);
                        reservationService.makeReservation(reservationToAdd);
                        return new ResponseDto<>(null);
                    },
                    responseTransformer);
            delete(
                    "/:id",
                    (request, response) -> {
                        var id = Integer.parseInt(request.params("id"));
                        reservationService.deleteReservation(id);
                        response.header("Content-Type", "application/json;charset=utf-8");
                        response.status(204);
                        return new ResponseDto<>(null);
                    },
                    responseTransformer
            );
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
                get("/visit", (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return new ResponseDto<>(reservationService.getMostVisitedCountries());
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
