package com.app.converter.countries.impl;

import com.app.converter.countries.FileToCountriesConverter;
import com.app.entity.CountryEntity;
import com.app.persistence.json.deserializer.JsonDeserializer;
import com.app.persistence.model.country.CountriesData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JsonFileToCountriesConverterImpl implements FileToCountriesConverter {
    private final JsonDeserializer<CountriesData> deserializer;

    @Override
    public List<CountryEntity> convert(String filename) {
        return deserializer.deserialize(filename).getConvertedToCountries();
    }
}
