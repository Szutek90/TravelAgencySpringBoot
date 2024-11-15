package com.app.converter.countries.impl;

import com.app.converter.countries.FileToCountriesConverter;
import com.app.entity.country.CountryEntity;
import com.app.persistence.model.country.CountriesData;
import com.app.persistence.xml.deserializer.XmlFileDeserializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.List;

@Component
@RequiredArgsConstructor
public class XmlFileToCountriesConverterImpl implements FileToCountriesConverter {
    private final XmlFileDeserializer<CountriesData> deserializer;

    @Override
    public List<CountryEntity> convert(String filename) {
        return deserializer.deserializeFromFile(Paths.get(filename)).getConvertedToCountries();
    }
}
