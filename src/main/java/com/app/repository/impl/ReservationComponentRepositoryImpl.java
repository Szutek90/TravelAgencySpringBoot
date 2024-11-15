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
        EntityManager em = null;
        EntityTransaction tx = null;
        List<ReservationComponent> components = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            var reservation = em.createQuery("select r from ReservationEntity r where r.id = :id", ReservationEntity.class)
                    .getSingleResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }

        var sql = "SELECT * FROM %s WHERE reservation_id = :reservation_id".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("reservation_id", id)
                .map((rs, ctx) -> ReservationComponent.valueOf(rs.getString("component")))
                .list());
    }

    public ReservationComponent save(int id, ReservationComponent item) {
        var sql = "insert into %s ( reservation_id, component) values ( :reservation_id, :component )"
                .formatted(tableName());
        jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("reservation_id", id)
                .bind("component", item)
                .execute());
        return item;
    }

    @Override
    public List<ReservationComponent> findAll() {
        var sql = " select * from reservation_components order by reservation_id desc ";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .map((rs, ctx) -> ReservationComponent.valueOf(rs.getString("component")))
                .list());
    }
}
