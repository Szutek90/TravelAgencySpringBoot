package com.app.repository.generic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractCrudRepository<T, ID> implements CrudRepository<T, ID> {
    @SuppressWarnings("unchecked")
    protected final Class<T> entityType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    protected final EntityManagerFactory emf;

    @Override
    public T save(T item) {
        EntityManager em = null;
        EntityTransaction tx = null;
        T addedItem = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            addedItem = em.merge(item);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return addedItem;
    }

    @Override
    public List<T> saveAll(List<T> items) {
        EntityManager em = null;
        EntityTransaction tx = null;
        var addedItems = new ArrayList<T>();
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            for (var item : items) {
                addedItems.add(em.merge(item));
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return addedItems;
    }

    @Override
    public T update(T item, ID id) {
        T item = null;
        EntityManager em = null;
        EntityTransaction tx = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            var itemToUpdate = em.find(entityType, id);
            //TODO 6. Jak zrobić aktualizację?
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        var sql = "update %s set %s where id = :id".formatted(tableName(), columnNamesAndValues(item));
        var updatedRows = jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("id", id)
                .execute());

        if (updatedRows == 0) {
            throw new IllegalStateException("Row not updated");
        }
        return findById(id).orElseThrow();
    }

    @Override
    public Optional<T> findById(ID id) {
        EntityManager em = null;
        EntityTransaction tx = null;
        Optional<T> item = Optional.empty();
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            item = Optional.ofNullable(em.find(entityType, id));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return item;
    }

    @Override
    public List<T> findLast(int n) {
        //TODO 2. czy nie moge od razu tutaj tworzyć em i tx lub wewnątrz try/catch jesli byloby bez Wrappera?
        //TODO 3. Czy jesli mam juz utworzony projekt to czy istnieje jakis graficzny interfejs do dodawania
        // dependencies tak jak to wyglada podczas tworzenia nowego projektu?
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
        EntityManager em = null;
        EntityTransaction tx = null;
        List<T> items = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            items = em.createQuery("select t from %s t order by t.id desc"
                            .formatted(entityType.getSimpleName()), entityType)
                    .setMaxResults(n)
                    .getResultList();

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return items;
    }

    @Override
    public List<T> findAll() {
        EntityManager em = null;
        EntityTransaction tx = null;
        List<T> items = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            items = em.createQuery("select t from %s t"
                            .formatted(entityType.getSimpleName()), entityType)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return items;
    }

    @Override
    public List<T> findAllById(List<ID> ids) {
        EntityManager em = null;
        EntityTransaction tx = null;
        List<T> items = null;

        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            items = em.createQuery("select t from %s t where t.id in :ids".formatted(entityType.getSimpleName()),
                            entityType)
                    .setParameter("ids", ids)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        if (items.size() != ids.size()) {
            throw new IllegalStateException("Found " + items.size() + " items but expected " + ids.size());
        }

        return items;
    }

    @Override
    public List<T> deleteAllyByIds(List<ID> ids) {
        EntityManager em = null;
        EntityTransaction tx = null;
        //TODO 5 Czy to jest dobry pomysl aby w tym miejscu wyszukiwac elementy do usuniecia? Bedzie wtedy utworzona
        // nowa tranzakcja
        var itemsToDelete = findAllById(ids);
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            //TODO 4 Która opcja usuwania jest najlepsza?
            //1.
            em.createQuery("delete from %s where id in :ids"
                            .formatted(entityType.getSimpleName()), entityType)
                    .setParameter("ids", ids);
            tx.commit();

            //2.
            em.createQuery("delete from %s where id in :ids"
                            .formatted(entityType.getSimpleName()), entityType)
                    .setParameter("ids", ids).executeUpdate();
            tx.commit();

            //3.
            var items = em.createQuery("select t from %s t where t.id in :ids".formatted(entityType.getSimpleName()),
                            entityType)
                    .setParameter("ids", ids)
                    .getResultList();
            for (var item : items) {
                em.remove(item);
            }
            //4.
            var items2 = findAllById(ids);
            for (var item : items2) {
                em.remove(item);
            }
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }

        return itemsToDelete;
    }

    @Override
    public List<T> deleteAll() {
        EntityManager em = null;
        EntityTransaction tx = null;
        List<T> deletedItems = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            deletedItems = em.createQuery("select t from %s t"
                            .formatted(entityType.getSimpleName()), entityType)
                    .getResultList();
            for (var item : deletedItems) {
                em.remove(item);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return deletedItems;
    }

    @Override
    public T deleteById(ID id) {
        var itemToDelete = findById(id).orElseThrow(() ->
                new IllegalArgumentException("Item with that id not found"));
        EntityManager em = null;
        EntityTransaction et = null;
        try (var emw = new EntityManagerWrapper(emf)) {
            em = emw.getEntityManager();
            et = em.getTransaction();
            et.begin();
            em.remove(em.find(entityType, id));
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
        }
        return itemToDelete;
    }

}
