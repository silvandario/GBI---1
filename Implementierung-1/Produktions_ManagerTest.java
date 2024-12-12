import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse für Produktions_Manager.
 */
public class Produktions_ManagerTest {

    private Produktions_Manager produktionsManager;
    private Fabrik fabrik;
    private Lager lager;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        // Standardausgabe umleiten
        System.setOut(new PrintStream(outputStream));
        lager = new Lager();
        fabrik = new Fabrik();
        produktionsManager = new Produktions_Manager(fabrik, lager);
        produktionsManager.start();
    }

    @AfterEach
    public void tearDown() {
        produktionsManager.beenden(); // Stoppt den Produktions-Thread
        System.setOut(originalOut); // Standardausgabe wiederherstellen
        outputStream.reset();
    }

    @Test
public void testProduktionEinerBestellung() throws InterruptedException {
    // Arrange
    Bestellung bestellung = new Bestellung(2, 3, 0); // 2 Premium-, 3 Standardtüren
    produktionsManager.fuegeZuVerarbeitendeBestellungenHinzu(bestellung);

    // Act
    Thread.sleep(5000); // Allow enough time for processing

    // Assert
    String output = getTrimmedOutput();
    assertTrue(output.contains("Produktion gestartet: Bestellung 1"), "Produktion sollte gestartet sein.");
    assertTrue(output.contains("Produktion abgeschlossen: Bestellung 1"), "Produktion sollte abgeschlossen sein.");
}

   @Test
public void testMaterialMangelBestellungAbgelehnt() throws InterruptedException {
    // Arrange
    lager.setVorhandeneHolzeinheiten(0); // Simulate empty stock
    Bestellung bestellung = new Bestellung(1, 2, 0); // 1 Premium-, 2 Standardtüren
    produktionsManager.fuegeZuVerarbeitendeBestellungenHinzu(bestellung);

    // Act
    Thread.sleep(3000); // Wait for processing

    // Assert
    String output = getTrimmedOutput();
    assertTrue(output.contains("Material nicht verfügbar für Bestellung: 1"), "Es sollte eine Meldung über Materialmangel geben.");
}

    @Test
public void testMehrereBestellungenInReihenfolge() throws InterruptedException {
    // Arrange
    Bestellung bestellung1 = new Bestellung(2, 1, 0); // 2 Premium-, 1 Standardtür
    Bestellung bestellung2 = new Bestellung(0, 3, 0); // 3 Standardtüren
    produktionsManager.fuegeZuVerarbeitendeBestellungenHinzu(bestellung1);
    produktionsManager.fuegeZuVerarbeitendeBestellungenHinzu(bestellung2);

    // Act
    Thread.sleep(10000); // Wait for both orders to be processed

    // Assert
    String output = getTrimmedOutput();
    assertTrue(output.contains("Produktion gestartet: Bestellung 1"), "Produktion der ersten Bestellung sollte starten.");
    assertTrue(output.contains("Produktion abgeschlossen: Bestellung 1"), "Produktion der ersten Bestellung sollte abgeschlossen sein.");
    assertTrue(output.contains("Produktion gestartet: Bestellung 2"), "Produktion der zweiten Bestellung sollte starten.");
    assertTrue(output.contains("Produktion abgeschlossen: Bestellung 2"), "Produktion der zweiten Bestellung sollte abgeschlossen sein.");
}

    @Test
    public void testBeendenVonProduktionsManager() throws InterruptedException {
        // Act
        produktionsManager.beenden();

        // Assert
        assertFalse(produktionsManager.isAlive(), "Produktionsmanager-Thread sollte beendet sein.");
    }

    /**
     * Hilfsmethode: Trimmt die Konsolenausgabe und gibt sie als String zurück.
     */
    private String getTrimmedOutput() {
        String output = outputStream.toString();
        outputStream.reset();
        return output.trim();
    }
}