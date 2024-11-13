import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Diese Testklasse prüft die Fabrik. Da void-Methoden vorhanden sind, wobei oft Output in die Konsole geprintet wird, 
 * wird dieser Output als Referenz genommen.
 * 
 * @author Silvan Ladner
 * @version 3.3
 */
public class FabrikTest {
    private Fabrik fabrik;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        fabrik = new Fabrik();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        outputStream.reset(); // Ausgabe-Stream leeren
    }

    /**
     * Hilfsmethode, um die aktuelle Konsolenausgabe zu holen und zu trimmen.
     * 
     * @return getrimmte Konsolenausgabe
     */
    private String getTrimmedOutput() {
        String output = outputStream.toString();
        outputStream.reset(); // Stream für die nächste Ausgabe leeren
        return output.trim();
    }

    @Test
    void testErfolgreicheBestellung() {
        // Arrange & Act
        fabrik.bestellungAufgeben(2, 3);

        // Assert
        String expectedOutput = "Bestellung mit Bestellungsnummer 1 wurde erfolgreich aufgegeben.";
        assertEquals(expectedOutput, getTrimmedOutput());
    }

    @Test
    public void testLeereBestellung() {
        // Arrange & Act
        fabrik.bestellungAufgeben(0, 0); // Bestellung mit 0 Türen

        // Assert
        String expectedOutput = "Bestellung konnte nicht erfolgreich aufgegeben werden. Du musst mindestens eine Türe bestellen beziehungsweise kannst keine negative Anzahl bestellen.";
        assertEquals(expectedOutput, getTrimmedOutput());
    }

    @Test
    public void testNegativeBestellung() {
        // Arrange & Act
        fabrik.bestellungAufgeben(-1, 3); // Negative Anzahl

        // Assert
        String expectedOutput = "Bestellung konnte nicht erfolgreich aufgegeben werden. Du musst mindestens eine Türe bestellen beziehungsweise kannst keine negative Anzahl bestellen.";
        assertEquals(expectedOutput, getTrimmedOutput());
    }

    @Test
    public void testBestellungsNummernKorrekt() {
        // Arrange
        fabrik.bestellungAufgeben(1, 1); // Bestellung 1
        fabrik.bestellungAufgeben(2, 2); // Bestellung 2

        // Konsolenausgabe von `bestellungenAusgeben` prüfen
        fabrik.bestellungenAusgeben();

        // Assert
        String expectedOutput = """
            Bestellung mit Bestellungsnummer 1 wurde erfolgreich aufgegeben.
            Bestellung mit Bestellungsnummer 2 wurde erfolgreich aufgegeben.
            Bestellungsnummer: 1
            Anzahl Standardtüren: 1
            Anzahl Premiumtüren: 1
            Beschaffungszeit: 0
            Bestellbestätigung: false
            --------------------------------------------
            Bestellungsnummer: 2
            Anzahl Standardtüren: 2
            Anzahl Premiumtüren: 2
            Beschaffungszeit: 0
            Bestellbestätigung: false
            --------------------------------------------
            """;
        assertEquals(expectedOutput.trim(), getTrimmedOutput());
    }
}
