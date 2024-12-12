import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Die Test-Klasse ProduktTest.
 *
 * Testet die Funktionalität der Klasse Produkt.
 * 
 * @author  Silvan
 * @version 3.1
 */
public class ProduktTest {
    /**
     * Konstruktor für die Test-Klasse ProduktTest.
     */
    public ProduktTest() {
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
    public void testInitialisierungZustand() {
        // Arrange
        Produkt produkt = new Produkt();

        // Act
        int result = produkt.aktuellerZustand();

        // Assert
        int expectedResult = 1; // Der initiale Zustand sollte 1 sein
        assertEquals(expectedResult, result);
    }

    @Test
    public void testVeraendereZustandInnerhalbBereich() {
        // Arrange
        Produkt produkt = new Produkt();

        // Act
        produkt.zustandAendern(3); // Valider Zustand
        int result = produkt.aktuellerZustand();

        // Assert
        int expectedResult = 3; // Der Zustand sollte auf 3 geändert sein
        assertEquals(expectedResult, result);
    }

    @Test
    public void testVeraendereZustandAusserhalbBereich() {
        // Arrange
        Produkt produkt = new Produkt();

        // Act
        produkt.zustandAendern(7); // Invalider Zustand
        int result = produkt.aktuellerZustand();

        // Assert
        int expectedResult = 5; // Fehlerzustand sollte gesetzt werden
        assertEquals(expectedResult, result);
    }

    @Test
    public void testFehlerhafterZustand() {
        // Arrange
        Produkt produkt = new Produkt();

        // Act
        produkt.zustandAendern(7); // Invalider Zustand

        // Assert
        assertTrue(produkt.istFehlerhaft(), "Das Produkt sollte im Fehlerzustand sein.");
    }
}