package com.app.persistence.model.tour;

import com.app.entity.CountryEntity;
import com.app.entity.TourEntity;
import com.app.entity.TravelAgencyEntity;
import com.app.persistence.xml.adapter.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@ToString
@EqualsAndHashCode
public class TourData {
    @XmlElement
    protected TravelAgencyEntity travelAgencyEntity;
    @XmlElement
    protected CountryEntity countryEntity;
    @XmlElement
    protected BigDecimal pricePerPerson;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startDate;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate endDate;

    public TourEntity toTour() {
        return TourEntity.builder()
                .travelAgencyEntity(travelAgencyEntity)
                .countryEntity(countryEntity)
                .pricePerPerson(pricePerPerson)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
