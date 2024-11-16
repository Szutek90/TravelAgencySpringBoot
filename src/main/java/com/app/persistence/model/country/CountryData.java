package com.app.persistence.model.country;

import com.app.entity.country.CountryEntity;
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
@XmlRootElement(name = "country")
@EqualsAndHashCode
public class CountryData {
    @XmlElement
    private String name;

    public CountryEntity toCountry() {
        return CountryEntity.builder()
                .name(name)
                .build();
    }
}
