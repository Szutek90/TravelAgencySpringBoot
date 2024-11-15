package com.app.repository.impl;

import com.app.entity.person.PersonEntity;
import com.app.repository.PersonRepository;
import com.app.repository.generic.AbstractCrudRepository;
import com.app.repository.generic.EntityManagerWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepositoryImpl extends AbstractCrudRepository<PersonEntity, Integer> implements PersonRepository {

    public PersonRepositoryImpl(EntityManagerFactory emf) {
        super(emf);
    }

    @Override
    public List<PersonEntity> findBySurname(String surname) {
        EntityManager em = null;
        EntityTransaction tx = null;
        List<PersonEntity> personEntities = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            personEntities = em.createQuery("select p from PersonEntity p where p.surname = :surname", PersonEntity.class)
                    .setParameter("surname", surname)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return personEntities;
    }

    @Override
    public Optional<PersonEntity> findByNameAndSurname(String name, String surname) {
        EntityManager em = null;
        EntityTransaction tx = null;
        Optional<PersonEntity> person = Optional.empty();
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            person = Optional.ofNullable(
                    em.createQuery("select p from PersonEntity p where p.name = :name AND p.surname = :surname",
                                    PersonEntity.class)
                            .setParameter("name", name)
                            .setParameter("surname", surname)
                            .getSingleResult());
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return person;
    }

    @Override
    public Optional<PersonEntity> findByEmail(String email) {
        EntityManager em = null;
        EntityTransaction tx = null;
        Optional<PersonEntity> person = Optional.empty();
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            person = Optional.ofNullable(em.createQuery("select p from PersonEntity p where p.email = :email",
                            PersonEntity.class)
                    .setParameter("email", email)
                    .getSingleResult());
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return person;
    }
}
