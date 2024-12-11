/**
 * Die Klasse Lieferant simuliert die Funktion eines externen Lieferanten,
 * der Materialien an die Fabrik liefert, um das Lager aufzufüllen.
 *
 * Diese Klasse enthält eine Methode, die eine Bestellung von Materialien bearbeitet.
 *
 * @author Silvan
 * @version 1.0
 */
public class Lieferant {
    
    // Maximalwerte für Lager = Bestellungen
    private static final int maxHolzeinheiten = 1000;
    private static final int maxSchrauben = 5000;
    private static final int maxFarbeinheiten = 500;
    private static final int maxKartoneinheiten = 300;
    private static final int maxGlaseinheiten = 200;
    
    /**
     * Konstruktor
     */
    public Lieferant() {
    }
    

    /**
     * Simuliert die Bearbeitung einer Bestellung von Materialien des Kunden.
     * 
     * @param holzEinheiten    Anzahl der bestellten Holzeinheiten
     * @param schrauben        Anzahl der bestellten Schrauben
     * @param farbEinheiten    Anzahl der bestellten Farbeinheiten
     * @param kartonEinheiten  Anzahl der bestellten Kartoneinheiten
     * @param glasEinheiten    Anzahl der bestellten Glaseinheiten
     * @return true, wenn die Bestellung erfolgreich war, andernfalls false
     */
    public boolean wareBestellen(int holzEinheiten, int schrauben, int farbEinheiten, int kartonEinheiten, int glasEinheiten) {
        // Berechnung der Gesamtbestellmenge
        int gesamtBestellmenge = holzEinheiten + schrauben + farbEinheiten + kartonEinheiten + glasEinheiten;

        // Bedingung: Bestellung muss gültig sein sprich ins Lager passend
        if (gesamtBestellmenge > 0 &&
            holzEinheiten <= maxHolzeinheiten &&
            schrauben <= maxSchrauben &&
            farbEinheiten <= maxFarbeinheiten &&
            kartonEinheiten <= maxKartoneinheiten &&
            glasEinheiten <= maxGlaseinheiten) {
            System.out.println("Bestellung erfolgreich verarbeitet.");
            return true;
        } else {
            System.out.println("Bestellung fehlgeschlagen: Ungültige Menge oder Überschreitung des Maximums.");
            return false;
        }
    }
  public void run(){
        try{
            Thread.sleep(2000);
            System.out.println("Lieferant: Ware wird versandt"); 
            lager.wareLiefern();
        }catch(InterruptedException ie){
            System.out.println("Lieferant: Thread Exception!");
        }
    }}
    