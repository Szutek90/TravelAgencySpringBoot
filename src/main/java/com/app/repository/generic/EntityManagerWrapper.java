package com.app.repository.generic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class EntityManagerWrapper implements AutoCloseable {
    private final EntityManager em;

    public EntityManagerWrapper(EntityManagerFactory emf) {
        em = emf.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return em;
    }

    //TODO 1 .IntelliJ proponuje mi za każdym razem zamiast em != null em.isOpen(). Czy to jest zamienne?

    @Override
    public void close() throws Exception {
        if (em != null) {
            em.close();
        }
    }
}
