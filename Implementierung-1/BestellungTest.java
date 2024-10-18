

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Die Test-Klasse BestellungTest. Prüft, ob verschieden Arten der Bestellungen korrekt aufegegeben werden können und Bestellinforamtionen korrekt ausgegeben werden.
 * DIe Tests werden für die Standardtuere geschrieben, da die Logik für Premiumtuer identisch ist.
 *
 * @author  Silvan Ladner
 * @version 1
 */
public class BestellungTest
{
    /**
     * Konstruktor fuer die Test-Klasse BestellungTest
     */
    public BestellungTest()
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
    public void testBestellePositiveAnzahl() {
        //Arrange
        Bestellung bestellung = new Bestellung(10, 20, 1); // alle Mengen < 0
        //Act
        int result = bestellung.gibAnzahlStandardTueren(); // Rückgabe Standardtüren
        
        //Assert
        int expectedResult = 10; // Nun wird 10 erwartet
        assertEquals(expectedResult, result);

    }
    
    @Test
    public void testBestelleNichtNegativeAnzahl() {
        //Arrange
        Bestellung bestellung = new Bestellung(0, 10, 1); // Menge der Standardtüren ist 0
        
        //Act
        int result = bestellung.gibAnzahlStandardTueren(); // Rückgabe Standardtüren
        
        //Assert
        int expectedResult = 0; // Nun wird 0 erwartet
        assertEquals(expectedResult, result);

    }
    
    @Test
    public void testBestelleNegativeAnzahl() {
        //Arrange
        Bestellung bestellung = new Bestellung(-10, 10, 1); // Menge der Standardtüren ist Ausversehen -10
        
        //Act
        int result = bestellung.gibAnzahlStandardTueren(); // Rückgabe Standardtüren
        
        //Assert
        int expectedResult = 10; // Nun wird 0 erwartet
        assertEquals(expectedResult, result);

    }
    
    @Test
    public void testBestelleAnzahlTurenNach() {
        //Arrange
        Bestellung bestellung = new Bestellung(10, 0, 1); // Normale BEstellung
        bestellung.standartTuereHinzufuegen(); // Nochmals 1 Standardtueren drauf
        bestellung.standartTuereHinzufuegen(); // Nochmals 1 Standardtueren drauf; nun muss total 12 sein
        //Act
        int result = bestellung.gibBestellteProdukte().size(); // Rückgabe Standardtüren
        
        //Assert
        int expectedResult = 12; // Nun wird 12 (10+2) erwartet
        assertEquals(expectedResult, result);

    }
    
    
}
