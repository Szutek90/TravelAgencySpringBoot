package com.app.persistence.model.tour;

import com.app.entity.tour.TourEntity;
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
    protected Integer agencyId;
    @XmlElement
    protected Integer countryId;
    @XmlElement
    protected BigDecimal pricePerPerson;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startDate;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate endDate;

    public TourEntity toTour() {
        return new TourEntity(agencyId, countryId, pricePerPerson, startDate, endDate,
                null, null, null);
    }
}
