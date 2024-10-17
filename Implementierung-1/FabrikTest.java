import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.ArrayList;

/**
 * Testklasse für die Fabrik-Klasse.
 * Diese Klasse überprüft die Funktionalität der Fabrik, Bestellungen aufzugeben und anzuzeigen.
 * 
 * @author Silvan Ladner
 * @version 1
 */
public class FabrikTest {
    
    @Test
    public void testBestellungAufgebenMitNegativenWerten() {
        // arrange - Erstellen einer Fabrik-Instanz und erwartete Werte festlegen
        Fabrik fabrik = new Fabrik();
        int erwarteteStandardTueren = 0; // Bei negativer Eingabe sollte es auf 0 gesetzt werden
        int erwartetePremiumTueren = 0;  // Bei negativer Eingabe sollte es auf 0 gesetzt werden

        // act - Methode bestellungAufgeben() mit negativen Werten aufrufen
        fabrik.bestellungAufgeben(-10, -5);

        // assert - Überprüfen, ob die Bestellung korrekt gesetzt wurde
        ArrayList<Bestellung> bestellungen = fabrik.getBestellungen();
        assertEquals(1, bestellungen.size()); // Es sollte genau eine Bestellung existieren
        Bestellung bestellung = bestellungen.get(0);
        assertEquals(erwarteteStandardTueren, bestellung.gibAnzahlStandardTueren());
        assertEquals(erwartetePremiumTueren, bestellung.gibAnzahlPremiumTueren());
    }

    @Test
    public void testBestellungAufgebenMitNullWerten() {
        // arrange - Erstellen einer Fabrik-Instanz und erwartete Werte festlegen
        Fabrik fabrik = new Fabrik();
        int erwarteteStandardTueren = 0;
        int erwartetePremiumTueren = 0;

        // act - Methode bestellungAufgeben() mit null Werten aufrufen
        fabrik.bestellungAufgeben(0, 0);

        // assert - Überprüfen, ob die Bestellung korrekt gesetzt wurde
        ArrayList<Bestellung> bestellungen = fabrik.getBestellungen();
        assertEquals(1, bestellungen.size()); // Es sollte genau eine Bestellung existieren
        Bestellung bestellung = bestellungen.get(0);
        assertEquals(erwarteteStandardTueren, bestellung.gibAnzahlStandardTueren());
        assertEquals(erwartetePremiumTueren, bestellung.gibAnzahlPremiumTueren());
    }

    @Test
    public void testBestellungAufgebenMitPositivenWerten() {
        // arrange - Erstellen einer Fabrik-Instanz und erwartete Werte festlegen
        Fabrik fabrik = new Fabrik();
        int erwarteteStandardTueren = 5;
        int erwartetePremiumTueren = 3;

        // act - Methode bestellungAufgeben() mit positiven Werten aufrufen
        fabrik.bestellungAufgeben(erwarteteStandardTueren, erwartetePremiumTueren);

        // assert - Überprüfen, ob die Bestellung korrekt gesetzt wurde
        ArrayList<Bestellung> bestellungen = fabrik.getBestellungen();
        assertEquals(1, bestellungen.size()); // Es sollte genau eine Bestellung existieren
        Bestellung bestellung = bestellungen.get(0);
        assertEquals(erwarteteStandardTueren, bestellung.gibAnzahlStandardTueren());
        assertEquals(erwartetePremiumTueren, bestellung.gibAnzahlPremiumTueren());
    }

    @Test
    public void testMehrereBestellungenAufgeben() {
        // arrange - Erstellen einer Fabrik-Instanz
        Fabrik fabrik = new Fabrik();

        // act - Mehrere Bestellungen aufgeben
        fabrik.bestellungAufgeben(2, 3);
        fabrik.bestellungAufgeben(4, 1);

        // assert - Überprüfen, ob die Bestellungen korrekt gesetzt wurden
        ArrayList<Bestellung> bestellungen = fabrik.getBestellungen();
        assertEquals(2, bestellungen.size()); // Es sollten genau zwei Bestellungen existieren

        Bestellung ersteBestellung = bestellungen.get(0);
        assertEquals(2, ersteBestellung.gibAnzahlStandardTueren());
        assertEquals(3, ersteBestellung.gibAnzahlPremiumTueren());

        Bestellung zweiteBestellung = bestellungen.get(1);
        assertEquals(4, zweiteBestellung.gibAnzahlStandardTueren());
        assertEquals(1, zweiteBestellung.gibAnzahlPremiumTueren());
    }

    @Test
    public void testBestellungAufgebenUndProduktZustand() {
        // arrange - Erstellen einer Fabrik-Instanz
        Fabrik fabrik = new Fabrik();

        // act - Bestellung mit positiven Werten aufgeben
        fabrik.bestellungAufgeben(1, 1);

        // assert - Überprüfen, ob die Produkte den korrekten Zustand haben
        ArrayList<Bestellung> bestellungen = fabrik.getBestellungen();
        Bestellung bestellung = bestellungen.get(0);
        assertEquals(2, bestellung.gibBestellteProdukte().size()); // Es sollten genau zwei Produkte existieren

        for (Produkt produkt : bestellung.gibBestellteProdukte()) {
            assertEquals(0, produkt.getAktuellerZustand()); // Der anfängliche Zustand sollte 0 sein
        }
    }

    @Test
    public void testKeineBestellungAufgegeben() {
        // arrange - Erstellen einer Fabrik-Instanz
        Fabrik fabrik = new Fabrik();

        // act & assert - Überprüfen, dass keine Bestellung existiert
        ArrayList<Bestellung> bestellungen = fabrik.getBestellungen();
        assertTrue(bestellungen.isEmpty()); // Es sollte keine Bestellung existieren
    }

    @Test
    public void testBestellBestaetigung() {
        // arrange - Erstellen einer Bestellung und erwartete Werte festlegen
        Bestellung bestellung = new Bestellung(3, 2);

        // act - Bestellbestätigung setzen
        bestellung.bestellungBestaetigen();

        // assert - Überprüfen, ob die Bestellung bestätigt wurde
        assertTrue(bestellung.gibBestellBestaetigung());
    }

    @Test
    public void testBeschaffungsZeitSetzenUndAbfragen() {
        // arrange - Erstellen einer Bestellung und erwartete Werte festlegen
        Bestellung bestellung = new Bestellung(3, 2);
        int erwarteteBeschaffungsZeit = 5;

        // act - Beschaffungszeit setzen
        bestellung.setzeBeschaffungsZeit(erwarteteBeschaffungsZeit);

        // assert - Überprüfen, ob die Beschaffungszeit korrekt gesetzt wurde
        assertEquals(erwarteteBeschaffungsZeit, bestellung.gibBeschaffungsZeit());
    }
}