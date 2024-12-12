

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Die Test-Klasse StandardtuerTest. Geprüft wird ob die MEthode von Superklasse PRodukt
 * richtig vererbt wird und ob eine zufällig ausgewählte get-Methode funktioniert
 *
 * @author  Silvan Ladner
 * @version 1
 */
public class StandardtuerTest
{
    /**
     * Konstruktor fuer die Test-Klasse StandardtuerTest
     */
    public StandardtuerTest()
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
    public void testStandardtuerHatSuperklasseMethode() {
        //Arrange
        Standardtuer standardtuer = new Standardtuer();
        //Act
        int result = standardtuer.aktuellerZustand();
        
        //Assert
        int expectedResult = 1; // Initialwert vom Zustand wurde auff 1 gesetzt
        assertEquals(expectedResult, result);

    }
    
    @Test
    public void testGibRichtigeFinalenWertZeit() {
        //Arrange
        Standardtuer standardtuer = new Standardtuer();
        //Act
        int result = standardtuer.getProduktionszeit();
        
        //Assert
        int expectedResult = 10; // wie VOrgeschrieben
        assertEquals(expectedResult, result);

    }
}
