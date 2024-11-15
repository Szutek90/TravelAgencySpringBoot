package com.app.converter.tours.impl;

import com.app.converter.tours.FileToToursConverter;
import com.app.entity.tour.TourEntity;
import com.app.persistence.model.tour.ToursData;
import com.app.persistence.xml.deserializer.XmlFileDeserializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.List;

@Component
@RequiredArgsConstructor
public class XmlFileToToursConverterImpl implements FileToToursConverter {
    private final XmlFileDeserializer<ToursData> deserializer;

    @Override
    public List<TourEntity> convert(String filename) {
        return deserializer.deserializeFromFile(Paths.get(filename)).getConvertedToTours();
    }
}
