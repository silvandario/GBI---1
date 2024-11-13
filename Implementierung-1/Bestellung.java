import java.util.ArrayList;

/**
 * Klasse Bestellung - repräsentiert eine Bestellung von Standard- und Premiumtüren.
 * 
 * @author Silvan Ladner
 * @version 2.2
 */
public class Bestellung {
    
    private static final int MAX_TEILE_PRO_TYP = 10_000; // Maximale Anzahl von Teilen pro Typ

    private ArrayList<Produkt> bestellteProdukte = new ArrayList<>(); 
    private boolean bestellBestaetigung = false; 
    private int bestellungsNr;
    private int beschaffungsZeit; // Standardwert 0 ist hier ausreichend; wird nicht im Konstruktor berechnet.
    private int anzahlStandardTueren;
    private int anzahlPremiumTueren;

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

        // Initialisiert die Liste der bestellten Produkte basierend auf den Eingabewerten
        for (int i = 0; i < this.anzahlStandardTueren; i++) {
            bestellteProdukte.add(new Standardtuer());
        }
        for (int i = 0; i < this.anzahlPremiumTueren; i++) {
            bestellteProdukte.add(new Premiumtuer());
        }

        // Hinweis: beschaffungsZeit bleibt 0, bis sie explizit gesetzt wird.
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
     * Fügt eine Standardtür der Bestellung hinzu, wenn die Maximalgrenze nicht überschritten wird.
     * 
     * @throws IllegalStateException bei Überschreitung der Maximalgrenze
     */
    public void standardTuereHinzufuegen() {
        if (anzahlStandardTueren >= MAX_TEILE_PRO_TYP) {
            throw new IllegalStateException("Maximale Anzahl von Standardtüren erreicht. Keine weiteren können hinzugefügt werden.");
        }
        bestellteProdukte.add(new Standardtuer());
        anzahlStandardTueren++;
    }
    
    /**
     * Fügt eine Premiumtür der Bestellung hinzu, wenn die Maximalgrenze nicht überschritten wird.
     * 
     * @throws IllegalStateException bei Überschreitung der Maximalgrenze
     */
    public void premiumTuereHinzufuegen() {
        if (anzahlPremiumTueren >= MAX_TEILE_PRO_TYP) {
            throw new IllegalStateException("Maximale Anzahl von Premiumtüren erreicht. Keine weiteren können hinzugefügt werden.");
        }
        bestellteProdukte.add(new Premiumtuer());
        anzahlPremiumTueren++;
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
