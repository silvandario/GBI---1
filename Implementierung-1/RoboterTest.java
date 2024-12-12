import org.junit.jupiter.api.*;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse für die Klasse Roboter.
 * Diese Klasse überprüft die Funktionalität des Roboters und dessen Interaktion mit Produkten.
 * 
 * @author Silvan
 * @version 1.1
 */
public class RoboterTest {

    private Roboter roboter;
    private Produkt produkt;

    @BeforeEach
    public void setUp() {
        roboter = new Roboter("TestRoboter");
        produkt = new Standardtuer(); 
    }

    @Test
    public void testSetzteProduktionsAblaufMitGueltigerListe() {
        // Arrange
        LinkedList<Roboter> produktionsAblauf = new LinkedList<>();
        produktionsAblauf.add(new Roboter("Holzbearbeitungs_Roboter"));
        produktionsAblauf.add(new Roboter("Montage_Roboter"));

        // Act
        produkt.setzteProduktionsAblauf(produktionsAblauf);

        // Assert
        assertNotNull(produkt, "Produktionsablauf sollte gesetzt sein.");
    }

    @Test
    public void testSetzteProduktionsAblaufMitLeererListe() {
        // Arrange
        LinkedList<Roboter> produktionsAblauf = new LinkedList<>();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> produkt.setzteProduktionsAblauf(produktionsAblauf),
            "Eine IllegalArgumentException sollte bei einer leeren Liste geworfen werden."
        );

        assertEquals("Produktionsablauf darf nicht leer sein.", exception.getMessage());
    }

    @Test
    public void testSetzteProduktionsAblaufMitNullListe() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> produkt.setzteProduktionsAblauf(null),
            "Eine IllegalArgumentException sollte bei null geworfen werden."
        );

        assertEquals("Produktionsablauf darf nicht leer sein.", exception.getMessage());
    }

    @Test
    public void testFuegeProduktHinzu() {
        // Act
        roboter.fuegeProduktHinzu(produkt);

        // Assert
        assertEquals(1, roboter.warteschlange.size(), "Warteschlange sollte ein Produkt enthalten.");
        assertEquals(produkt, roboter.warteschlange.peek(), "Das hinzugefügte Produkt sollte in der Warteschlange sein.");
    }

    @Test
    public void testProduktionszeitStandardtuer() {
        // Arrange
        Standardtuer standardTuer = new Standardtuer();
        int erwarteteProduktionszeit = standardTuer.getProduktionszeit();// * 1000; // In Millisekunden

        // Act
        long startTime = System.currentTimeMillis();
        roboter.produziereProdukt(standardTuer);
        long endTime = System.currentTimeMillis();

        // Assert
        long dauer = endTime - startTime;
        assertTrue(dauer >= erwarteteProduktionszeit && dauer <= erwarteteProduktionszeit + 5,//anpassen für echtfall
                "Die Produktionszeit für Standardtuer sollte " + erwarteteProduktionszeit +
                " ms betragen, aber es waren: " + dauer + " ms.");
    }

    @Test
    public void testProduktionszeitPremiumtuer() {
        // Arrange
        Premiumtuer premiumTuer = new Premiumtuer();
        int erwarteteProduktionszeit = premiumTuer.getProduktionszeit(); // * 1000; // In Millisekunden

        // Act
        long startTime = System.currentTimeMillis();
        roboter.produziereProdukt(premiumTuer);
        long endTime = System.currentTimeMillis();

        // Assert
        long dauer = endTime - startTime;
        assertTrue(dauer >= erwarteteProduktionszeit && dauer <= erwarteteProduktionszeit + 6,//anpassen für echtfall
                "Die Produktionszeit für Premiumtuer sollte " + erwarteteProduktionszeit +
                " ms betragen, aber es waren: " + dauer + " ms.");
    }

    @Test
    public void testProduziereProdukt() throws InterruptedException {
        // Arrange
        roboter.setzteProduktionsZeit(100); // Produktionszeit reduzieren für den Test
        roboter.fuegeProduktHinzu(produkt);

        // Act
        Thread roboterThread = new Thread(roboter);
        roboterThread.start();
        Thread.sleep(200); // Warten, bis der Roboter das Produkt verarbeitet

        // Assert
        assertEquals(0, roboter.warteschlange.size(), "Warteschlange sollte leer sein, nachdem das Produkt produziert wurde.");
        assertEquals(3, produkt.aktuellerZustand(), "Produkt sollte fertiggestellt sein.");
    }

    @Test
    public void testRoboterStop() {
    // Arrange
    roboter.fuegeProduktHinzu(produkt);

    // Act
    roboter.interrupt();

    // Kleine Pause, um sicherzustellen, dass der Interrupt verarbeitet wurde
    try {
        Thread.sleep(100);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    // Assert
    assertTrue(roboter.isInterrupted(), "Der Roboter-Thread sollte unterbrochen sein.");
}

    @Test
    public void testRoboterBearbeitetProdukteInReihenfolge() throws InterruptedException {
        // Arrange
        Standardtuer standardTuer = new Standardtuer();
        Premiumtuer premiumTuer = new Premiumtuer();
        roboter.fuegeProduktHinzu(standardTuer);
        roboter.fuegeProduktHinzu(premiumTuer);

        // Act
        Thread roboterThread = new Thread(roboter);
        roboterThread.start();
        Thread.sleep(4000); // Warten, bis beide Produkte verarbeitet wurden

        // Assert
        assertEquals(0, roboter.warteschlange.size(), "Warteschlange sollte leer sein.");
        assertEquals(3, standardTuer.aktuellerZustand(), "Standardtuer sollte fertiggestellt sein.");
        assertEquals(3, premiumTuer.aktuellerZustand(), "Premiumtuer sollte fertiggestellt sein.");
    }

    @Test
    public void testProduktionsAblaufInteraktion() {

        // Arrange
        LinkedList<Roboter> produktionsAblauf = new LinkedList<>();
        produktionsAblauf.add(roboter);
        Standardtuer standardTuer = new Standardtuer();
        standardTuer.setzteProduktionsAblauf(produktionsAblauf);

        // Act
        standardTuer.naechsteProduktionsStation();

        // Assert
        assertEquals(1, roboter.warteschlange.size(), "Warteschlange des Roboters sollte ein Produkt enthalten.");
        assertEquals(2, standardTuer.aktuellerZustand(), "Produkt sollte den Zustand 'in Produktion' haben.");
    }


}