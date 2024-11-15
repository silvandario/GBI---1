import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Diese Testklasse prüft die Klasse Lager.
 * Testet Methoden wie lagerBestandAusgeben, lagerAuffuellen und gibBeschaffungsZeit.
 * 
 * @author Silvan Ladner
 * @version 1.0
 */
public class LagerTest {

    private Lager lager;
    private Fabrik fabrik;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        lager = new Lager();
        fabrik = new Fabrik();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        outputStream.reset();
    }

    /**
     * Hilfsmethode, um die Konsolenausgabe zu holen und zu trimmen.
     */
    private String getTrimmedOutput() {
        String output = outputStream.toString();
        outputStream.reset();
        return output.trim();
    }

    @Test
    public void testLagerBestandAusgeben() {
        // Act
        lager.lagerBestandAusgeben();
    
        // Normalize actual output
        String actualOutput = getTrimmedOutput().replaceAll("\\s+", " ").trim();
    
        // Normalize expected output
        String expectedOutput = """
            -----------Lagerbestand-----------
            1000 Holzeinheiten vorhanden
            5000 Schrauben vorhanden
            500 Farbeinheiten vorhanden
            300 Kartoneinheiten vorhanden
            200 Glaseinheiten vorhanden
            ----------- ----------- -----------
            """.replaceAll("\\s+", " ").trim();
    
        // Assert
        assertEquals(expectedOutput, actualOutput);
    }
    

    @Test
    public void testVollesLagerAuffuellen() {
        // sollte natürlich nicht möglich sein, da kein sinn macht
        // das nicht volle Lager Auffüll-testing befindet sich bei Fabrik-Test
        // Arrange
        lager = new Lager();

        // Act
        lager.lagerAuffuellen();
        String actualOutput = getTrimmedOutput();
        String expectedOutput="Bestellung nicht getätigt! Das Lager ist voll :)";
        // Assert
        assertEquals(expectedOutput, actualOutput);
    }

    
    @Test
    void reduziereLagerUmPositiveAnzahl() {
        // es soll nicht möglich sein, einen Logikfehler zu machen und Bestände zuzubuchen wenn diese für Bestellung verwendet wserden
        // Arrange
        Lager lager = new Lager(); 
    
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            lager.setVorhandeneGlaseinheiten(-12); // Negativer Betrag, sollte IllegalArgumentException werfen
        });
    
        // Exception soll die exakte Fehlermeldung enthalten
        String expectedMessage = "Ungültige Menge für Glaseinheiten.";
        String actualMessage = exception.getMessage();
    
        //assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

   

}
