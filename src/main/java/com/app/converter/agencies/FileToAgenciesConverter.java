package com.app.converter.agencies;

import com.app.converter.Converter;
import com.app.entity.agency.TravelAgencyEntity;

import java.util.List;

public interface FileToAgenciesConverter extends Converter<String, List<TravelAgencyEntity>> {
}
