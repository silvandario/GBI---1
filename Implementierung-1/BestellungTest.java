import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Die Test-Klasse BestellungTest. Prüft, ob verschiedene Arten der Bestellungen korrekt aufgegeben werden können und Bestellinformationen korrekt ausgegeben werden.
 * Die Tests werden für die Standardtüre geschrieben, da die Logik für Premiumtüren identisch ist.
 * 
 * @author Silvan Ladner
 * @version 2
 */
public class BestellungTest {
    /**
     * Konstruktor für die Test-Klasse BestellungTest
     */
    public BestellungTest() {
    }

    /**
     * Setzt das Testgerüst für den Test.
     *
     * Wird vor jeder Testfall-Methode aufgerufen.
     */
    @BeforeEach
    public void setUp() {
    }

    /**
     * Gibt das Testgerüst wieder frei.
     *
     * Wird nach jeder Testfall-Methode aufgerufen.
     */
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testBestellePositiveAnzahl() {
        // Arrange
        Bestellung bestellung = new Bestellung(10, 20, 1);

        // Act
        int result = bestellung.gibAnzahlStandardTueren();

        // Assert
        int expectedResult = 10;
        assertEquals(expectedResult, result);
    }

    @Test
    public void testBestelleNichtNegativeAnzahl() {
        // Arrange
        Bestellung bestellung = new Bestellung(0, 10, 1);

        // Act
        int result = bestellung.gibAnzahlStandardTueren();

        // Assert
        int expectedResult = 0;
        assertEquals(expectedResult, result);
    }

    @Test
    public void testBestelleNegativeAnzahl() {
        // Arrange
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Bestellung(-10, 10, 1);
        });

        // Assert
        String expectedMessage = "Standardtüren darf keine negative Zahl sein.";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void testBestelleAnzahlTurenNachHinzufuegen() {
        // Arrange
        Bestellung bestellung = new Bestellung(10, 0, 1);
        bestellung.standardTuereHinzufuegen();
        bestellung.standardTuereHinzufuegen();

        // Act
        int result = bestellung.gibBestellteProdukte().size();

        // Assert
        int expectedResult = 12;
        assertEquals(expectedResult, result);
    }

    @Test
    public void testMaximaleAnzahlStandardTueren() {
        // Arrange
        Bestellung bestellung = new Bestellung(10_000, 0, 1);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            bestellung.standardTuereHinzufuegen();
        });
    }

    @Test
    public void testMaximaleAnzahlUeberschritten() {
        // Arrange & Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Bestellung(10_001, 0, 1);
        });

        // Assert
        String expectedMessage = "Standardtüren dürfen maximal 10000 Stück betragen.";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
