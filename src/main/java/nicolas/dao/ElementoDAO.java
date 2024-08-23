package nicolas.dao;

import nicolas.entities.Elemento;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class ElementoDAO {

    private final EntityManager em;

    public ElementoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Elemento elemento) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(elemento);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    public Elemento findById(Long id) {
        return em.find(Elemento.class, id);
    }

    public void delete(Elemento elemento) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.remove(em.contains(elemento) ? elemento : em.merge(elemento));
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    public List<Elemento> findByTitolo(String titolo) {
        return em.createQuery("SELECT e FROM Elemento e WHERE e.titolo = :titolo", Elemento.class)
                .setParameter("titolo", titolo)
                .getResultList();
    }


    public void deleteByISBN(String isbn) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Elemento elemento = em.createQuery("SELECT e FROM Elemento e WHERE e.isbn = :isbn", Elemento.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
            em.remove(em.contains(elemento) ? elemento : em.merge(elemento));
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to delete Elemento by ISBN", e);
        }
    }
    public List<Elemento> findByAutore(String autore) {
        return em.createQuery("SELECT e FROM Elemento e WHERE e.autore = :autore", Elemento.class)
                .setParameter("autore", autore)
                .getResultList();
    }


    public List<Elemento> findByAnnoPubblicazione(int anno) {
        return em.createQuery("SELECT e FROM Elemento e WHERE e.annoPubblicazione = :anno", Elemento.class)
                .setParameter("anno", anno)
                .getResultList();
    }

}
