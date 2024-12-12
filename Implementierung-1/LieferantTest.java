import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Testklasse für die Klasse Lieferant.
 * Überprüft die Funktionalität der Materiallieferung ins Lager.
 * 
 * @author Silvan
 * @version 1.3
 */
public class LieferantTest {

    @Test
    public void testLieferungErfolgreich() throws InterruptedException {
        // Arrange
        Lager lager = new Lager();
        lager.setVorhandeneHolzeinheiten(0); // Simuliere leeres Lager
        Lieferant lieferant = new Lieferant(lager);
    
        // Act
        Thread lieferantThread = new Thread(lieferant);
        lieferantThread.start();
    
        Thread.sleep(3000); // Warte, bis die Lieferung abgeschlossen ist
        lieferant.beenden(); // Beende den Lieferant-Thread
        lieferantThread.join(); // Warte, bis der Thread beendet ist
    
        // Assert
        assertEquals(1000, lager.getVorhandeneHolzeinheiten(), "Das Lager sollte vollständig aufgefüllt sein.");
        assertEquals(5000, lager.getVorhandeneSchrauben(), "Das Lager sollte vollständig aufgefüllt sein.");
        assertEquals(500, lager.getVorhandeneFarbeinheiten(), "Das Lager sollte vollständig aufgefüllt sein.");
        assertEquals(300, lager.getVorhandeneKartoneinheiten(), "Das Lager sollte vollständig aufgefüllt sein.");
        assertEquals(200, lager.getVorhandeneGlaseinheiten(), "Das Lager sollte vollständig aufgefüllt sein.");
    }
        @Test
    public void testUnterbrechungDerLieferung() throws InterruptedException {
        // Arrange
        Lager lager = new Lager();
        lager.setVorhandeneHolzeinheiten(500); // Teilweise aufgefülltes Lager
        Lieferant lieferant = new Lieferant(lager);
        Thread lieferantThread = new Thread(lieferant);
    
        // Act
        lieferantThread.start();
        Thread.sleep(300); // Warte, bis die Lieferung begonnen hat
        lieferant.beenden(); // Beende den Lieferant-Thread
        lieferantThread.join(); // Warte, bis der Thread beendet ist
    
        // Assert
        assertEquals(500, lager.getVorhandeneHolzeinheiten(), "Das Lager sollte unverändert bleiben.");
    }

    @Test
    public void testKeineMaterialänderungBeiUnterbrechung() throws InterruptedException {
        // Arrange
        Lager lager = new Lager();
        lager.setVorhandeneHolzeinheiten(1000); // Voll aufgefülltes Lager
        Lieferant lieferant = new Lieferant(lager);
        Thread lieferantThread = new Thread(lieferant);
    
        // Act
        lieferantThread.start();
        Thread.sleep(1000); // Kurze Wartezeit
        lieferant.beenden(); // Lieferant beenden
        lieferantThread.join(); // Warte, bis der Thread beendet ist
    
        // Assert
        assertEquals(1000, lager.getVorhandeneHolzeinheiten(), "Die Holzeinheiten sollten unverändert bleiben.");
        assertEquals(5000, lager.getVorhandeneSchrauben(), "Die Schrauben sollten unverändert bleiben.");
        assertEquals(500, lager.getVorhandeneFarbeinheiten(), "Die Farbeinheiten sollten unverändert bleiben.");
        assertEquals(300, lager.getVorhandeneKartoneinheiten(), "Die Kartoneinheiten sollten unverändert bleiben.");
        assertEquals(200, lager.getVorhandeneGlaseinheiten(), "Die Glaseinheiten sollten unverändert bleiben.");
    }
}