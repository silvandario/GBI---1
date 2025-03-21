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
        Bestellung bestellung = new Bestellung(1, 1, 1);
        produktionsManager.fuegeZuVerarbeitendeBestellungenHinzu(bestellung);
        produktionsManager.bearbeiteBestellungen();
        Thread.sleep(3000); // Zeit geben, damit die Bestellung verarbeitet wird
        String output = getTrimmedOutput();
        assertTrue(output.contains("Bearbeitung abgeschlossen"), "Bearbeitung sollte abgeschlossen");
        
    }
    
    @Test
    public void testProduktionEinerBestellungGross() throws InterruptedException {
        Bestellung bestellung = new Bestellung(30, 0, 0); // Bestellung mit 30 Standardtüren
        produktionsManager.fuegeZuVerarbeitendeBestellungenHinzu(bestellung);
        produktionsManager.bearbeiteBestellungen();
        Thread.sleep(65000); // Wartezeit anpassen für größere Bestellungen
    
        String output = getTrimmedOutput();
    
        // Überprüfen, ob die Ausgabe 30 Mal das erwartete Muster enthält
        String regex = "Produkt bereit zur Auslieferung: Standardtuer@[a-fA-F0-9]+";
        long count = output.lines().filter(line -> line.matches(regex)).count();
        assertEquals(30, count, "Es sollten 30 Produkte bereit zur Auslieferung sein.");
    }
    
    @Test
    public void testMaterialMangelBestellungAbgelehnt() throws InterruptedException {
        lager.setVorhandeneHolzeinheiten(0);
        lager.setVorhandeneGlaseinheiten(0);
        lager.setVorhandeneFarbeinheiten(0);// Lagerbestand simulieren
        Bestellung bestellung = new Bestellung(1, 1, 1);
        produktionsManager.fuegeZuVerarbeitendeBestellungenHinzu(bestellung);
        produktionsManager.bearbeiteBestellungen();
        Thread.sleep(3000); // Wartezeit für Verarbeitung
        String output = getTrimmedOutput();
        assertTrue(output.contains("Materialmangel! Bestellung 1 kann nicht bearbeitet werden."), "Es sollte eine Meldung über Materialmangel geben.");
    }
    
    @Test
    public void testMehrereBestellungenInReihenfolge() throws InterruptedException {
        Bestellung bestellung1 = new Bestellung(2, 1, 1);
        Bestellung bestellung2 = new Bestellung(0, 3, 2);
        produktionsManager.fuegeZuVerarbeitendeBestellungenHinzu(bestellung1);
        produktionsManager.fuegeZuVerarbeitendeBestellungenHinzu(bestellung2);
        Thread.sleep(30000); // Zeit geben für beide Bestellungen
        String output = getTrimmedOutput();
        assertTrue(output.contains("Produktion gestartet: Bestellung 1"), "Produktion der ersten Bestellung sollte starten.");
        assertTrue(output.contains("Produktion abgeschlossen: Bestellung 1"), "Produktion der ersten Bestellung sollte abgeschlossen sein.");
        assertTrue(output.contains("Produktion gestartet: Bestellung 2"), "Produktion der zweiten Bestellung sollte starten.");
        assertTrue(output.contains("Produktion abgeschlossen: Bestellung 2"), "Produktion der zweiten Bestellung sollte abgeschlossen sein.");
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