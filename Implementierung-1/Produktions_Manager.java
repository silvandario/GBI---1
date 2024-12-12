import java.util.*;

/**
 * Die Klasse Produktions_Manager steuert die Produktionsprozesse in der Fabrik.
 * Sie ist als Thread implementiert, um kontinuierlich eingehende Bestellungen
 * zu bearbeiten und die Produkte den entsprechenden Robotern zuzuweisen.
 *
 * @version 10.12.2024
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

    public Produktions_Manager(Fabrik meineFabrik, Lager meinLager) {
        this.meineFabrik = meineFabrik;
        this.meinLager = meinLager;
        this.holzRoboter = new Holzbearbeitungs_Roboter("Holzroboter");
        this.montageRoboter = new Montage_Roboter("Montageroboter");
        this.lackierRoboter = new Lackier_Roboter("Lackierroboter");
        this.verpackungsRoboter = new Verpackungs_Roboter("Verpackungsroboter");

        this.holzRoboter.start();
        this.montageRoboter.start();
        this.lackierRoboter.start();
        this.verpackungsRoboter.start();
    }

    public void run() {
        synchronisiertesPrintln("Produktionsmanager gestartet");
        while (true) {
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

            lasseThreadSchlafen(200);
        }
    }

    private boolean istProduktionAbgeschlossen(Bestellung bestellung) {
        for (Produkt produkt : bestellung.gibBestellteProdukte()) {
            if (produkt.aktuellerZustand() != 3) {
                return false;
            }
        }
        return true;
    }

    private void starteProduktion(Bestellung bestellung) {
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

    private void lasseThreadSchlafen(int zeit) {
        try {
            Thread.sleep(zeit);
        } catch (InterruptedException lasseSchlafenE) {
            Thread.currentThread().interrupt();
            synchronisiertesPrintln("Thread wurde unterbrochen: " + lasseSchlafenE.getMessage());
        }
    }

    private void synchronisiertesPrintln(String output) {
        synchronized (System.out) {
            System.out.println(output);
        }
    }
}