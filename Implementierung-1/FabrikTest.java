/**
 * Testklasse für die Fabrik-Klasse.
 * Diese Klasse überprüft die Funktionalität der Fabrik, Bestellungen aufzugeben und anzuzeigen.
 */
public class FabrikTest {

    /**
     * Hauptmethode, die alle Tests für die Fabrik durchführt.
     */
    public static void main(String[] args) {
        // Test 1: Bestellung mit 3 Standardtüren und 2 Premiumtüren
        testBestellungAufgeben(3, 2);

        // Test 2: Bestellung mit 0 Standardtüren und 5 Premiumtüren
        testBestellungAufgeben(0, 5);

        // Test 3: Bestellung mit 10 Standardtüren und 0 Premiumtüren
        testBestellungAufgeben(10, 0);

        // Test 4: Bestellung mit -1 (kleiner 0) Standardtür und 1 Premiumtür
        testBestellungAufgeben(-1, 1);
    }

    /**
     * Führt einen Test durch, bei dem eine Bestellung mit der angegebenen Anzahl
     * an Standardtüren und Premiumtüren aufgegeben wird.
     * 
     * @param standardTueren Anzahl der Standardtüren in der Bestellung
     * @param premiumTueren Anzahl der Premiumtüren in der Bestellung
     */
    public static void testBestellungAufgeben(int standardTueren, int premiumTueren) {
        System.out.println("\nTest: Bestellung mit " + standardTueren + " Standardtüren und " + premiumTueren + " Premiumtüren.");
        
        // Erstellen einer neuen Fabrikinstanz
        Fabrik fabrik = new Fabrik();

        // Bestellung aufgeben
        fabrik.bestellungAufgeben(standardTueren, premiumTueren);

        // Bestellungen ausgeben
        fabrik.bestellungAusgeben();
    }
}
