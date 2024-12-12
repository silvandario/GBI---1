import java.util.ArrayList;
import java.util.Scanner;

/**
 * Die Klasse Fabrik verwaltet Bestellungen von Türen und das Lager.
 * Sie bietet Funktionen zur Aufgabe und Verwaltung von Bestellungen.
 * Enthält eine Hauptmethode für interaktive Benutzung.
 * 
 * @author Silvan Ladner
 * @version 1.4
 */
public class Fabrik {

    private ArrayList<Bestellung> bestellungen; // Liste aller Bestellungen
    private int bestellungsNr;
    private Lager lager;
    private Produktions_Manager produktionsManager;

    /**
     * Konstruktor: Initialisiert die Fabrik und startet den Produktionsmanager.
     */
    public Fabrik() {
        bestellungen = new ArrayList<>();
        bestellungsNr = 1;
        lager = new Lager();
        produktionsManager = new Produktions_Manager(this, lager);
        produktionsManager.start();
    }

    /**
     * Main-Methode für die interaktive Steuerung der Fabrik.
     */
    public static void main(String[] args) {
        System.out.println("Willkommen bei der Türfabrik!");
        Fabrik fabrik = new Fabrik();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Möchtest du eine Bestellung aufgeben? (ja/nein)");
                String antwort = scanner.nextLine().trim().toLowerCase();

                if ("ja".equals(antwort)) {
                    try {
                        System.out.println("Wie viele Standardtüren möchtest du bestellen?");
                        int standardTueren = Integer.parseInt(scanner.nextLine());

                        System.out.println("Wie viele Premiumtüren möchtest du bestellen?");
                        int premiumTueren = Integer.parseInt(scanner.nextLine());

                        fabrik.bestellungAufgeben(premiumTueren, standardTueren);

                    } catch (NumberFormatException e) {
                        System.out.println("Ungültige Eingabe! Bitte gib eine gültige Zahl ein.");
                    }
                } else if ("nein".equals(antwort)) {
                    fabrik.bestellungenAusgeben();
                    System.out.println("Programm wird beendet.");
                    break;
                } else {
                    System.out.println("Bitte gib 'ja' oder 'nein' ein.");
                }
            }
        }
    }

    /**
     * Gibt eine neue Bestellung auf und fügt Standard- und Premiumtüren hinzu.
     */
    public void bestellungAufgeben(int premiumTueren, int standardTueren) {
        if (premiumTueren < 0 || standardTueren < 0 || (premiumTueren + standardTueren == 0)) {
            System.out.println("Ungültige Bestellung: Mindestens eine Tür muss bestellt werden.");
            return;
        }

        Bestellung neueBestellung = new Bestellung(standardTueren, premiumTueren, bestellungsNr);
        int beschaffungsZeit = lager.gibBeschaffungsZeit(neueBestellung);
        System.out.println("Bestellung mit Bestellungsnummer "+ bestellungsNr + " wurde erfolgreich aufgegeben.");
        if (beschaffungsZeit >= 0) {
            if (beschaffungsZeit > 0) {
                System.out.println("Materialien unzureichend. Lager wird aufgefüllt...");
                lager.lagerAuffuellen();
            }
            float lieferzeit = berechneLieferzeit(standardTueren, premiumTueren, beschaffungsZeit);
            neueBestellung.setzeLieferZeit(lieferzeit);
            neueBestellung.bestellungBestaetigen();
            bestellungen.add(neueBestellung);
            produktionsManager.fuegeZuVerarbeitendeBestellungenHinzu(neueBestellung);
            System.out.println("Bestellung aufgegeben: Bestellnummer " + bestellungsNr);
            aktualisiereLagerBestand(standardTueren, premiumTueren);
            bestellungsNr++;
        } else {
            System.out.println("Bestellung konnte nicht aufgegeben werden. Fehler bei der Lagerprüfung.");
        }
    }

    /**
     * Berechnet die Lieferzeit für eine Bestellung.
     */
    private float berechneLieferzeit(int standardTueren, int premiumTueren, int beschaffungsZeit) {
        return (Standardtuer.getProduktionszeit() * standardTueren +
                Premiumtuer.getProduktionszeit() * premiumTueren) / (60 * 24) + beschaffungsZeit + 1;
    }

    /**
     * Gibt alle Bestellungen mit Details aus.
     */
    public void bestellungenAusgeben() {
        if (bestellungen.isEmpty()) {
            System.out.println("Es wurden noch keine Bestellungen aufgegeben.");
        } else {
            for (Bestellung bestellung : bestellungen) {
                System.out.println("Bestellnummer: " + bestellung.gibBestellungsNr());
                System.out.println("Standardtüren: " + bestellung.gibAnzahlStandardTueren());
                System.out.println("Premiumtüren: " + bestellung.gibAnzahlPremiumTueren());
                System.out.println("Beschaffungszeit: " + bestellung.gibBeschaffungsZeit());
                System.out.println("Bestätigt: " + bestellung.gibBestellBestaetigung());
                System.out.println("--------------------------------------------");
            }
        }
    }
    /**
     * Das Lager wird befohlen Material wieder nachzubestellen und auszugeben
     * 
     */
    
    public void lagerAuffuellenUndAusgeben(){
        lager.lagerAuffuellen();
        lager.lagerBestandAusgeben();
    }
    
    public ArrayList<Bestellung> getBestellung() {
        return this.bestellungen;
    }
    public void lagerbestandVonFarbrik() {
    lager.lagerBestandAusgeben();
    }
    
        public int getVorhandeneHolzeinheiten() {
        return lager.getVorhandeneHolzeinheiten();
    }
    
    public int getVorhandeneSchrauben() {
        return lager.getVorhandeneSchrauben();
    }
    
    public int getVorhandeneFarbeinheiten() {
        return lager.getVorhandeneFarbeinheiten();
    }
    
    public int getVorhandeneKartoneinheiten() {
        return lager.getVorhandeneKartoneinheiten();
    }
    
    public int getVorhandeneGlaseinheiten() {
        return lager.getVorhandeneGlaseinheiten();
    }
    
    public void aktualisiereLagerBestand(int standardTueren, int premiumTueren) {
        lager.setVorhandeneHolzeinheiten(
            lager.getVorhandeneHolzeinheiten() -
            (standardTueren * Standardtuer.getHolzeinheiten() + premiumTueren * Premiumtuer.getHolzeinheiten())
        );
        lager.setVorhandeneSchrauben(
            lager.getVorhandeneSchrauben() -
            (standardTueren * Standardtuer.getSchrauben() + premiumTueren * Premiumtuer.getSchrauben())
        );
        lager.setVorhandeneFarbeinheiten(
            lager.getVorhandeneFarbeinheiten() -
            (standardTueren * Standardtuer.getFarbeinheiten() + premiumTueren * Premiumtuer.getFarbeinheiten())
        );
        lager.setVorhandeneKartoneinheiten(
            lager.getVorhandeneKartoneinheiten() -
            (standardTueren * Standardtuer.getKartoneinheiten() + premiumTueren * Premiumtuer.getKartoneinheiten())
        );
        lager.setVorhandeneGlaseinheiten(
            lager.getVorhandeneGlaseinheiten() -
            (premiumTueren * Premiumtuer.getGlaseinheiten()) // Nur Premiumtüren haben Glaseinheiten
        );
    }
}