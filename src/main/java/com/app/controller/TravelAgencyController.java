package com.app.controller;

import com.app.controller.dto.ResponseDto;
import com.app.dto.TravelAgencyDto;
import com.app.dto.travel_agency.GetTravelAgencyDto;
import com.app.service.TravelAgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("travel-agency")
@RequiredArgsConstructor
public class TravelAgencyController {
    private final TravelAgencyService service;

    @GetMapping
    public List<ResponseDto<GetTravelAgencyDto>> getAllTravelAgencies() {
        return service.getAllTravelAgency().stream()
                .map(ResponseDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseDto<GetTravelAgencyDto> getTravelAgencyById(@PathVariable Integer id) {
        return new ResponseDto<>(service.getTravelAgencyById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseDto<GetTravelAgencyDto> getTravelAgencyByName(@PathVariable String name) {
        return new ResponseDto<>(service.getTravelAgencyByName(name));
    }

    @GetMapping("/city/{name}")
    public List<ResponseDto<GetTravelAgencyDto>> getTravelAgenciesByCity(@PathVariable String name) {
        return service.getAllTravelAgenciesByCity(name).stream()
                .map(ResponseDto::new)
                .toList();
    }

    @PostMapping
    public ResponseDto<GetTravelAgencyDto> createTravelAgency(@RequestBody TravelAgencyDto dto) {
        return new ResponseDto<>(service.addTravelAgency(dto));
    }
}
