import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Diese Testklasse prüft die Fabrik. 
 * Da void-Methoden oft Konsolenausgaben produzieren, wird der Output für die Tests ausgewertet.
 * 
 * @author Silvan
 * @version 3.4
 */
public class FabrikTest {
    private Fabrik fabrik;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Premiumtuer premiumtuer;
    private Standardtuer standardtuer;

    @BeforeEach
    public void setUp() {
        fabrik = new Fabrik();
        premiumtuer = new Premiumtuer(); // Create instance for instance method calls
        standardtuer = new Standardtuer();
        System.setOut(new PrintStream(outputStream)); // Redirect console output
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut); // Restore console output
        outputStream.reset(); // Clear the stream
    }

    /**
     * Helper method: Trimmed console output.
     */
    private String getTrimmedOutput() {
        String output = outputStream.toString();
        outputStream.reset();
        return output.trim();
    }

    @Test
    void testErfolgreicheBestellung() {
        fabrik.bestellungAufgeben(2, 3); // Bestellung mit 2 Premium- und 3 Standardtüren
        String expectedOutput = "Bestellung mit Bestellungsnummer 1 wurde erfolgreich aufgegeben.";
        assertTrue(getTrimmedOutput().contains(expectedOutput));
    }

    @Test
    void testLeereBestellung() {
        fabrik.bestellungAufgeben(0, 0); // Bestellung mit 0 Türen
        String expectedOutput = "Mindestens eine Tür muss bestellt werden";
        assertTrue(getTrimmedOutput().contains(expectedOutput));
    }

    @Test
    void testNegativeBestellung() {
        fabrik.bestellungAufgeben(-1, 3); // Negative Anzahl
        String expectedOutput = "Ungültige Bestellung";
        assertTrue(getTrimmedOutput().contains(expectedOutput));
    }

    @Test
    void testLieferzeitBerechnung() {
        fabrik.bestellungAufgeben(2, 3); // 2 Premium-, 3 Standardtüren
        Bestellung bestellung = fabrik.getBestellung().get(0);
        float actualLieferzeit = bestellung.gibLieferZeit();

        // Use instance methods for Produktionszeit
        float expectedLieferzeit = 
            (2 * premiumtuer.getProduktionszeit() + 3 * standardtuer.getProduktionszeit()) / (60 * 24) + 1;

        assertEquals(expectedLieferzeit, actualLieferzeit, 0.01, "Lieferzeit stimmt nicht überein.");
    }

    @Test
    void testBestellungenAusgeben() {
        fabrik.bestellungAufgeben(1, 1); // Bestellung 1
        fabrik.bestellungAufgeben(2, 2); // Bestellung 2

        fabrik.bestellungenAusgeben();

        String output = getTrimmedOutput();
        assertTrue(output.contains("Bestellungsnummer 1"));
        assertTrue(output.contains("Bestellungsnummer 2"));
    }

    @Test
    void testLagerbestandNachBestellung() {
        fabrik.bestellungAufgeben(2, 3); // 2 Premium-, 3 Standardtüren
        fabrik.lagerbestandVonFarbrik();

        assertEquals(1000 - (2 * premiumtuer.getHolzeinheiten() + 3 * standardtuer.getHolzeinheiten()), fabrik.getVorhandeneHolzeinheiten());
        assertEquals(5000 - (2 * premiumtuer.getSchrauben() + 3 * standardtuer.getSchrauben()), fabrik.getVorhandeneSchrauben());
        assertEquals(500 - (2 * premiumtuer.getFarbeinheiten() + 3 * standardtuer.getFarbeinheiten()), fabrik.getVorhandeneFarbeinheiten());
        assertEquals(300 - (2 * premiumtuer.getKartoneinheiten() + 3 * standardtuer.getKartoneinheiten()), fabrik.getVorhandeneKartoneinheiten());
        assertEquals(200 - (2 * premiumtuer.getGlaseinheiten()), fabrik.getVorhandeneGlaseinheiten());
    }

    @Test
    void testVollesLagerAuffuellen() {
        fabrik.lagerAuffuellenUndAusgeben();
        String actualOutput = getTrimmedOutput();
        assertTrue(actualOutput.contains("Bestellung nicht getätigt! Das Lager ist voll"));
    }

    @Test
    void testNichtVollesLagerAuffuellen() {
        fabrik.aktualisiereLagerBestand(10, 10); // Reduce inventory
        fabrik.lagerAuffuellenUndAusgeben();
        String actualOutput = getTrimmedOutput();
        assertTrue(actualOutput.contains("Lager erfolgreich aufgefüllt"));
    }

    @Test
    void testProduktionsManagerBearbeitung() throws InterruptedException {
        fabrik.bestellungAufgeben(1, 1); // Bestellung 1
        fabrik.bestellungAufgeben(2, 0); // Bestellung 2

        Thread.sleep(3000); // Allow time for Produktionsmanager

        String actualOutput = getTrimmedOutput();
        assertTrue(actualOutput.contains("Neue Bestellung hinzugefügt: 1"));
        assertTrue(actualOutput.contains("Neue Bestellung hinzugefügt: 2"));
    }

    @Test
    void testInkrementelleBestellnummern() {
        fabrik.bestellungAufgeben(1, 1); // Bestellung 1
        fabrik.bestellungAufgeben(2, 2); // Bestellung 2
        fabrik.bestellungenAusgeben();

        String output = getTrimmedOutput();
        assertTrue(output.contains("Bestellungsnummer 1"));
        assertTrue(output.contains("Bestellungsnummer 2"));
    }

    @Test
    void testLagerInteraktionNormalerGebrauch() {
        fabrik.bestellungAufgeben(3, 2);
        fabrik.lagerAuffuellenUndAusgeben();

        String actualOutput = getTrimmedOutput();
        assertTrue(actualOutput.contains("Bestellung mit Bestellungsnummer 1 wurde erfolgreich aufgegeben."));
        assertTrue(actualOutput.contains("Lager erfolgreich aufgefüllt"));
    }

    @Test
    void testLagerInteraktionVollesLager() {
        fabrik.lagerAuffuellenUndAusgeben();
        String actualOutput = getTrimmedOutput();
        assertTrue(actualOutput.contains("Das Lager ist voll"));
    }
}