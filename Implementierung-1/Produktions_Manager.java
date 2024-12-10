import java.util.*;
/**
 * Die Klasse Produktions_Manager steuert die Produktionsprozesse in der Fabrik.
 * Sie ist als Thread implementiert, um kontinuierlich eingehende Bestellungen
 * zu bearbeiten und die Produkte den entsprechenden Robotern zuzuweisen.
 *
 * Die Klasse verwaltet:
 * - Eine Warteschlange für neue Bestellungen
 * - Eine Liste für Bestellungen, die sich in Produktion befinden
 *
 * Hauptaufgaben:
 * - Bestellungen annehmen und Materialien aus dem Lager anfordern
 * - Den Produktionsprozess starten und überwachen
 * - Den Produktionsstatus von Bestellungen prüfen und abschließen
 * 
 * Die Klasse ist für die Koordination zwischen Lager, Robotern und Bestellungen verantwortlich.
 * 
 * @author (Gruppe 1) 
 * @version (10.12.2024)
 */
public class Produktions_Manager extends Thread
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private Holzbearbeitungs_Roboter holzRoboter;
    private Montage_Roboter montageRoboter;
    private Verpackungs_Roboter verpackungsRoboter;
    private Lackier_Roboter lackierRoboter;
    private Fabrik meineFabrik;
    private Lager meinLager;
    private LinkedList<Bestellung> zuVerarbeitendeBestellungen = new LinkedList();
    private LinkedList<Bestellung> bestellungenInProduktion = new LinkedList();

    /**
     * Konstruktor für Objekte der Klasse Produktions_Manager
     */
    public Produktions_Manager(Fabrik meineFabrik, Lager meinLager) {
      this.meineFabrik = meineFabrik;
      this.meinLager = meinLager;
      this.holzRoboter = new Holzbearbeitungs_Roboter("Holzroboter");
      this.montageRoboter = new Montage_Roboter("Montageroboter");
      this.lackierRoboter = new Lackier_Roboter("Lackierroboter");
      this.verpackungsRoboter = new Verpackungs_Roboter("Verpackungsroboter");
      // anstatt run wird hier ween konvention und so start verwendet, siehe Thread MEthode unten
      this.holzRoboter.start();
      this.montageRoboter.start();
      this.lackierRoboter.start();
      this.verpackungsRoboter.start();
    }
    
    /**
     * Die run-Methode verarbeitet Bestellungen und überwacht den Produktionsstatus.
     */
    public void run(){
        // synchronisiertesPrintln("Produktionsmanager gestartet");
        while (true) {
            synchronized (zuVerarbeitendeBestellungen) {
                if (!zuVerarbeitendeBestellungen.isEmpty()) {
                    Bestellung naechsteBestellung = zuVerarbeitendeBestellungen.poll();
                    if (meinLager.liefereMaterial(naechsteBestellung)) {
                        bestellungenInProduktion.add(naechsteBestellung);
                        starteProduktion(naechsteBestellung);
                    } else {
                        System.out.println("Material nicht verfügbar für Bestellung: " + naechsteBestellung.gibBestellungsNr());
                    }
                }
            }

            synchronized (bestellungenInProduktion) {
                Iterator<Bestellung> iterator = bestellungenInProduktion.iterator();
                while (iterator.hasNext()) {
                    Bestellung bestellung = iterator.next();
                    if (istProduktionAbgeschlossen(bestellung)) {
                        iterator.remove();
                        bestellung.setzeAlleProdukteProduziert();
                        System.out.println("Produktion abgeschlossen: Bestellung " + bestellung.gibBestellungsNr());
                    }
                }
            }

            lasseThreadSchlafen(200);
        }
    }
    
    /**
     * Überprüft, ob alle Produkte einer Bestellung produziert wurden.
     */
    private boolean istProduktionAbgeschlossen(Bestellung bestellung) {
        for (Produkt produkt : bestellung.liefereBestellteProdukte()) {
            if (produkt.getAktuellerZustand() != 3) { // Zustand 3 = fertig produziert
                return false;
            }
        }
        return true;
    }
   
    /**
     * Startet die Produktion für eine Bestellung.
     */
    private void starteProduktion(Bestellung bestellung) {
        System.out.println("Produktion gestartet: Bestellung " + bestellung.gibBestellungsNr());
        for (Produkt produkt : bestellung.liefereBestellteProdukte()) {
            alloziereRoboter(produkt);
            produkt.naechsteProduktionsStation();
        }
    }
    
    /**
     * Fügt eine neue Bestellung zur Warteschlange hinzu.
     */   
    public void fuegeZuVerarbeitendeBestellungenHinzu(Bestellung bestellung) {
        synchronized (zuVerarbeitendeBestellungen) {
            zuVerarbeitendeBestellungen.add(bestellung);
            System.out.println("Neue Bestellung hinzugefügt: " + bestellung.gibBestellungsNr());
        }
    }
    
    /**
     * Methode legt korrekte Reihenfolge der Roboter fest
     */
    private void alloziereRoboter(Produkt produkt){
        LinkedList<Roboter> bearbeitungsReihenfolge = new LinkedList<Roboter>();
        if(produkt instanceof Standardtuer){
            bearbeitungsReihenfolge.add(holzRoboter);
            bearbeitungsReihenfolge.add(montageRoboter);
            bearbeitungsReihenfolge.add(lackierRoboter);
            bearbeitungsReihenfolge.add(verpackungsRoboter);
            produkt.setzteProduktionsAblauf(bearbeitungsReihenfolge);
        }
        else if(produkt instanceof Premiumtuer){
            bearbeitungsReihenfolge.add(holzRoboter);
            bearbeitungsReihenfolge.add(lackierRoboter);
            bearbeitungsReihenfolge.add(montageRoboter);            
            bearbeitungsReihenfolge.add(verpackungsRoboter);
            produkt.setzteProduktionsAblauf(bearbeitungsReihenfolge);

        }        
    }
    
    /**
     * Lässt den Thread für eine bestimmte Zeit ruhen.
     */
    private void lasseThreadSchlafen(int zeit) {
        try {
            Thread.sleep(zeit);
        } catch (InterruptedException lasseSchlafenE) {
            Thread.currentThread().interrupt(); // Thread wieder auf interrupted setzen
            System.out.println("Thread wurde unterbrochen: " + lasseSchlafenE.getMessage());
        }
    }
}
