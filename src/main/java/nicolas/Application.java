package nicolas;

import nicolas.dao.ElementoDAO;
import nicolas.dao.PrestitoDAO;
import nicolas.dao.UtenteDAO;
import nicolas.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();

        ElementoDAO elementoDAO = new ElementoDAO(em);
        UtenteDAO utenteDAO = new UtenteDAO(em);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);

        try {
            Utente utente = new Utente();
            utente.setNome("Mario");
            utente.setCognome("Rossi");
            utente.setDataDiNascita(LocalDate.of(1990, 1, 1));
            utente.setNumeroTessera("123456");

            utenteDAO.save(utente);

            Libro libro = new Libro();
            libro.setIsbn("978-3-16-148410-0");
            libro.setTitolo("Il grande Gigi");
            libro.setAnnoPubblicazione(1925);
            libro.setNumeroPagine(218);
            libro.setAutore("Gigi");
            libro.setGenere("Romanzo");
            elementoDAO.save(libro);

            Prestito prestito = new Prestito();
            prestito.setUtente(utente);
            prestito.setElemento(libro);
            prestito.setDataInizioPrestito(LocalDate.now());
            prestito.setDataRestituzionePrevista(LocalDate.now().plusDays(30));

            prestitoDAO.save(prestito);

            List<Elemento> libriConTitolo = elementoDAO.findByTitolo("Il grande Gigi");
            libriConTitolo.forEach(System.out::println);

            List<Elemento> libriPerAutore = elementoDAO.findByAutore("Gigi");
            libriPerAutore.forEach(System.out::println);

            List<Elemento> libriPerAnno = elementoDAO.findByAnnoPubblicazione(1925);
            libriPerAnno.forEach(System.out::println);

            List<Prestito> prestitiPerUtente = prestitoDAO.findByUtente(utente);
            prestitiPerUtente.forEach(System.out::println);

            List<Prestito> prestitiScaduti = prestitoDAO.findPrestitiScaduti();
            prestitiScaduti.forEach(System.out::println);


        } finally {
            em.close();
            emf.close();
        }
    }
}
