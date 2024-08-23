package nicolas.dao;

import nicolas.entities.Prestito;
import nicolas.entities.Utente;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class PrestitoDAO {

    private final EntityManager em;

    public PrestitoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Prestito prestito) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(prestito);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to save Prestito", e);
        }
    }

    public Prestito findById(Long id) {
        return em.find(Prestito.class, id);
    }

    public void delete(Prestito prestito) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.remove(em.contains(prestito) ? prestito : em.merge(prestito));
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to delete Prestito", e);
        }
    }

    public List<Prestito> findByUtente(Utente utente) {
        return em.createQuery("SELECT p FROM Prestito p WHERE p.utente = :utente", Prestito.class)
                .setParameter("utente", utente)
                .getResultList();
    }
}
