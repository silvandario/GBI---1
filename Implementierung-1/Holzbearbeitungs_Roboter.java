/**
 * Implementierung der Klasse Holzbearbeitungs_Roboter.
 * Diese Klasse erweitert Roboter und simuliert die Holzbearbeitung von Produkten.
 */

public class Holzbearbeitungs_Roboter extends Roboter {

    /**
     * Konstruktor für Holzbearbeitungs_Roboter.
     *
     * @param name Der Name des Roboters.
     */
    public Holzbearbeitungs_Roboter(String name) {
        super(name);
    }

    /**
     * Simuliert den Produktionsprozess für ein Produkt.
     *
     * @param produkt Das zu bearbeitende Produkt.
     */
    public void produziereProdukt(Produkt produkt) {
        try {
            // Bestimmen der Bearbeitungszeit basierend auf dem Produkttyp
            int verarbeitungsZeit;
            if (produkt instanceof Standardtuer) {
                verarbeitungsZeit = 600000; // 10 Minuten in Millisekunden
            } else if (produkt instanceof Premiumtuer) {
                verarbeitungsZeit = 1800000; // 30 Minuten in Millisekunden
            } else {
                System.out.println("Unbekannter Produkttyp: " + produkt.getClass().getSimpleName());
                return;
            }

            // Verarbeitung simulieren
            System.out.println(getName() + " beginnt mit der Bearbeitung von: " + produkt.getName());
            Thread.sleep(verarbeitungsZeit);
            System.out.println(getName() + " hat die Bearbeitung abgeschlossen: " + produkt.getName());

            // Produkt als bereit für den nächsten Schritt markieren
            produkt.markiereBereitFuerNaechsteStation();
        } catch (InterruptedException e) {
            System.out.println("Die Bearbeitung wurde unterbrochen: " + e.getMessage());
        }
    }

    /**
     * Überprüft und verarbeitet kontinuierlich die Warteschlange der Produkte.
     */
    @Override
    public void run() {
        while (true) {
            // Überprüfen, ob Produkte in der Warteschlange vorhanden sind
            Produkt produkt = entnehmeProduktAusWarteschlange();
            if (produkt != null) {
                produziereProdukt(produkt);
            } else {
                try {
                    // Kurz schlafen, bevor die Warteschlange erneut überprüft wird
                    Thread.sleep(1000); // 1 Sekunde
                } catch (InterruptedException e) {
                    System.out.println("Holzbearbeitungs_Roboter wurde unterbrochen: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Entnimmt das nächste Produkt aus der Warteschlange.
     *
     * @return Das nächste zu verarbeitende Produkt oder null, wenn die Warteschlange leer ist.
     */
    private Produkt entnehmeProduktAusWarteschlange() {
        synchronized (this) {
            return warteschlange.isEmpty() ? null : warteschlange.poll();
        }
    }
}
