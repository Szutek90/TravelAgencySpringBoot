package com.app.persistence.model.agency;

import com.app.entity.agency.TravelAgency;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "travelAgency")
@EqualsAndHashCode
public class TravelAgencyData {
    @XmlElement
    private String name;
    @XmlElement
    private String city;
    @XmlElement
    private String phoneNumber;

    public TravelAgency toTravelAgency(){
        return new TravelAgency(name, city, phoneNumber);
    }
}
