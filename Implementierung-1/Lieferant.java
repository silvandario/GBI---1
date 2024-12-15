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
                if (!lager.isLeer()) {
                    lager.wait(); // Wait for stock to empty
                    continue; // Skip rest of the iteration if the stock is not empty
                }
            }
            System.out.println("Lieferant: Lieferung gestartet...");
            Thread.sleep(2400); // Gemäss Aufgabe 24'000 ms; aber hier wird bewusst ein Zehntel verwendet um die Tests zu beschleunigen
            synchronized (lager) {
                lager.lagerAuffuellen();
                lager.notifyAll(); // Notify waiting threads
            }
        }
    } catch (InterruptedException e) {
        System.out.println("Lieferant: Thread unterbrochen!");
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