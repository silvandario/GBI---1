
/**
 * Die Klasse Produkt wird von Standardtuer und Premiumtuer erweitert. Produkt ist also Superklasse von Standard- und Premiumtür.
 * Jedes Produkt hat einen Zustand, der den Status des Produkts beschreibt.
 * 
 * @author Silvan Ladner
 * @version 2.1
 */
public class Produkt {
    /**Die variabel zustand korrespondiert mit dem Zustand (als ganzzahlige numerische Kodierung) des betsellten Produktes
     * Zustände (zur Zeit noch arbiträr, da auch von späterer Implementation abhängig):
     * 1: betsellt
     * 2: in Produktion
     * 3: fertig gestellt & im Lager
     * 4: verkauft & abtransportiert
     * 5: Achtung! Hier ist was Falsch
     */
    private int zustand;
    
    /**
     * no-args-Konstruktor
     */
    public Produkt() {
        zustand = 1; // 1, da neu
    }

    /**
     * Ändert den Zustand des Produkts.
     * 
     * @param neuerZustand der neue Zustand des Produkts
     */
    public void zustandAendern(int neuerZustand) {
        if (neuerZustand >= 0 && neuerZustand < 5) {
        this.zustand = neuerZustand;
    } else {
        this.zustand = 5;
    }
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

