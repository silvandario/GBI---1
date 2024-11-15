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
            Bestellbestätigung: true
            --------------------------------------------
            Bestellungsnummer: 2
            Anzahl Standardtüren: 2
            Anzahl Premiumtüren: 2
            Beschaffungszeit: 0
            Bestellbestätigung: true
            --------------------------------------------
            """;
        assertEquals(expectedOutput.trim(), getTrimmedOutput());
    }
    
    @Test
    void testLieferzeitBerechnung() {
        // Act
        fabrik.bestellungAufgeben(2, 3); // 2 Premium, 3 Standard
    
        // Retrieve Lieferzeit der Bestellung
        Bestellung bestellung = fabrik.getBestellungen().get(0);
        float actualLieferzeit = bestellung.gibLieferZeit();
    
        // Assert
        float expectedLieferzeit = 
            (2 * Premiumtuer.getProduktionszeit() + 3 * Standardtuer.getProduktionszeit()) / (60 * 24) + 1; // Beschaffungszeit hinzufügen
    
        // Vergleich mit Toleranz
        assertEquals(expectedLieferzeit, actualLieferzeit, 0.01, "Lieferzeit stimmt nicht überein.");
    }

    @Test
    void testBlackFridayKaufwahn() {
        // Arrange
        Fabrik fabrik = new Fabrik();

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fabrik.bestellungAufgeben(50000000, 1000000000); // Grosse Bestellung
        });

        // Überprüfen, dass die Exception die erwartete Fehlermeldung enthält
        String expectedMessage = "Standardtüren dürfen maximal 10000 Stück betragen.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }



    @Test   
    void testInvalidInputHandling() {
        // Test negative Eingabe
        fabrik.bestellungAufgeben(-5, 0);
        String actualOutput = getTrimmedOutput();
        assertTrue(actualOutput.contains("Du musst mindestens eine Türe bestellen"));
    
        // Test keine Eingabe
        fabrik.bestellungAufgeben(0, 0);
        actualOutput = getTrimmedOutput();
        assertTrue(actualOutput.contains("Du musst mindestens eine Türe bestellen"));
    }
    
    @Test
    public void testNichtVollesLagerAuffuellen() {
        // sollte machbar sein
        // Arrange
        
    
        fabrik = new Fabrik();
        
        fabrik.bestellungAufgeben(10, 20); // Bestellung 1
        
        fabrik.bestellungAufgeben(5, 0);

        // Act
        fabrik.lagerAuffuellenUndAusgeben();
        String actualMessage = getTrimmedOutput();
        String expectedMessage="---Lager wieder bei 100% aufgefüllt";
        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testInkrementelleBestellnummern() {
        // Arrange
        fabrik.bestellungAufgeben(1, 1); // Bestellung 1
        fabrik.bestellungAufgeben(2, 2); // Bestellung 2
    
        // Act
        fabrik.bestellungenAusgeben();
    
        // Assert
        String output = getTrimmedOutput();
        assertTrue(output.contains("Bestellungsnummer: 1"));
        assertTrue(output.contains("Bestellungsnummer: 2"));
    }

    @Test
    void testLagerInteraktionNormalerGebrauch() {
        // Simuliere eine Bestellung und Lagerauffüllung
        
        fabrik.bestellungAufgeben(3, 2);
        fabrik.lagerAuffuellenUndAusgeben();
    
        // Assert
        String actualOutput = getTrimmedOutput();

        assertTrue(actualOutput.contains("Bestellung mit Bestellungsnummer 1 wurde erfolgreich aufgegeben."));
        assertTrue(actualOutput.contains("Lager wieder bei 100% aufgefüllt"));
    }
    @Test
    void testLagerInteraktionVollesLager() {
            // Volles Lager wird versucht aufzufüllen
            
            fabrik.lagerAuffuellenUndAusgeben();
            
        
            // Assert
            String actualOutput = getTrimmedOutput();
    
            assertTrue(actualOutput.contains("Das Lager ist voll"));
     
    }
    
        @Test
        void testLagerbestandNachBestellung() {    

            // Act
            fabrik.bestellungAufgeben(2, 3); // 2 Premiumtüren, 3 Standardtüren
        
            // Assert: Ausgabe überprüfen (dies könnte durch Mocking der Konsole präzisiert werden)
            fabrik.lagerbestandVonFarbrik();
        
            // Überprüfen der Werte
            assertEquals(1000 - (2 * Premiumtuer.getHolzeinheiten() + 3 * Standardtuer.getHolzeinheiten()), fabrik.getVorhandeneHolzeinheiten());
            assertEquals(5000 - (2 * Premiumtuer.getSchrauben() + 3 * Standardtuer.getSchrauben()), fabrik.getVorhandeneSchrauben());
            assertEquals(500 - (2 * Premiumtuer.getFarbeinheiten() + 3 * Standardtuer.getFarbeinheiten()), fabrik.getVorhandeneFarbeinheiten());
            assertEquals(300 - (2 * Premiumtuer.getKartoneinheiten() + 3 * Standardtuer.getKartoneinheiten()), fabrik.getVorhandeneKartoneinheiten());
            assertEquals(200 - (2 * Premiumtuer.getGlaseinheiten()), fabrik.getVorhandeneGlaseinheiten());
}
}
