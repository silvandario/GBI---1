

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Die Test-Klasse PremiumtuerTest. Geprüft wird die erfolgreiche Verebrung der Produktklasse und eine zufällig 
 * ausgewählte get Methode
 *
 * @author  Silvan Ladner
 * @version 1
 */
public class PremiumtuerTest
{
    /**
     * Konstruktor fuer die Test-Klasse PremiumtuerTest
     */
    public PremiumtuerTest()
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
    public void testPremiumtuerHatSuperklasseMethode() {
        //Arrange
        Premiumtuer premiumtuer = new Premiumtuer();
        //Act
        int result = premiumtuer.getAktuellerZustand();
        
        //Assert
        int expectedResult = 1; // Initialwert vom Zustand wurde auff 1 gesetzt
        assertEquals(expectedResult, result);

    }
    
    @Test
    public void testGibRichtigeFinalenWertZeit() {
        //Arrange
        Premiumtuer premiumtuer = new Premiumtuer();
        //Act
        int result = premiumtuer.getProduktionszeit();
        
        //Assert
        int expectedResult = 30; // wie vorgeschrieben
        assertEquals(expectedResult, result);

    }

}

