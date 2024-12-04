import java.util.LinkedList;

/**
 * Die Klasse Produkt wird von Standardtuer und Premiumtuer erweitert. Produkt ist also Superklasse von Standard- und Premiumtür.
 * Jedes Produkt hat einen Zustand, der den Status des Produkts beschreibt.
 * 
 * @author Silvan Ladner
 * @version 3.1
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
    private LinkedList<Roboter> produktionsAblauf;
    private LinkedList<Produkt> warteschlange = new LinkedList<Produkt>();
    
    /**
     * no-args-Konstruktor
     */
    public Produkt() {
        zustand = 1; // 1, da neu
        produktionsAblauf = new LinkedList<Roboter>();
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
     * Hier wird für das Produkt festgelegt, in welcher Reihenfolge es von
     * den Robotern bearbeitet wird
     * 
     * @param produktionsAblauf LinkedList<Roboter> die List mit der Reihenfolge der zu druchlaufenden Roboter
     */
    
    public void setzteProduktionsAblauf(LinkedList<Roboter> produktionsAblauf){
        this.produktionsAblauf = produktionsAblauf;
    }
    
     /**
     * Nach Bearbeitung durch Roboter kommt Produkt zur nächsten Station bzw Roboter, spirch:
     * Ändert den aktuellen Zustand des Produkts. Der Zustand ist bei Initialiserung 1 und wird entsprechend Beschreib oben auf 2 bzw. 3 geändert
     */   
    
    
    public void naechsteProduktionsStation(){
        if(produktionsAblauf.peek() != null){  //Check, dass nicht "Nichts" ist
            Roboter roboter = produktionsAblauf.poll(); // nächstes Eleemtn wird mitgenommen (wie in Übung)
            System.out.println("Next Station:" + roboter.gibName()); // gibt Station, die folgt, aus
            zustandAendern(2);
            roboter.fuegeProduktHinzu(this);
        }
        else{
            System.out.println("Folgendes Produkt ist bereit zur Auslieferung: "+ this);
            zustandAendern(3);
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

