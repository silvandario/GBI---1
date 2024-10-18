import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Diese Testklasse prüft die Fabrik. Da void Methoden vorhanden sind, wobei oft Output in die Konsole geprintet wird, wird deiser Output als Referenz genommen.
 * 
 * @author Silvan Ladner
 * @version 3.2
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
        outputStream.reset(); //leeren
    }

    @Test
    void testErfolgreicheBestellung() {
        fabrik.bestellungAufgeben(2, 3);
        String expectedOutput = "Bestellung mit Bestellungsnummer 1 wurde erfolgreich aufgegeben.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testLeereBestellung() {
        fabrik.bestellungAufgeben(0, 0); // macht kein Sinn für nichts iene Bestellung aufzugeben
        String expectedOutput = "Bestellung konnte nicht erfolgreich aufgegeben werden. Du musst mindenstens eine Türe bestellen beziehungsweise kannst keine negative Anzahl bestellen.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testNegativeBestellung() {
        fabrik.bestellungAufgeben(-1, 3); // hier ein Typo (das Minus). Hier soll so gehandelt werden, dass ein Feedback gegeben wird.
        String expectedOutput = "Bestellung konnte nicht erfolgreich aufgegeben werden. Du musst mindenstens eine Türe bestellen beziehungsweise kannst keine negative Anzahl bestellen.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testBestellungsNummernKorrekt() {
        fabrik.bestellungAufgeben(1, 1); // Bestellung 1
        fabrik.bestellungAufgeben(2, 2); // Bestellung 2

        // Überprüfen, ob die Bestellnummern korrekt inkrementiert wurden
        fabrik.bestellungenAusgeben();
        String expectedOutput = """
            Bestellung mit Bestellungsnummer 1 wurde erfolgreich aufgegeben.
            Bestellung mit Bestellungsnummer 2 wurde erfolgreich aufgegeben.
            Bestellungsnummer: 1
            Anzahl Standardtüren: 1
            Anzahl Premiumtüren: 1
            Beschaffungszeit: 0
            Bestellbestätigung: false
            --------------------------------------------
            Bestellungsnummer: 2
            Anzahl Standardtüren: 2
            Anzahl Premiumtüren: 2
            Beschaffungszeit: 0
            Bestellbestätigung: false
            --------------------------------------------
            """;
        assertEquals(expectedOutput, outputStream.toString());
    }
}
