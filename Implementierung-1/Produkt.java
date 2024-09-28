
/**
 * Abstrakte Klasse Produkt - wird von Standardtuer und Premiumtuer erweitert.
 * Jedes Produkt hat einen Zustand, der den Status des Produkts beschreibt.
 */

public class Produkt
{
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
    public int aktuelleZustand() {
        return this.zustand;
    }
}
