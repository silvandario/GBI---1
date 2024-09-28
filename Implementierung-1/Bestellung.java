
/**
 * Beschreiben Sie hier die Klasse Bestellung.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
import java.util.ArrayList;
public class Bestellung
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private ArrayList<Produkt> bestellteProdukte = new ArrayList<>();
    private boolean bestellBestaetigung;
    private int beschaffungsZeit;
    private int anzahlStandardTueren;
    private int anzahlPremiumTueren;
    //private int bestellNr; // evtl. in Zukunft mit Fabrik
    
    /**
     * Konstruktor für Bestellung.
     * 
     * @param anzahlStandardTueren Anzahl der bestellten Standardtüren
     * @param anzahlPremiumTueren Anzahl der bestellten Premiumtüren
     */
    public Bestellung(int anzahlStandardTueren, int anzahlPremiumTueren) {
        this.anzahlStandardTueren = anzahlStandardTueren;
        this.anzahlPremiumTueren = anzahlPremiumTueren;
    }


    /**
     * Fügt ein Produkt der Bestellung hinzu.
     * 
     * @param produkt Das hinzuzufügende Produkt
     */
    public void addProdukt(Produkt produkt) {
        bestellteProdukte.add(produkt);
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

    // Evtl. in Zukunft
    //public int gibBestellungsNr() {
    //    return bestellNr;
    //}

}
