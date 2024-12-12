import java.util.LinkedList;

/**
 * Klasse Roboter - Simuliert die Bearbeitung von Produkten durch Roboter.
 * 
 * @author Silvan
 * @version 1.2
 */
public class Roboter extends Thread {
    // Name des Roboters
    private final String name;

    // Warteschlange für zu bearbeitende Produkte
    public final LinkedList<Produkt> warteschlange = new LinkedList<>();

    // Produktionszeit in Millisekunden
    private int produktionsZeit = 2000;

    // Kontrollvariable für die Schleife
    private volatile boolean isRunning = true;

    /**
     * Konstruktor
     * 
     * @param name Name des Roboters
     */
    public Roboter(String name) {
        this.name = name;
        synchronisiertesPrintln("Roboter " + name + " gestartet.");
    }

    /**
     * Hauptmethode des Threads: Bearbeitet Produkte in der Warteschlange.
     */
    @Override
    public void run() {
        while (isRunning) {
            try {
                Produkt produkt;
                synchronized (warteschlange) {
                    produkt = warteschlange.poll();
                }

                if (produkt != null) {
                    synchronisiertesPrintln("Roboter " + name + ": nimmt " + produkt + " aus der Warteschlange.");
                    produziereProdukt(produkt);
                    produkt.naechsteProduktionsStation();
                }

                lasseThreadSchlafen(1000); // Kurze Pause zwischen den Iterationen
            } catch (Exception e) {
                synchronisiertesPrintln("Roboter " + name + ": Fehler aufgetreten - " + e.getMessage());
            }
        }
        synchronisiertesPrintln("Roboter " + name + " beendet.");
    }

    /**
     * Beendet den Thread.
     */
    public void beenden() {
        isRunning = false;
        this.interrupt();
    }

    /**
     * Simuliert die Produktionszeit durch Thread-Schlaf.
     * 
     * @param produkt Das Produkt, das bearbeitet wird
     */
    public void produziereProdukt(Produkt produkt) {
        int zeit = produkt.getProduktionszeit(); // Produktionszeit vom Produkt abrufen
        lasseThreadSchlafen(zeit); // Zeit simulieren
        synchronisiertesPrintln("Roboter " + name + ": produziert " + produkt + ".");
        synchronisiertesPrintln("Roboter " + name + ": hat " + produkt + " fertig produziert.");
    }

    /**
     * Fügt ein neues Produkt zur Warteschlange hinzu.
     * 
     * @param produkt Das Produkt, das hinzugefügt werden soll
     */
    public void fuegeProduktHinzu(Produkt produkt) {
        synchronized (warteschlange) {
            warteschlange.add(produkt);
        }
        synchronisiertesPrintln("Roboter " + name + ": Produkt " + produkt + " zur Warteschlange hinzugefügt.");
    }

    /**
     * Setzt die Produktionszeit.
     * 
     * @param zeit Neue Produktionszeit in Millisekunden
     */
    public void setzteProduktionsZeit(int zeit) {
        this.produktionsZeit = zeit;
    }

    /**
     * Gibt den Namen des Roboters zurück.
     * 
     * @return Name des Roboters
     */
    public String gibName() {
        return name;
    }

    /**
     * Lässt den Thread für eine bestimmte Zeit schlafen.
     * 
     * @param zeit Zeit in Millisekunden, die der Thread schlafen soll
     */
    private void lasseThreadSchlafen(int zeit) {
        try {
            Thread.sleep(zeit);
        } catch (InterruptedException e) {
            synchronisiertesPrintln("Roboter " + name + ": Schlaf wurde unterbrochen!");
            Thread.currentThread().interrupt(); // Interrupt-Status wiederherstellen
        }
    }

    /**
     * Synchronized Print-Methode zur Vermeidung von Konsolen-Kollisionen.
     * 
     * @param output Text, der ausgegeben werden soll
     */
    private void synchronisiertesPrintln(String output) {
        synchronized (System.out) {
            System.out.println(output);
        }
    }
}