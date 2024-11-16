package com.app.repository.impl;

import com.app.converter.agencies.FileToAgenciesConverter;
import com.app.entity.agency.TravelAgencyEntity;
import com.app.repository.TravelAgencyRepository;
import com.app.repository.generic.AbstractCrudRepository;
import com.app.repository.generic.EntityManagerWrapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TravelAgencyRepositoryImpl extends AbstractCrudRepository<TravelAgencyEntity, Integer> implements TravelAgencyRepository {
    private final ApplicationContext context;

    @Value("${agencies.file}")
    private String filename;

    @Value("${agencies.format}")
    private String format;

    public TravelAgencyRepositoryImpl(EntityManagerFactory emf, ApplicationContext context) {
        super(emf);
        this.context = context;
    }

    @PostConstruct
    public void init() {
        var converter = context.getBean("%sFileToAgenciesConverterImpl".formatted(format),
                FileToAgenciesConverter.class);
        if (findAll().isEmpty()) {
            saveAll(converter.convert(filename));
        }
    }

    @Override
    public Optional<TravelAgencyEntity> findById(int id) {
        EntityManager em;
        EntityTransaction tx = null;
        Optional<TravelAgencyEntity> travelAgency = Optional.empty();
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            travelAgency = Optional.ofNullable(em.find(TravelAgencyEntity.class, id));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return travelAgency;
    }

    @Override
    public Optional<TravelAgencyEntity> findByName(String name) {
        EntityManager em;
        EntityTransaction tx = null;
        Optional<TravelAgencyEntity> travelAgency = Optional.empty();
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            travelAgency = Optional.ofNullable(em.createQuery("select t from TravelAgencyEntity t " +
                            "where t.name = :name", TravelAgencyEntity.class)
                    .setParameter("name", name)
                    .getSingleResult());
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return travelAgency;
    }

    @Override
    public List<TravelAgencyEntity> findByCity(String city) {
        EntityManager em;
        EntityTransaction tx = null;
        List<TravelAgencyEntity> travelAgency = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            travelAgency = em.createQuery("select t from TravelAgencyEntity t " +
                            "where t.city = :city", TravelAgencyEntity.class)
                    .setParameter("city", city)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return travelAgency;
    }
}
