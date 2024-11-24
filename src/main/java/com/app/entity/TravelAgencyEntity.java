package com.app.entity;

import com.app.dto.TravelAgencyDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Table(name = "travel_agencies")
@Entity
@Setter
@Getter
public class TravelAgencyEntity extends BaseEntity {
    protected String name;
    protected String city;
    protected String phoneNumber;

    public TravelAgencyDto toTravelAgencyDto() {
        return new TravelAgencyDto(name, city, phoneNumber);
    }

    @OneToMany(mappedBy = "travelAgencyEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TourEntity> tourEntities = new ArrayList<>();
}
