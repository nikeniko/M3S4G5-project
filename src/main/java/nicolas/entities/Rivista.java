package nicolas.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("RIVISTA")
public class Rivista extends Elemento {

    public enum Periodicita {
        SETTIMANALE, MENSILE, SEMESTRALE
    }

    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;

    public Rivista() {}

    public Rivista(String isbn, String titolo, int annoPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(isbn, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }

    @Override
    public String toString() {
        return "Rivista{" +
                "periodicita=" + periodicita +
                ", titolo='" + getTitolo() + '\'' +
                ", annoPubblicazione=" + getAnnoPubblicazione() +
                ", isbn='" + getIsbn() + '\'' +
                ", numeroPagine=" + getNumeroPagine() +
                '}';
    }
}