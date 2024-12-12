import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

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
    @Test
    public void testBestellungBestaetigen() {
        Bestellung bestellung = new Bestellung(10, 20, 1);
        bestellung.bestellungBestaetigen();
        //asset
        assertTrue(bestellung.gibBestellBestaetigung());
    }

    @Test
    public void testBeschaffungsZeitSetzenUndAbfragen() {
        Bestellung bestellung = new Bestellung(10, 20, 1);
        bestellung.setzBeschaffungsZeit(5);
        // asset
        assertEquals(5, bestellung.gibBeschaffungsZeit());
    }

    @Test
    public void testLieferZeitSetzenUndAbfragen() {
        Bestellung bestellung = new Bestellung(10, 20, 1);
        bestellung.setzeLieferZeit(2.5f);
        // Assert
        assertEquals(2.5f, bestellung.gibLieferZeit());
    }

    @Test
    public void testLiefereBestellteProdukte() {
        Bestellung bestellung = new Bestellung(5, 3, 1);
        ArrayList<Produkt> produkte = bestellung.gibBestellteProdukte();
        // Assert
        assertEquals(8, produkte.size());
    }
    @Test
    public void testGibBestellteProdukte() {
        Bestellung bestellung = new Bestellung(3, 2, 1);
        ArrayList<Produkt> produkte = bestellung.gibBestellteProdukte();
        assertEquals(5, produkte.size());
        assertTrue(produkte.get(0) instanceof Standardtuer);
        assertTrue(produkte.get(3) instanceof Premiumtuer);
    }
}
