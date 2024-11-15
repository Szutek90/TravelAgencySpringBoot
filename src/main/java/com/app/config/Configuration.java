package com.app.config;

import com.app.persistence.json.deserializer.custom.LocalDateDeserializer;
import com.app.persistence.json.deserializer.custom.LocalDateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .setPrettyPrinting().create();
    }
}
