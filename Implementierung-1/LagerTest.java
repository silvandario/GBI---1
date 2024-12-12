import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Diese Testklasse prüft die Klasse Lager.
 * Testet Methoden wie lagerBestandAusgeben, lagerAuffuellen und setVorhandeneGlaseinheiten.
 * 
 * @author Silvan Ladner
 * @version 1.1
 */
public class LagerTest {

    private Lager lager;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        lager = new Lager();
        System.setOut(new PrintStream(outputStream)); // Konsolenausgabe umleiten
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut); // Konsolenausgabe wiederherstellen
        outputStream.reset(); // Konsolenausgabe-Stream leeren
    }

    /**
     * Hilfsmethode, um die Konsolenausgabe zu holen und zu trimmen.
     */
    private String getTrimmedOutput() {
        String output = outputStream.toString();
        outputStream.reset();
        return output.trim();
    }

    @Test
public void testLagerBestandAusgeben() {
    // Arrange
    Lager lager = new Lager();
    String expected = "-----------Lagerbestand-----------\n" +
                      "1000 Holzeinheiten vorhanden\n" +
                      "5000 Schrauben vorhanden\n" +
                      "500 Farbeinheiten vorhanden\n" +
                      "300 Kartoneinheiten vorhanden\n" +
                      "200 Glaseinheiten vorhanden\n" +
                      "----------- ----------- -----------";

    // Act
    String actual = lager.gibLagerBestandAlsString();

    // Assert
    assertEquals(expected, actual, "Die Lagerbestandsausgabe stimmt nicht überein.");
}

    @Test
public void testVollesLagerAuffuellen() {
    // Arrange
    lager.setVorhandeneHolzeinheiten(1000); // Set full stock
    lager.setVorhandeneSchrauben(5000);
    lager.setVorhandeneFarbeinheiten(500);
    lager.setVorhandeneKartoneinheiten(300);
    lager.setVorhandeneGlaseinheiten(200);

    // Act
    lager.lagerAuffuellen();

    // Assert
    String output = getTrimmedOutput();
    assertEquals("Bestellung nicht getätigt! Das Lager ist voll", output.trim(),
                 "Die Auffüllmeldung für ein volles Lager ist nicht korrekt.");
}

    @Test
    public void testSetVorhandeneGlaseinheitenMitNegativemWert() {
        // Arrange, Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lager.setVorhandeneGlaseinheiten(-12); // Negativer Betrag, sollte IllegalArgumentException werfen
        });

        // Erwartete Fehlermeldung
        String expectedMessage = "Ungültige Menge für Glaseinheiten.";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage), "Die Fehlermeldung stimmt nicht überein.");
    }

    @Test
    public void testSetVorhandeneHolzeinheitenMitNegativemWert() {
        // Arrange, Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lager.setVorhandeneHolzeinheiten(-100);
        });

        // Erwartete Fehlermeldung
        String expectedMessage = "Ungültige Menge für Holzeinheiten.";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage), "Die Fehlermeldung für Holzeinheiten ist nicht korrekt.");
    }

    @Test
    public void testSetVorhandeneSchraubenMitZuHohemWert() {
        // Arrange, Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lager.setVorhandeneSchrauben(6000); // Mehr als die maximal erlaubten Schrauben
        });

        // Erwartete Fehlermeldung
        String expectedMessage = "Ungültige Menge für Schrauben.";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage), "Die Fehlermeldung für Schrauben ist nicht korrekt.");
    }

    @Test
    public void testLagerBestandNachAuffuellen() {
        // Arrange
        lager.setVorhandeneHolzeinheiten(800);
        lager.setVorhandeneSchrauben(4000);
    
        // Act
        lager.lagerAuffuellen();
        String actualOutput = getTrimmedOutput();
    
        // Erwartete Ausgabe
        String expectedOutput = "--- Lager erfolgreich aufgefüllt ---";
    
        // Assert
        assertTrue(actualOutput.contains(expectedOutput), "Die Auffüllmeldung ist nicht korrekt.");
        assertEquals(1000, lager.getVorhandeneHolzeinheiten(), "Die Anzahl der Holzeinheiten nach Auffüllen ist nicht korrekt.");
        assertEquals(5000, lager.getVorhandeneSchrauben(), "Die Anzahl der Schrauben nach Auffüllen ist nicht korrekt.");
    }
}