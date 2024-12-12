import java.util.LinkedList;

/**
 * Die Klasse Produkt wird von Standardtuer und Premiumtuer erweitert. Produkt ist also Superklasse von Standard- und Premiumtür.
 * Jedes Produkt hat einen Zustand, der den Status des Produkts beschreibt.
 * 
 * @author Silvan Ladner
 * @version 3.1
 */
public abstract class Produkt {
    /** Zustände des Produkts:
     * 1: bestellt
     * 2: in Produktion
     * 3: fertiggestellt & im Lager
     * 4: verkauft & abtransportiert
     * 5: Fehlerzustand
     */
    private int zustand;
    private LinkedList<Roboter> produktionsAblauf;
    int PRODUKTIONSZEIT;

    /**
     * Konstruktor: Initialisiert Zustand und Produktionsablauf.
     */
    public Produkt() {
        this.zustand = 1; // Neu bestellt
        this.produktionsAblauf = new LinkedList<>();
        }

    /**
     * Ändert den Zustand des Produkts.
     * 
     * @param neuerZustand Der neue Zustand des Produkts.
     */
    public void zustandAendern(int neuerZustand) {
        if (neuerZustand >= 1 && neuerZustand <= 4) {
            this.zustand = neuerZustand;
        } else {
            this.zustand = 5; // Fehlerzustand
        }
    }

    /**
     * Legt die Reihenfolge der Roboter fest, die das Produkt bearbeiten.
     * 
     * @param produktionsAblauf Die Liste der Roboter in Bearbeitungsreihenfolge.
     */
    public void setzteProduktionsAblauf(LinkedList<Roboter> produktionsAblauf) {
    if (produktionsAblauf == null || produktionsAblauf.isEmpty()) {
        throw new IllegalArgumentException("Produktionsablauf darf nicht leer sein.");
    }
    this.produktionsAblauf = produktionsAblauf;
    }
    /**
     * Leitet das Produkt zur nächsten Produktionsstation weiter.
     */
    public void naechsteProduktionsStation() {
        if (!produktionsAblauf.isEmpty()) { // Überprüfung, ob weitere Stationen vorhanden sind
            Roboter roboter = produktionsAblauf.poll();
            System.out.println("Nächste Station: " + roboter.gibName());
            zustandAendern(2); // In Produktion
            roboter.fuegeProduktHinzu(this);
        } else {
            System.out.println("Produkt bereit zur Auslieferung: " + this);
            zustandAendern(3); // Fertiggestellt & im Lager
        }
    }

    /**
     * Gibt den aktuellen Zustand des Produkts zurück.
     * 
     * @return Aktueller Zustand des Produkts.
     */
    public int aktuellerZustand() {
        return this.zustand;
    }

    /**
     * Überprüft, ob das Produkt im Fehlerzustand ist.
     * 
     * @return True, wenn das Produkt im Fehlerzustand ist, sonst false.
     */
    public boolean istFehlerhaft() {
        return this.zustand == 5;
    }
    
   /**
     * Abstrakte Methode zur Rückgabe der Produktionszeit.
     * Diese Methode muss in den Subklassen implementiert werden.
     * 
     * @return Produktionszeit des Produkts.
     */
    public int getProduktionszeit() {
        throw new UnsupportedOperationException("getProduktionszeit() muss in der Unterklasse überschrieben werden.");
    }
}