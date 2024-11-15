package com.app.entity.agency;

import com.app.dto.travel_agency.GetTravelAgencyDto;
import com.app.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "travel_agencies")
@Entity
@Setter
@Getter
public class TravelAgency extends BaseEntity {
    protected String name;
    protected String city;
    protected String phoneNumber;

    public GetTravelAgencyDto toGetTravelAgencyDto() {
        return new GetTravelAgencyDto(name, city, phoneNumber);
    }
}
