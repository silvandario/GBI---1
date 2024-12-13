import java.util.*;

/**
 * Die Klasse Produktions_Manager steuert die Produktionsprozesse in der Fabrik.
 * Sie ist als Thread implementiert, um kontinuierlich eingehende Bestellungen
 * zu bearbeiten und die Produkte den entsprechenden Robotern zuzuweisen.
 *
 * @version 13.12.2024
 */
public class Produktions_Manager extends Thread {
    private Holzbearbeitungs_Roboter holzRoboter;
    private Montage_Roboter montageRoboter;
    private Verpackungs_Roboter verpackungsRoboter;
    private Lackier_Roboter lackierRoboter;
    private Fabrik meineFabrik;
    private Lager meinLager;
    private LinkedList<Bestellung> zuVerarbeitendeBestellungen = new LinkedList<>();
    private LinkedList<Bestellung> bestellungenInProduktion = new LinkedList<>();
    private volatile boolean isRunning = true; // Kontrollvariable für den Thread

    public Produktions_Manager(Fabrik meineFabrik, Lager meinLager) {
        this.meineFabrik = meineFabrik;
        this.meinLager = meinLager;

        // Roboter-Threads initialisieren und starten
        this.holzRoboter = starteRoboter(new Holzbearbeitungs_Roboter("Holzroboter"));
        this.montageRoboter = starteRoboter(new Montage_Roboter("Montageroboter"));
        this.lackierRoboter = starteRoboter(new Lackier_Roboter("Lackierroboter"));
        this.verpackungsRoboter = starteRoboter(new Verpackungs_Roboter("Verpackungsroboter"));
    }
    public LinkedList<Bestellung> getBestellungenInProduktion() {
        return bestellungenInProduktion;
    }

    @Override
    public void run() {
        synchronisiertesPrintln("Produktionsmanager gestartet");
        while (isRunning) {
            try {
                bearbeiteBestellungen();
                überprüfeProduktion();
                lasseThreadSchlafen(200);
            } catch (Exception e) {
                synchronisiertesPrintln("Fehler im Produktionsmanager: " + e.getMessage());
            }
        }
        synchronisiertesPrintln("Produktionsmanager beendet.");
    }

    public void beenden() {
        isRunning = false; // Stoppt die Schleife in `run()`
        interrupt(); // Unterbricht den Thread
        synchronisiertesPrintln("Produktionsmanager gestoppt.");

        // Roboter stoppen
        holzRoboter.beenden();
        montageRoboter.beenden();
        lackierRoboter.beenden();
        verpackungsRoboter.beenden();
    }

    public void bearbeiteBestellungen() {
        synchronized (zuVerarbeitendeBestellungen) {
            if (!zuVerarbeitendeBestellungen.isEmpty()) {
                Bestellung naechsteBestellung = zuVerarbeitendeBestellungen.poll();
                if (meinLager.liefereMaterial(naechsteBestellung)) {
                    bestellungenInProduktion.add(naechsteBestellung);
                    starteProduktion(naechsteBestellung);
                } else {
                    synchronisiertesPrintln("Material nicht verfügbar für Bestellung: " + naechsteBestellung.gibBestellungsNr());
                }
            }
        }
    }

    public void überprüfeProduktion() {
        synchronized (bestellungenInProduktion) {
            List<Bestellung> kopie = new ArrayList<>(bestellungenInProduktion);
            for (Bestellung bestellung : kopie) {
                if (istProduktionAbgeschlossen(bestellung)) {
                    bestellungenInProduktion.remove(bestellung);
                    bestellung.setzeAlleProdukteProduziert();
                    synchronisiertesPrintln("Produktion abgeschlossen: Bestellung " + bestellung.gibBestellungsNr());
                }
            }
        }
    }

    private boolean istProduktionAbgeschlossen(Bestellung bestellung) {
        for (Produkt produkt : bestellung.gibBestellteProdukte()) {
            if (produkt.aktuellerZustand() != 3) { // Zustand 3 = vollständig produziert
                return false;
            }
        }
        return true;
    }

    private void starteProduktion(Bestellung bestellung) {
        if (bestellung == null || bestellung.gibBestellteProdukte().isEmpty()) {
            synchronisiertesPrintln("Keine Produkte für Bestellung: " + (bestellung != null ? bestellung.gibBestellungsNr() : "Unbekannt"));
            return;
        }
        synchronisiertesPrintln("Produktion gestartet: Bestellung " + bestellung.gibBestellungsNr());
        for (Produkt produkt : bestellung.gibBestellteProdukte()) {
            alloziereRoboter(produkt);
            produkt.naechsteProduktionsStation();
        }
    }

    public void fuegeZuVerarbeitendeBestellungenHinzu(Bestellung bestellung) {
        synchronized (zuVerarbeitendeBestellungen) {
            zuVerarbeitendeBestellungen.add(bestellung);
            synchronisiertesPrintln("Neue Bestellung hinzugefügt: " + bestellung.gibBestellungsNr());
        }
    }

    private void alloziereRoboter(Produkt produkt) {
        LinkedList<Roboter> bearbeitungsReihenfolge = new LinkedList<>();
        if (produkt instanceof Standardtuer) {
            bearbeitungsReihenfolge.add(holzRoboter);
            bearbeitungsReihenfolge.add(montageRoboter);
            bearbeitungsReihenfolge.add(lackierRoboter);
            bearbeitungsReihenfolge.add(verpackungsRoboter);
        } else if (produkt instanceof Premiumtuer) {
            bearbeitungsReihenfolge.add(holzRoboter);
            bearbeitungsReihenfolge.add(lackierRoboter);
            bearbeitungsReihenfolge.add(montageRoboter);
            bearbeitungsReihenfolge.add(verpackungsRoboter);
        }
        produkt.setzteProduktionsAblauf(bearbeitungsReihenfolge);
    }

    private <T extends Roboter> T starteRoboter(T roboter) {
        roboter.start();
        return roboter;
    }

    private void lasseThreadSchlafen(int zeit) {
        try {
            Thread.sleep(zeit);
        } catch (InterruptedException lasseSchlafenE) {
            Thread.currentThread().interrupt();
            synchronisiertesPrintln("Thread wurde unterbrochen: " + lasseSchlafenE.getMessage());
        }
    }

    public void synchronisiertesPrintln(String output) {
        synchronized (System.out) {
            System.out.println(output);
        }
    }
}