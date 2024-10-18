

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Die Test-Klasse ProduktTest.
 *
 * @author  (Ihr Name)
 * @version (eine Versionsnummer oder ein Datum)
 */
public class ProduktTest
{
    /**
     * Konstruktor fuer die Test-Klasse ProduktTest
     */
    public ProduktTest()
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
    public void testInitialisierungZustand() {
        //Arrange
        Produkt produkt = new Produkt();
        //Act
        int result = produkt.getAktuellerZustand();
        
        //Assert
        int expectedResult = 1; // Initialwert vom Zustand wurde auff 1 gesetzt
        assertEquals(expectedResult, result);

    }
    
    @Test
    public void testVeraendereZustandInnerhalbBereich() {
        //Arrange
        Produkt produkt = new Produkt();
        //Act
        produkt.zustandAendern(3); // valider Zustand
        int result = produkt.getAktuellerZustand();
        
        //Assert
        int expectedResult = 3; // Nun wird 3 erwartet
        assertEquals(expectedResult, result);

    }
    
    @Test
    public void testVeraendereZustandAusserhalbBereich() {
        //Arrange
        Produkt produkt = new Produkt();
        //Act
        produkt.zustandAendern(7); // invalider Zustand
        int result = produkt.getAktuellerZustand();
        
        //Assert
        int expectedResult = 5; // Nun wird 5 erwartet - das Produkt soll auffallen
        assertEquals(expectedResult, result);

    }
}
