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

    /**
     * Konstruktor
     */
    public Fabrik() {
        bestellungen = new ArrayList<>();
        bestellungsNr = 1;
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
     * Gibt eine neue Bestellung auf und fügt Standard- und Premiumtüren hinzu.
     * 
     * @param premiumTueren Anzahl der Premiumtüren
     * @param standardTueren Anzahl der Standardtüren
     */
    public void bestellungAufgeben(int premiumTueren, int standardTueren) {
        if (premiumTueren >= 0 && standardTueren >= 0 && standardTueren + premiumTueren > 0) {
            Bestellung neueBestellung = new Bestellung(standardTueren, premiumTueren, bestellungsNr);
            bestellungen.add(neueBestellung);
            System.out.println("Bestellung mit Bestellungsnummer " + bestellungsNr + " wurde erfolgreich aufgegeben.");
            bestellungsNr++;
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
}
