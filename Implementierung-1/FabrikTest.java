

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Die Test-Klasse FabrikTest.
 *
 * @author  (Ihr Name)
 * @version (eine Versionsnummer oder ein Datum)
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
    public void testNichtNegativeAnzahlProdukteBestellen() {
        Fabrik fabrik = new Fabrik();
        fabrik.bestellungAufgeben(1, 4); // geringe Anzahl
        fabrik.bestellungAufgeben(100, 400); // grosse Anzahl
        fabrik.bestellungAufgeben(0, 1);  // ist 0 Betstellung möglich bei einzelner Türe
        fabrik.bestellungenAusgeben();
    }
    
    //Arrange
    
    //Act
    
    //Assert
    
    fabrik.bestellungAufgeben(0, 0); // Leere Bestellung
    fabrik.bestellungAufgeben(-10, 10); // negative menge  
}
