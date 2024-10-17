import java.util.ArrayList;

/**
 * Klasse Bestellung - repräsentiert eine Bestellung von Standard- und Premiumtüren.
 * 
 * @author Silvan Ladner
 * @version 2.0
 */
public class Bestellung {
    // Instanzvariablen
    private ArrayList<Produkt> bestellteProdukte = new ArrayList<>();
    private boolean bestellBestaetigung = false;
    private int bestellungsNr;
    private int beschaffungsZeit;
    private int anzahlStandardTueren;
    private int anzahlPremiumTueren;

    /**
     * Konstruktor für Bestellung.
     * 
     * @param anzahlStandardTueren Anzahl der bestellten Standardtüren
     * @param anzahlPremiumTueren Anzahl der bestellten Premiumtüren
     * @param bestellungsNr Bestellnummer der Bestellung
     */
    public Bestellung(int anzahlStandardTueren, int anzahlPremiumTueren, int bestellungsNr) {
        this.anzahlStandardTueren = anzahlStandardTueren;
        this.anzahlPremiumTueren = anzahlPremiumTueren;
        this.bestellungsNr = bestellungsNr;

        // Initialisierung der bestellten Produkte 
        for (int i = 0; i < anzahlStandardTueren; i++) {
            bestellteProdukte.add(new Standardtuer());
        }
        for (int i = 0; i < anzahlPremiumTueren; i++) {
            bestellteProdukte.add(new Premiumtuer());
        }
    }

    /**
     * Bestätigt die Bestellung.
     */
    public void bestellungBestaetigen() {
        this.bestellBestaetigung = true;
    }

    /**
     * Gibt an, ob die Bestellung bestätigt wurde.
     * 
     * @return true, wenn die Bestellung bestätigt wurde, ansonsten false
     */
    public boolean gibBestellBestaetigung() {
        return bestellBestaetigung;
    }

    /**
     * Setzt die Beschaffungszeit der Bestellung.
     * 
     * @param zeit Beschaffungszeit in Tagen
     */
    public void setzeBeschaffungsZeit(int zeit) {
        this.beschaffungsZeit = zeit;
    }

    /**
     * Gibt die Beschaffungszeit der Bestellung zurück.
     * 
     * @return Beschaffungszeit in Tagen
     */
    public int gibBeschaffungsZeit() {
        return beschaffungsZeit;
    }

    /**
     * Gibt die Bestellnummer zurück.
     * 
     * @return Bestellnummer
     */
    public int gibBestellungsNr() {
        return bestellungsNr;
    }

    /**
     * Gibt die Anzahl der Standardtüren zurück.
     * 
     * @return Anzahl der Standardtüren
     */
    public int gibAnzahlStandardTueren() {
        return anzahlStandardTueren;
    }

    /**
     * Gibt die Anzahl der Premiumtüren zurück.
     * 
     * @return Anzahl der Premiumtüren
     */
    public int gibAnzahlPremiumTueren() {
        return anzahlPremiumTueren;
    }

    /**
     * Gibt die Liste der bestellten Produkte zurück.
     * 
     * @return Liste der bestellten Produkte
     */
    public ArrayList<Produkt> gibBestellteProdukte() {
        return bestellteProdukte;
    }
}
