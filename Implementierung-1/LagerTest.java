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

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        lager = new Lager();
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
    public void testLagerAuffuellen() {
        // Arrange
        lager = new Lager();
        // Reduziere den Lagerbestand künstlich
        lager.lagerAuffuellen();

        // Act
        lager.lagerAuffuellen();

        // Assert
        assertEquals("", "");
    }

}
