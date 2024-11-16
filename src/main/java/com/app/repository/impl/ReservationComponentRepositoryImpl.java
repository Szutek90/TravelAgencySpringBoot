package com.app.repository.impl;

import com.app.entity.ReservationComponent;
import com.app.entity.reservation.ReservationEntity;
import com.app.repository.ReservationComponentRepository;
import com.app.repository.generic.AbstractCrudRepository;
import com.app.repository.generic.EntityManagerWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationComponentRepositoryImpl extends AbstractCrudRepository<ReservationComponent, Integer> implements ReservationComponentRepository {

    public ReservationComponentRepositoryImpl(EntityManagerFactory emf) {
        super(emf);
    }

    @Override
    public List<ReservationComponent> findByReservationId(int id) {
        EntityManager em;
        EntityTransaction tx = null;
        List<ReservationComponent> components = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            components = em.createQuery("select r from ReservationEntity r where r.id = :id",
                            ReservationEntity.class)
                    .setParameter("id", id)
                    .getSingleResult()
                    .getComponents();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return components;
    }

    public ReservationComponent save(int id, ReservationComponent item) {
        EntityManager em;
        EntityTransaction tx = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            // TODO 8 persist czy merge aby zaktualizować encję?
            var reservation = em.find(ReservationEntity.class, id);
            em.persist(reservation.getComponents().add(item));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return item;
    }

    @Override
    public List<ReservationComponent> findAll() {
        EntityManager em;
        EntityTransaction tx = null;
        List<ReservationComponent> components = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            components = em.createQuery("select r from ReservationEntity r", ReservationEntity.class)
                    .getResultList().stream()
                    .flatMap(r->r.getComponents().stream())
                    .toList();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return components;
    }
}
