package com.app.entity.agency;

import java.util.function.Function;

public interface TravelAgencyEntityMapper {
    Function<TravelAgencyEntity, String> toName = travelAgency -> travelAgency.name;
    Function<TravelAgencyEntity, String> toCity = travelAgency -> travelAgency.city;
}
