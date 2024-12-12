import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Die Test-Klasse ProduktTest.
 *
 * Testet die Funktionalität der Klasse Produkt.
 * Da Produkt abstrakt ist, wird Standardtuer als konkrete Implementierung verwendet.
 * 
 * @author Silvan
 * @version 3.2
 */
public class ProduktTest {
    private Produkt produkt;

    /**
     * Setzt das Testgerüst für den Test.
     *
     * Wird vor jeder Testfall-Methode aufgerufen.
     */
    @BeforeEach
    public void setUp() {
        // Standardtuer als konkrete Implementierung von Produkt
        produkt = new Standardtuer();
    }

    /**
     * Gibt das Testgerüst wieder frei.
     *
     * Wird nach jeder Testfall-Methode aufgerufen.
     */
    @AfterEach
    public void tearDown() {
        produkt = null; // Objekt freigeben
    }

    @Test
    public void testInitialisierungZustand() {
        // Act
        int result = produkt.aktuellerZustand();

        // Assert
        int expectedResult = 1; // Der initiale Zustand sollte 1 sein
        assertEquals(expectedResult, result, "Der initiale Zustand des Produkts sollte 1 sein.");
    }

    @Test
    public void testVeraendereZustandInnerhalbBereich() {
        // Act
        produkt.zustandAendern(3); // Valider Zustand
        int result = produkt.aktuellerZustand();

        // Assert
        int expectedResult = 3; // Der Zustand sollte auf 3 geändert sein
        assertEquals(expectedResult, result, "Der Zustand des Produkts sollte auf 3 geändert werden.");
    }

    @Test
    public void testVeraendereZustandAusserhalbBereich() {
        // Act
        produkt.zustandAendern(7); // Invalider Zustand
        int result = produkt.aktuellerZustand();

        // Assert
        int expectedResult = 5; // Fehlerzustand sollte gesetzt werden
        assertEquals(expectedResult, result, "Bei einem invaliden Zustand sollte der Fehlerzustand 5 gesetzt werden.");
    }

    @Test
    public void testFehlerhafterZustand() {
        // Act
        produkt.zustandAendern(7); // Invalider Zustand

        // Assert
        assertTrue(produkt.istFehlerhaft(), "Das Produkt sollte im Fehlerzustand sein.");
    }
}