package com.app.repository.impl;

import com.app.converter.tours.FileToToursConverter;
import com.app.entity.tour.TourEntity;
import com.app.repository.TourRepository;
import com.app.repository.generic.AbstractCrudRepository;
import com.app.repository.generic.EntityManagerWrapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public class TourRepositoryImpl extends AbstractCrudRepository<TourEntity, Integer> implements TourRepository {
    private final ApplicationContext context;

    @Value("${tours.file}")
    String filename;

    @Value("${tours.format}")
    String format;

    public TourRepositoryImpl(EntityManagerFactory emf, ApplicationContext context) {
        super(emf);
        this.context = context;
    }

    @PostConstruct
    public void init() {
        var converter = context.getBean("%sFileToToursConverterImpl".formatted(format),
                FileToToursConverter.class);
        if (findAll().isEmpty()) {
            saveAll(converter.convert(filename));
        }
    }

    @Override
    public List<TourEntity> getByCountryName(String countryName) {
        EntityManager em;
        EntityTransaction tx = null;
        List<TourEntity> tours = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            tours = em.createQuery("select t from TourEntity t where t.countryId = (select c.id from CountryEntity c" +
                            " where c.name = :countryName)", TourEntity.class)
                    .setParameter("countryName", countryName)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }

        return tours;
    }

    @Override
    public List<TourEntity> getInPriceRange(BigDecimal from, BigDecimal to) {
        EntityManager em;
        EntityTransaction tx = null;
        List<TourEntity> tours = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            tours = em.createQuery("select t from TourEntity t where t.pricePerPerson >= :from " +
                            "and t.pricePerPerson<= :to", TourEntity.class)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return tours;
    }

    @Override
    public List<TourEntity> getLessThanGivenPrice(BigDecimal to) {
        EntityManager em;
        EntityTransaction tx = null;
        List<TourEntity> tours = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            tours = em.createQuery("select t from TourEntity t where t.pricePerPerson <= :to", TourEntity.class)
                    .setParameter("to", to)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return tours;
    }

    @Override
    public List<TourEntity> getMoreExpensiveThanGivenPrice(BigDecimal from) {
        EntityManager em;
        EntityTransaction tx = null;
        List<TourEntity> tours = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            tours = em.createQuery("select t from TourEntity t where t.pricePerPerson >= :from", TourEntity.class)
                    .setParameter("from", from)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return tours;
    }

    @Override
    public List<TourEntity> getInDateRange(LocalDate from, LocalDate to) {
        EntityManager em;
        EntityTransaction tx = null;
        List<TourEntity> tours = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            tours = em.createQuery("select t from TourEntity t where t.startDate >= :from " +
                            "and t.endDate <= :to", TourEntity.class)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return tours;
    }

    @Override
    public List<TourEntity> getBeforeGivenDate(LocalDate to) {
        EntityManager em;
        EntityTransaction tx = null;
        List<TourEntity> tours = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            tours = em.createQuery("select t from TourEntity t where t.endDate <= :to", TourEntity.class)
                    .setParameter("to", to)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return tours;
    }

    @Override
    public List<TourEntity> getAfterGivenDate(LocalDate from) {
        EntityManager em;
        EntityTransaction tx = null;
        List<TourEntity> tours = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            tours = em.createQuery("select t from TourEntity t where t.startDate >= :from", TourEntity.class)
                    .setParameter("from", from)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return tours;
    }

    @Override
    public List<TourEntity> getByAgency(int agencyId) {
        EntityManager em;
        EntityTransaction tx = null;
        List<TourEntity> tours = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            tours = em.createQuery("select t from TourEntity t where t.agencyId = :agencyId", TourEntity.class)
                    .setParameter("agencyId", agencyId)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return tours;
    }
}
