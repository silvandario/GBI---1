/**
 * Diese Klasse simuliert die Lieferzeit des Lieferanten
 * und steuert die Lieferung von Materialien ins Lager.
 * 
 * @author Silvan
 * @version 1.2
 */
public class Lieferant extends Thread {
    private Lager lager;
    private volatile boolean isRunning = true;

    public Lieferant(Lager lager) {
        this.lager = lager;
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                synchronized (lager) {
                    while (!lager.isLeer() && isRunning) {
                        System.out.println("Lieferant: Warten auf leeres Lager...");
                        lager.wait(); // Warten, bis Lager leer ist oder Thread beendet wird
                    }
                }

                if (!isRunning) {
                    System.out.println("Lieferant: Beenden des Threads...");
                    break; // Schleife verlassen
                }

                System.out.println("Lieferant: Lieferung gestartet...");
                Thread.sleep(2000); // Simulierte Lieferzeit
                synchronized (lager) {
                    lager.wareLiefern();
                    System.out.println("Lieferant: Lieferung abgeschlossen.");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Lieferant: Thread unterbrochen!");
            Thread.currentThread().interrupt(); // Setzt den Interrupt-Status zurück
        }
    }

    public void beenden() {
        System.out.println("Lieferant: Beenden angefordert...");
        isRunning = false; // Zustand auf nicht mehr laufend setzen
        synchronized (lager) {
            lager.notifyAll(); // Sicherstellen, dass `wait` beendet wird
        }
        this.interrupt(); // Thread unterbrechen
    }
}