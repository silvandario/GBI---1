import java.util.LinkedList;

/**
 * Klasse Roboter - Simuliert die Bearbeitung von Produkten durch Roboter.
 * 
 * @author Silvan
 * @version 1.1
 */
public class Roboter extends Thread {
    // Name des Roboters
    private String name;

    // Warteschlange für zu bearbeitende Produkte
    public LinkedList<Produkt> warteschlange = new LinkedList<>();

    // Produktionszeit in Millisekunden
    private int produktionsZeit = 2000;

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
        while (true) { // Endlosschleife, um kontinuierlich auf neue Produkte zu prüfen
            if (warteschlange.peek() != null) { // Warteschlange prüfen
                Produkt produkt = warteschlange.poll(); // Produkt aus der Warteschlange holen
                synchronisiertesPrintln("Roboter " + name + ": nimmt " + produkt + " aus der Warteschlange.");
                produziereProdukt(produkt);
                produkt.naechsteProduktionsStation();
            }
            lasseThreadSchlafen(1000); // Kurze Pause zwischen den Iterationen
        }
    }

    /**
     * Simuliert die Produktionszeit durch Thread-Schlaf.
     * 
     * @param produkt Das Produkt, das bearbeitet wird
     */
    public void produziereProdukt(Produkt produkt) {
        int zeit = produkt.getProduktionszeit();// Produktionszeit vom Produkt abrufen
        lasseThreadSchlafen(zeit * 1); //mal 1000 für realen Case aber wirwolen nicht lange warten
        synchronisiertesPrintln("Roboter " + name + ": produziert " + produkt + ".");
        synchronisiertesPrintln("Roboter " + name + ": hat " + produkt + " fertig produziert.");
    }

    /**
     * Fügt ein neues Produkt zur Warteschlange hinzu.
     * 
     * @param produkt Das Produkt, das hinzugefügt werden soll
     */
    public void fuegeProduktHinzu(Produkt produkt) {
        synchronisiertesPrintln("Roboter " + name + ": Produkt " + produkt + " zur Warteschlange hinzugefügt.");
        warteschlange.add(produkt);
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