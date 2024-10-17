
/**
 * Abstrakte Klasse Produkt - wird von Standardtuer und Premiumtuer erweitert.
 * Jedes Produkt hat einen Zustand, der den Status des Produkts beschreibt.
 * 
 * @author Silvan Ladner
 * @version 2.0
 */
public abstract class Produkt {
    // Zustand des Produkts
    private int zustand;

    /**
     * Setzt den Zustand des Produkts.
     * 
     * @param neuerZustand der neue Zustand des Produkts
     */
    public void zustandAendern(int neuerZustand) {
        this.zustand = neuerZustand;
    }

    /**
     * Gibt den aktuellen Zustand des Produkts zurück.
     * 
     * @return aktueller Zustand des Produkts
     */
    public int getAktuellerZustand() {
        return this.zustand;
    }
}

