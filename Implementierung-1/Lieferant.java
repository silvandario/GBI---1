/**
 * Diese Klasse simuliert die Lieferzeit des Lieferanten
 * und steuert die Lieferung von Materialien ins Lager.
 * 
 * @author Silvan
 * @version 1.1
 */
public class Lieferant extends Thread {
    private Lager lager;

    public Lieferant(Lager lager) {
        this.lager = lager;
    }

    @Override
    public void run() {
    try {
        System.out.println("Lieferant: Lieferung gestartet...");
        for (int i = 0; i < 20; i++) { // Simuliert längere Lieferung in kleinen Schritten
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Lieferant: Lieferung wurde unterbrochen!");
                return; // Liefervorgang abbrechen
            }
            Thread.sleep(100); // Simuliere eine kleine Wartezeit pro Schritt
        }
        System.out.println("Lieferant: Lieferung abgeschlossen!");
        lager.wareLiefern();
    } catch (InterruptedException e) {
        System.out.println("Lieferant: Lieferung durch Exception unterbrochen!");
        Thread.currentThread().interrupt(); // Interrupt-Flag wieder setzen
    }
    }       
}