import java.util.Scanner;
import java.util.ArrayList;

/**
 * Die Klasse Fabrik verwaltet Bestellungen von Türen.
 * Eine Fabrik kann Bestellungen entgegennehmen und diese ausgeben.
 * 
 * @author Silvan Ladner
 * @version 1
 */
public class Fabrik {
    private ArrayList<Bestellung> bestellungen = new ArrayList<>();
    private int bestellungsNr = 1;

    /**
     * Hauptprogramm.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Fabrik fabrik = new Fabrik();

        // Anzahl der Standardtüren und Premiumtüren erfragen
        int standardTueren = fabrik.getPositiveEingabe(scanner, "Geben Sie die Anzahl der Standardtüren ein (größer als 0): ");
        int premiumTueren = fabrik.getPositiveEingabe(scanner, "Geben Sie die Anzahl der Premiumtüren ein (größer als 0): ");

        // Bestellung aufgeben
        fabrik.bestellungAufgeben(standardTueren, premiumTueren);

        // Alle Bestellungen ausgeben
        fabrik.bestellungenAusgeben();

        // Scanner schließen
        scanner.close();
    }

    /**
     * Gibt eine neue Bestellung auf und fügt Standard- und Premiumtüren hinzu.
     * 
     * @param standardTueren Anzahl der Standardtüren
     * @param premiumTueren Anzahl der Premiumtüren
     */
    public void bestellungAufgeben(int standardTueren, int premiumTueren) {
        Bestellung neueBestellung = new Bestellung(standardTueren, premiumTueren, bestellungsNr);
        bestellungen.add(neueBestellung);
        System.out.println("Bestellung mit Bestellungsnummer " + bestellungsNr + " wurde erfolgreich aufgegeben.");
        bestellungsNr++;
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
            System.out.println("Bestellte Produkte: ");
            for (Produkt produkt : bestellung.gibBestellteProdukte()) {
                System.out.println(" - Produktzustand: " + produkt.getAktuellerZustand());
            }
            System.out.println("-------------------------");
        }
    }

    /**
     * Fordert den Benutzer auf, eine positive Zahl größer als 0 einzugeben.
     * 
     * @param scanner Der Scanner zum Erfassen der Benutzereingabe
     * @param nachricht Die Nachricht, die dem Benutzer angezeigt wird
     * @return Eine positive ganze Zahl größer als 0
     */
    private int getPositiveEingabe(Scanner scanner, String nachricht) {
        int eingabe;
        do {
            System.out.print(nachricht);
            while (!scanner.hasNextInt()) {
                System.out.println("Ungültige Eingabe. Bitte geben Sie eine Zahl größer als 0 ein.");
                scanner.next(); // Ungültigen Input verwerfen
                System.out.print(nachricht);
            }
            eingabe = scanner.nextInt();
            if (eingabe <= 0) {
                System.out.println("Die Anzahl muss größer als 0 sein. Bitte versuchen Sie es erneut.");
            }
        } while (eingabe <= 0);
        return eingabe;
    }
}
