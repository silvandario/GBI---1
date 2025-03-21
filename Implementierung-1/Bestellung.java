import java.util.ArrayList;

/**
 * Klasse Bestellung - repräsentiert eine Bestellung von Standard- und Premiumtüren.
 * 
 * @author Silvan
 * @version 3.3
 */
public class Bestellung {
    
    private static final int MAX_TEILE_PRO_TYP = 10_000; // Maximale Anzahl von Teilen pro Typ

    private ArrayList<Produkt> bestellteProdukte; 
    private boolean bestellBestaetigung; 
    private int bestellungsNr;
    private int beschaffungsZeit; // Standardwert -1
    private int anzahlStandardTueren;
    private int anzahlPremiumTueren;
    private float lieferZeit; // Standardwert -1
    private boolean alleProdukteProduziert; 

    /**
     * Konstruktor für Bestellung.
     * 
     * @param anzahlStandardTueren Anzahl der bestellten Standardtüren
     * @param anzahlPremiumTueren Anzahl der bestellten Premiumtüren
     * @param bestellungsNr Bestellnummer der Bestellung
     * @throws IllegalArgumentException bei negativen Werten oder Überschreitung der Maximalgrenze
     */
    public Bestellung(int anzahlStandardTueren, int anzahlPremiumTueren, int bestellungsNr) {
        this.anzahlStandardTueren = validiereEingabe(anzahlStandardTueren, "Standardtüren");
        this.anzahlPremiumTueren = validiereEingabe(anzahlPremiumTueren, "Premiumtüren");
        this.bestellungsNr = bestellungsNr;
        this.beschaffungsZeit = -1;
        this.lieferZeit = -1;
        this.bestellBestaetigung = false;
        this.alleProdukteProduziert = false;
        this.bestellteProdukte = new ArrayList<>();
        
        // Initialisiert die Liste der bestellten Produkte
        for (int i = 0; i < anzahlStandardTueren; i++) {
            bestellteProdukte.add(new Standardtuer());
        }
        for (int i = 0; i < anzahlPremiumTueren; i++) {
            bestellteProdukte.add(new Premiumtuer());
        }
    }
    
    /**
     * Validiert die Eingabe für Anzahl von Produkten.
     * 
     * @param anzahl Anzahl der Produkte
     * @param typ Typ des Produkts (z. B. "Standardtüren" oder "Premiumtüren")
     * @return gültige Anzahl der Produkte
     * @throws IllegalArgumentException bei ungültigen Werten
     */
    private int validiereEingabe(int anzahl, String typ) {
        if (anzahl < 0) {
            throw new IllegalArgumentException(typ + " darf keine negative Zahl sein. Bitte geben Sie einen positiven Wert ein.");
        }
        if (anzahl > MAX_TEILE_PRO_TYP) {
            throw new IllegalArgumentException(typ + " dürfen maximal " + MAX_TEILE_PRO_TYP + " Stück betragen.");
        }
        return anzahl;
    }

    /**
     * Fügt eine Standardtür der Bestellung hinzu.
     * 
     * @throws IllegalStateException bei Überschreitung der Maximalgrenze
     */
    public void standardTuereHinzufuegen() {
        if (anzahlStandardTueren >= MAX_TEILE_PRO_TYP) {
            throw new IllegalStateException("Maximale Anzahl von Standardtüren erreicht.");
        }
        bestellteProdukte.add(new Standardtuer());
        anzahlStandardTueren++;
    }
    
    /**
     * Fügt eine Premiumtür der Bestellung hinzu.
     * 
     * @throws IllegalStateException bei Überschreitung der Maximalgrenze
     */
    public void premiumTuereHinzufuegen() {
        if (anzahlPremiumTueren >= MAX_TEILE_PRO_TYP) {
            throw new IllegalStateException("Maximale Anzahl von Premiumtüren erreicht.");
        }
        bestellteProdukte.add(new Premiumtuer());
        anzahlPremiumTueren++;
    }

    /**
     * Gibt die Liste der bestellten Produkte zurück.
     * 
     * @return Liste der bestellten Produkte
     */
    public ArrayList<Produkt> gibBestellteProdukte() {
        return bestellteProdukte;
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
     * @param zeit Beschaffungszeit (in Tagen)
     */
    public void setzBeschaffungsZeit(int zeit) {
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
     * Setzt die Lieferzeit der Bestellung.
     * 
     * @param lieferZeit Lieferzeit (in Tagen)
     */
    public void setzeLieferZeit(float lieferZeit) {
        this.lieferZeit = lieferZeit;
    }

    /**
     * Gibt die Lieferzeit der Bestellung zurück.
     * 
     * @return Lieferzeit in Tagen
     */
    public float gibLieferZeit() {
        return lieferZeit;
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
     * Markiert die Bestellung als vollständig produziert.
     */
    public void setzeAlleProdukteProduziert() {
        System.out.println("Bestellung " + bestellungsNr + ": Alle Produkte sind produziert und bereit zum Versand.");
        alleProdukteProduziert = true;
    }
}