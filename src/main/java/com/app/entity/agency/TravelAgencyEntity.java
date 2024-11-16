package com.app.entity.agency;

import com.app.dto.TravelAgencyDto;
import com.app.entity.BaseEntity;
import com.app.entity.tour.TourEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
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

    @OneToMany(mappedBy = "travel_agency", cascade = CascadeType.ALL)
    private List<TourEntity> tourEntities = new ArrayList<>();
}
