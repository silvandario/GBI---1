import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Testklasse für die Klasse Lieferant.
 * Überprüft die Funktionalität der Methode `wareBestellen`.
 * 
 * @author Silvan
 * @version 1.1
 */
public class LieferantTest {

    private Lieferant lieferant = new Lieferant();

    @Test
    public void testWareBestellenErfolgreich() {
        // Arrange
        int holzEinheiten = 500;
        int schrauben = 2000;
        int farbEinheiten = 100;
        int kartonEinheiten = 50;
        int glasEinheiten = 20;

        // Act
        boolean result = lieferant.wareBestellen(holzEinheiten, schrauben, farbEinheiten, kartonEinheiten, glasEinheiten);

        // Assert
        assertTrue(result, "Die Bestellung sollte erfolgreich sein.");
    }

    @Test
    public void testWareBestellenGesamtmengeNull() {
        // Arrange
        int holzEinheiten = 0;
        int schrauben = 0;
        int farbEinheiten = 0;
        int kartonEinheiten = 0;
        int glasEinheiten = 0;

        // Act
        boolean result = lieferant.wareBestellen(holzEinheiten, schrauben, farbEinheiten, kartonEinheiten, glasEinheiten);

        // Assert
        assertFalse(result, "Die Bestellung sollte fehlschlagen, da die Gesamtmenge 0 ist.");
    }

    @Test
    public void testWareBestellenUeberMaxWert() {
        // Arrange
        int holzEinheiten = 1500; // Überschreitet maxHolzeinheiten
        int schrauben = 2000;
        int farbEinheiten = 100;
        int kartonEinheiten = 50;
        int glasEinheiten = 20;

        // Act
        boolean result = lieferant.wareBestellen(holzEinheiten, schrauben, farbEinheiten, kartonEinheiten, glasEinheiten);

        // Assert
        assertFalse(result, "Die Bestellung sollte fehlschlagen, da die maximale Holzeinheiten überschritten wurden.");
    }

    @Test
    public void testWareBestellenNegativeWerte() {
        // Arrange
        int holzEinheiten = -10;
        int schrauben = -50;
        int farbEinheiten = -5;
        int kartonEinheiten = -2;
        int glasEinheiten = -1;

        // Act
        boolean result = lieferant.wareBestellen(holzEinheiten, schrauben, farbEinheiten, kartonEinheiten, glasEinheiten);

        // Assert
        assertFalse(result, "Die Bestellung sollte fehlschlagen, da negative Werte angegeben wurden.");
    }

    @Test
    public void testWareBestellenGenauAmLimit() {
        // Arrange
        int holzEinheiten = 1000;
        int schrauben = 5000;
        int farbEinheiten = 500;
        int kartonEinheiten = 300;
        int glasEinheiten = 200;

        // Act
        boolean result = lieferant.wareBestellen(holzEinheiten, schrauben, farbEinheiten, kartonEinheiten, glasEinheiten);

        // Assert
        assertTrue(result, "Die Bestellung sollte erfolgreich sein, da sie genau an der Obergrenze liegt.");
    }
}
