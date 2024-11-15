package com.app.repository.impl;

import com.app.converter.countries.FileToCountriesConverter;
import com.app.entity.country.CountryEntity;
import com.app.repository.CountryRepository;
import com.app.repository.generic.AbstractCrudRepository;
import com.app.repository.generic.EntityManagerWrapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CountryRepositoryImpl extends AbstractCrudRepository<CountryEntity, Integer> implements CountryRepository {
    private final ApplicationContext context;

    @Value("${countries.file}")
    String filename;

    @Value("${countries.format}")
    String format;

    public CountryRepositoryImpl(EntityManagerFactory emf, ApplicationContext context) {
        super(emf);
        this.context = context;
    }

    //TODO czy to jest poprawne - import jest z jakarta.annotation, przy Sparku było javax.annotation
    @PostConstruct
    public void init() {
        var converter = context.getBean("%sFileToCountriesConverterImpl".formatted(format)
                , FileToCountriesConverter.class);
        if (findAll().isEmpty()) {
            saveAll(converter.convert(filename));
        }
    }


    @Override
    public Optional<CountryEntity> findByCountry(String countryName) {
        //TODO 7. Czy przy odczycie niezbędne jest używania transakcji? Przeczytałem w paru miejscach, że robi się
        // to bez użycia transakcji
        //
        EntityManager em = null;
        EntityTransaction tx = null;
        Optional<CountryEntity> country = Optional.empty();
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            country = Optional.ofNullable(em.createQuery("select c from CountryEntity c where c.name =: countryName",
                            CountryEntity.class)
                    .setParameter("countryName", countryName)
                    .getSingleResult());

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return country;
    }
}
