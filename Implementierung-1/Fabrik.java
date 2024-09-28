/**
 * Die Klasse Fabrik verwaltet Bestellungen von Türen.
 * Eine Fabrik kann Bestellungen entgegennehmen und diese ausgeben.
 */
import java.util.Scanner;
import java.util.ArrayList; //Import wird benötigt
public class Fabrik
{
    
    private ArrayList<Bestellung> bestellungen = new ArrayList<>();
    private int bestellungsNr;
    
    /**
     * Hauptprogramm.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //Benutzereingabe anstzatt Werte hardcoden
        Fabrik fabrik = new Fabrik();

        // Benutzer nach der Anzahl der Standardtüren fragen
        int standardTueren = -1; //mit negativen Wert, um die Schlaufe zu triggeren
        while (standardTueren < 0) {
            System.out.println("Geben Sie die Anzahl der Standardtüren ein (0 oder mehr): ");
            standardTueren = scanner.nextInt();
            if (standardTueren < 0) {
                System.out.println("Die Anzahl der Standardtüren muss 0 oder mehr sein. Bitte versuchen Sie es erneut.");
            }
        }

        // Benutzer nach der Anzahl der Premiumtüren fragen
        int premiumTueren = -1;//mit negativen Wert, um die Schlaufe zu triggeren
        while (premiumTueren < 0) {
            System.out.println("Geben Sie die Anzahl der Premiumtüren ein (0 oder mehr): ");
            premiumTueren = scanner.nextInt();
            if (premiumTueren < 0) {
                System.out.println("Die Anzahl der Premiumtüren muss 0 oder mehr sein. Bitte versuchen Sie es erneut.");
            }
        }
        
        //if kein Input Fall, müssen die Türen ggf. noch positiv werden
        if (standardTueren < 0 || premiumTueren < 0) {
            standardTueren = Math.abs(standardTueren);
            premiumTueren = Math.abs(premiumTueren);
        }

        // Bestellung aufgeben
        fabrik.bestellungAufgeben(standardTueren, premiumTueren);

        // Alle Bestellungen ausgeben
        fabrik.bestellungAusgeben();

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
        Bestellung neueBestellung = new Bestellung(standardTueren, premiumTueren);
    }

    public void bestellungAusgeben() {
        for (Bestellung bestellung : bestellungen) { //jedes Element durchlaufen und Ausgeben
            //System.out.println("BestellungsNr: " + bestellungsNr); // in Zukunft evtl. 
            System.out.println("Anzahl Standardtueren: " + bestellung.gibAnzahlStandardTueren());
            System.out.println("Anzahl Premiumtueren: " + bestellung.gibAnzahlPremiumTueren());
        }
    }
}
