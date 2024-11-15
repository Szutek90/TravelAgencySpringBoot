package com.app.entity.country;

import java.util.function.Function;

public interface CountryEntityMapper {
    Function<CountryEntity, String> toName = country -> country.name;
}
