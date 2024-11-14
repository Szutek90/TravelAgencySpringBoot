package com.app.model.agency;

import com.app.dto.travel_agency.GetTravelAgencyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Builder
public class TravelAgency {
    protected final Integer id;
    protected final String name;
    protected final String city;
    protected final String phoneNumber;

    public GetTravelAgencyDto toGetTravelAgencyDto() {
        return new GetTravelAgencyDto(name, city, phoneNumber);
    }
}
