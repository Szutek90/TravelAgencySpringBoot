package com.app.repository.impl;

import com.app.converter.countries.FileToCountriesConverter;
import com.app.entity.country.Country;
import com.app.repository.CountryRepository;
import com.app.repository.generic.AbstractCrudRepository;
import com.app.repository.generic.EntityManagerWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Repository
public class CountryRepositoryImpl extends AbstractCrudRepository<Country, Integer> implements CountryRepository {
    private final ApplicationContext context;

    @Value("${countries.file}")
    String filename;

    @Value("${countries.format}")
    String format;

    public CountryRepositoryImpl(Jdbi jdbi, ApplicationContext context) {
        super(jdbi);
        this.context = context;
    }

    @PostConstruct
    public void init() {
        var converter = context.getBean("%sFileToCountriesConverterImpl".formatted(format)
                , FileToCountriesConverter.class);
        if (findAll().isEmpty()) {
            saveAll(converter.convert(filename));
        }
    }


    @Override
    public Optional<Country> findByCountry(String countryName) {
        //TODO 7. Czy przy odczycie niezbędne jest używania transakcji?
        EntityManager em = null;
        EntityTransaction tx = null;
        Optional<Country> country = Optional.empty();
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            country = Optional.ofNullable(em.createQuery("select c from Country c where c.name =: countryName",
                            Country.class)
                    .setParameter("countryName", countryName)
                    .getSingleResult());
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return country;
        var sql = "SELECT * FROM %s WHERE name = :countryName".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("countryName", countryName)
                .mapToBean(entityType)
                .findFirst());
    }
}
