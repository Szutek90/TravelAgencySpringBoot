package com.app.converter.countries;

import com.app.converter.Converter;
import com.app.entity.country.CountryEntity;

import java.util.List;

public interface FileToCountriesConverter extends Converter<String, List<CountryEntity>> {
}
