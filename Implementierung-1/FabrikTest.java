import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Die Test-Klasse FabrikTest.
 *
 * @author  Silvan Ladner
 * @version 1
 */
public class FabrikTest
{
    /**
     * Konstruktor fuer die Test-Klasse FabrikTest
     */
    public FabrikTest()
    {
    }

    /**
     *  Setzt das Testgerüst fuer den Test.
     *
     * Wird vor jeder Testfall-Methode aufgerufen.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Gibt das Testgerüst wieder frei.
     *
     * Wird nach jeder Testfall-Methode aufgerufen.
     */
    @AfterEach
    public void tearDown()
    {
    }
    
    @Test
    void testNichtNegativeAnzahlProdukteBestellen() {
        //Arrange
        Fabrik fabrik = new Fabrik();
        String expectedResult = """
        Bestellungsnummer: 1
        Anzahl Standardtüren: 1
        Anzahl Premiumtüren: 4
        Beschaffungszeit: 0
        Bestellbestätigung: false
        --------------------------------------------
        Bestellungsnummer: 2
        Anzahl Standardtüren: 100
        Anzahl Premiumtüren: 400
        Beschaffungszeit: 0
        Bestellbestätigung: false
        -------------------------------------------- 
        Bestellungsnummer: 3
        Anzahl Standardtüren: 0
        Anzahl Premiumtüren: 1
        Beschaffungszeit: 0
        Bestellbestätigung: false
        --------------------------------------------
        """;
        //Act
        fabrik.bestellungAufgeben(1, 4); // geringe Anzahl
        fabrik.bestellungAufgeben(100, 400); // grosse Anzahl
        fabrik.bestellungAufgeben(0, 1);  // ist 0 Betstellung möglich bei einzelner Türe
        String result=fabrik.bestellungenAusgeben();
        
        assertEquals(expectedResult, result);
    }
    @Test
    void testLeereBestellung() {
        //Arrange
        Fabrik fabrik = new Fabrik();
        //Act
        String result = fabrik.bestellungAufgeben(0, 0); // keine Anzahl
        
        //Assert
        String expectedResult = "Bestellung mit Bestellungsnummer 1 konnte nicht erfolgreich aufgegeben werden. Du musst mindestens eine Türe bestellen.";
        assertEquals(expectedResult, result);

    }

     
}