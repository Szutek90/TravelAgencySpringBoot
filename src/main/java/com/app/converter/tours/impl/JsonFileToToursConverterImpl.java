package com.app.converter.tours.impl;

import com.app.converter.tours.FileToToursConverter;
import com.app.entity.tour.TourEntity;
import com.app.persistence.json.deserializer.JsonDeserializer;
import com.app.persistence.model.tour.ToursData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JsonFileToToursConverterImpl implements FileToToursConverter {
    private final JsonDeserializer<ToursData> deserializer;

    @Override
    public List<TourEntity> convert(String filename) {
        return deserializer.deserialize(filename).getConvertedToTours();
    }
}
