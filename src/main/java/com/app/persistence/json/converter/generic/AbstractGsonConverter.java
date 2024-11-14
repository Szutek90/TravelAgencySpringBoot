package com.app.persistence.json.converter.generic;

import com.app.persistence.json.converter.JsonConverter;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import java.io.FileReader;

@RequiredArgsConstructor
public class AbstractGsonConverter<T> implements JsonConverter<T> {
    private final Gson gson;

    @Override
    public T fromJson(FileReader fileReader, Class<T> type) {
        return gson.fromJson(fileReader, type);
    }
}
