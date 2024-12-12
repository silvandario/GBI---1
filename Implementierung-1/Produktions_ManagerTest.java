
import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

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
        lager = new Lager(); // Annahme: Lager wird initialisiert mit maximalen Ressourcen
        fabrik = new Fabrik();
        produktionsManager = new Produktions_Manager(fabrik, lager);
    }

    @AfterEach
    public void tearDown() {
        // Standardausgabe wiederherstellen
        System.setOut(originalOut);
        produktionsManager.interrupt(); // Produktions-Thread stoppen
        outputStream.reset(); // Stream leeren
    }

    @Test
    public void testNormaleBestellungVerarbeitung() throws InterruptedException {
        // Arrange
        Bestellung bestellung = new Bestellung(2, 3, 1); // 2 Premium-, 3 Standardtüren
        produktionsManager.fuegeZuVerarbeitendeBestellungenHinzu(bestellung);
    
        // Act
        Thread.sleep(4000); // Wartezeit erhöhen, um die Verarbeitung zuzulassen
    
        // Assert
        String output = getTrimmedOutput();
        assertTrue(output.contains("Neue Bestellung hinzugefügt: 1"), "Neue Bestellung sollte hinzugefügt worden sein.");
        assertTrue(output.contains("Produktion gestartet: Bestellung 1"), "Produktion sollte gestartet sein.");
        assertTrue(output.contains("Produktion abgeschlossen: Bestellung 1"), "Produktion sollte abgeschlossen sein.");
    
        // Zusätzliche Überprüfung: Bestellung wurde tatsächlich bestätigt
        assertTrue(bestellung.gibBestellBestaetigung(), "Die Bestellung sollte verarbeitet und bestätigt werden.");
    }

    @Test
    public void testLeeresLagerMaterialmangel() throws InterruptedException {
        // Arrange
        lager.setVorhandeneHolzeinheiten(0); // Simulate an empty stock
        Bestellung bestellung = new Bestellung(1, 1, 1); // 1 Premium-, 1 Standardtür
        produktionsManager.fuegeZuVerarbeitendeBestellungenHinzu(bestellung);
    
        // Act
        Thread.sleep(2000); // Allow time for the manager to process
    
        // Assert
        String output = getTrimmedOutput();
        assertTrue(output.contains("Material nicht verfügbar für Bestellung: 1"), "Es sollte eine Meldung über Materialmangel geben.");
        assertFalse(bestellung.gibBestellBestaetigung(), "Die Bestellung sollte abgelehnt werden, da Material fehlt.");
    }

    /**
     * Hilfsmethode: Trimmt die Konsolenausgabe und gibt sie als String zurück.
     */
    public String getTrimmedOutput() {
        String output = outputStream.toString();
        outputStream.reset();
        return output.trim();
    }

}

