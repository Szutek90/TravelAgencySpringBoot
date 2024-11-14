package com.app.persistence.json.deserializer.impl;

import com.app.persistence.json.converter.JsonConverter;
import com.app.persistence.json.deserializer.JsonDeserializer;
import com.app.persistence.json.deserializer.generic.AbstractJsonDeserializer;
import com.app.persistence.model.country.CountriesData;
import org.springframework.stereotype.Component;

@Component
public class CountriesJsonDeserializer extends AbstractJsonDeserializer<CountriesData> implements JsonDeserializer<CountriesData> {
    public CountriesJsonDeserializer(JsonConverter<CountriesData> converter) {
        super(converter);
    }
}
