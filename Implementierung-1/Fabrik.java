import java.util.ArrayList;
import java.util.Scanner;

/**
 * Die Klasse Fabrik verwaltet Bestellungen von Türen.
 * Eine Fabrik kann Bestellungen entgegennehmen und diese ausgeben.
 * Enthält Hauptmethode.
 * 
 * @author Silvan Ladner
 * @version 1.3
 */

public class Fabrik {

    private ArrayList<Bestellung> bestellungen = new ArrayList<>(); // Liste aller Bestellungen
    private int bestellungsNr; // Bestellnummern starten bei 1
    private static Lager lager; // Statische Variable existiert nur einaml
    private Produktions_Manager produktionsManager;
    

    /**
     * Konstruktor
     */
    public Fabrik() {
        bestellungen = new ArrayList<>();
        bestellungsNr = 1;
        lager = new Lager(); // neu auch Lager
        produktionsManager = new Produktions_Manager(this, lager);
        produktionsManager.start();
        
    }

    /**
     * Main-Methode.
     */
    public static void main(String[] args) {
        System.out.println("Willkommen bei der Türfabrik!");
        Fabrik fabrik = new Fabrik();
        Scanner scanner = new Scanner(System.in);

        // Benutzer nach Eingabe fragen
        while (true) {
            System.out.println("Möchtest du eine Bestellung aufgeben? (ja/nein)");
            String antwort = scanner.nextLine().trim().toLowerCase();

            if (antwort.equals("ja")) {
                try {
                    System.out.println("Wie viele Standardtüren möchtest du bestellen?");
                    int standardTueren = Integer.parseInt(scanner.nextLine());

                    System.out.println("Wie viele Premiumtüren möchtest du bestellen?");
                    int premiumTueren = Integer.parseInt(scanner.nextLine());

                    fabrik.bestellungAufgeben(premiumTueren, standardTueren);

                } catch (NumberFormatException e) {
                    System.out.println("Ungültige Eingabe! Bitte gib eine gültige Zahl ein.");
                }
            } else if (antwort.equals("nein")) {
                System.out.println("Hier sind die aktuellen Bestellungen:");
                fabrik.bestellungenAusgeben();
                System.out.println("Programm wird beendet.");
                break;
            } else {
                System.out.println("Bitte gib 'ja' oder 'nein' ein.");
            }
        }

        scanner.close();
    }
    /**
     * Methode zum auffüllen des Lagesr und Ausgabe des akteuellen Bestenades anschliessend* 
     */
    
    public void lagerAuffuellenUndAusgeben(){
        lager.lagerAuffuellen();
        lager.lagerBestandAusgeben();
    }
    /**
     * Methode Ausgabe des akteuellen Bestenades vom Lager* 
     */
    
    public void lagerbestandVonFarbrik(){
        lager.lagerBestandAusgeben();
    }
    /**
     * Gibt eine neue Bestellung auf und fügt Standard- und Premiumtüren hinzu.
     * 
     * @param premiumTueren Anzahl der Premiumtüren
     * @param standardTueren Anzahl der Standardtüren
     */
    public void bestellungAufgeben(int premiumTueren, int standardTueren) {
        int beschaffungsZeit = -1;
        float lieferzeit = -1;
        if (premiumTueren >= 0 && standardTueren >= 0 && standardTueren + premiumTueren > 0) {
            Bestellung neueBestellung = new Bestellung(standardTueren, premiumTueren, bestellungsNr);
            beschaffungsZeit=lager.gibBeschaffungsZeit(neueBestellung); // entweder 0 oder 2; sodass folgender if loop ausgeführt wird
            if(beschaffungsZeit>=0){
                neueBestellung.setzBeschaffungsZeit(beschaffungsZeit);
                if (beschaffungsZeit > 0) {
                    System.out.println("Materialien unzureichend. Lager wird aufgefüllt...");
                    lager.lagerAuffuellen();
                    System.out.println("Lager erfolgreich aufgefüllt.");
                } 
                lieferzeit = (Standardtuer.getProduktionszeit()*standardTueren+Premiumtuer.getProduktionszeit()*premiumTueren)/(60*24) + beschaffungsZeit +1; // zuerst Lieferzeit berechnen
                neueBestellung.setzeLieferZeit(lieferzeit); // nun wird die LIeferzeit offiziell gesetzt
                neueBestellung.bestellungBestaetigen();
                // Lagerbestand reduzieren: ANPASSUNGEN NOCH MÖGLICH GEMÄSS AUFGABE 3
                bestellungen.add(neueBestellung);  
                produktionsManager.fuegeZuVerarbeitendeBestellungenHinzu(neueBestellung);
                System.out.println("Bestellung mit Bestellungsnummer " + bestellungsNr + " wurde erfolgreich aufgegeben.");
                bestellungsNr++;
            } else {
                System.out.println("Bestellung konnte nicht erfolgreich aufgegeben werden. Unser Supply Chain management hat versagt.");
            } 
        } else {
            System.out.println("Bestellung konnte nicht erfolgreich aufgegeben werden. Du musst mindestens eine Türe bestellen beziehungsweise kannst keine negative Anzahl bestellen.");
        }
    }

    /**
     * Gibt alle Bestellungen aus, einschließlich der Details zu den Standard- und Premiumtüren.
     */
    public void bestellungenAusgeben() {
        if (bestellungen.isEmpty()) {
            System.out.println("Es wurden noch keine Bestellungen aufgegeben.");
        } else {
            for (Bestellung bestellung : bestellungen) {
                System.out.println("Bestellungsnummer: " + bestellung.gibBestellungsNr());
                System.out.println("Anzahl Standardtüren: " + bestellung.gibAnzahlStandardTueren());
                System.out.println("Anzahl Premiumtüren: " + bestellung.gibAnzahlPremiumTueren());
                System.out.println("Beschaffungszeit: " + bestellung.gibBeschaffungsZeit());
                System.out.println("Bestellbestätigung: " + bestellung.gibBestellBestaetigung());
                System.out.println("--------------------------------------------");
            }
        }
    }
    /**
     * GEtter Methode für Bestellungen
     */
    public ArrayList<Bestellung> getBestellungen() {
    return bestellungen;
    }
     /**
     * Getter Methoden der Fabrik (für die testklasse, wir wollen direkt aus der "gebauten" Farbik zugreiffen)* 
     */
    
    public int getVorhandeneFarbeinheiten(){
        return lager.getVorhandeneFarbeinheiten();
    }
     public int getVorhandeneHolzeinheiten(){
        return lager.getVorhandeneHolzeinheiten();
    }
     public int getVorhandeneSchrauben(){
        return lager.getVorhandeneSchrauben();
    }
     public int getVorhandeneKartoneinheiten(){
        return lager.getVorhandeneKartoneinheiten();
    }
     public int getVorhandeneGlaseinheiten(){
        return lager.getVorhandeneGlaseinheiten();
    }
}
