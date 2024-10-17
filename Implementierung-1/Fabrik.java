import java.util.ArrayList;

/**
 * Die Klasse Fabrik verwaltet Bestellungen von Türen.
 * Eine Fabrik kann Bestellungen entgegennehmen und diese ausgeben.
 * Enthält Huaptmethode
 * 
 * @author Silvan Ladner
 * @version 1.2
 */
public class Fabrik {
    
    // bestgellte Produkte gehen in diese Array List
    private ArrayList<Bestellung> bestellungen = new ArrayList<>();
    // Bestellungsnummern werden bei uns als Ganzzahlen von 1 aufwärts definiert
    private int bestellungsNr;
    private static final int BESTELLUNGSBEGINN = 1;

    
    /**
     * Konstruktor
     */
    public Fabrik() {
        bestellungen = new ArrayList();
        bestellungsNr = BESTELLUNGSBEGINN;
    }

    /**
     * Main Method.
     */
    public static void main(String[] args) {
        System.out.println("Start"); // 
        Fabrik fabrik = new Fabrik();
    }
    
    /**
     * Gibt eine neue Bestellung auf und fügt Standard- und Premiumtüren hinzu.
     * 
     * @param standardTueren Anzahl der Standardtüren
     * @param premiumTueren Anzahl der Premiumtüren
     */
    public void bestellungAufgeben(int premiumTueren, int standardTueren) {
        
        
        if (premiumTueren >=0 && standardTueren >= 0 && standardTueren+premiumTueren>0) { 
            // Kunden mpüssen mindenstens eine türe total kaufen (Kunden aus dem Luxussegment wollen evtl nur Premiumtüren und keine Standardtüren, weshalb die Anzahl 0 sein kann, 
            //jedoch nicht negativ sein kann für die Türvarianten
            bestellungsNr= bestellungsNr++; // wir starten bei 1 ja
            Bestellung neueBestellung = new Bestellung(standardTueren, premiumTueren, bestellungsNr);
            bestellungen.add(neueBestellung);
            System.out.println("Bestellung mit Bestellungsnummer " + bestellungsNr + " wurde erfolgreich aufgegeben.");
        } else {
            System.out.println("Bestellung konnte nicht erfolgreich aufgegeben werden. Du musst mindenstens eine Türe bestellen."); // Meldung bei Falscheingabe
        }
        

    }


    /**
     * Gibt alle Bestellungen aus, einschließlich der Details zu den Standard- und Premiumtüren.
     */
    public void bestellungenAusgeben() {
        for (int i = 0; i < bestellungen.size(); i++) {
            Bestellung bestellung = bestellungen.get(i);
            System.out.println("Bestellungsnummer: " + bestellung.gibBestellungsNr());
            System.out.println("Anzahl Standardtüren: " + bestellung.gibAnzahlStandardTueren());
            System.out.println("Anzahl Premiumtüren: " + bestellung.gibAnzahlPremiumTueren());
            System.out.println("Beschaffungszeit: " + bestellung.gibBeschaffungsZeit());
            System.out.println("Bestellbestätigung: " + bestellung.gibBestellBestaetigung());
            System.out.println("--------------------------------------------");
        }
    }
}