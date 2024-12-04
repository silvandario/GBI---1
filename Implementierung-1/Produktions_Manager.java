import java.util.*;
/**
 * Beschreiben Sie hier die Klasse Produktions_Manager.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Produktions_Manager
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
     * Die run Methode des Threads nimmt die Bestellungen aus der Liste der zu 
     * verarbeitenden Bestellungen. Wenn genügend Material im Lager ist, wird die 
     * Produktion der Bestellten Produkte gestartet und die Bestellung wird
     * der Liste betsellungenInProduktion hinzugefügt.
     * 
     * Bei den Bestellungen in Produktion wird dann geschaut, ob alle Produkte
     * bereits produziert wurden. Wenn ja, wird die Bestellung von der Liste, der 
     * zu produzierenden Bestellungen gelöscht und in der Klasse Bestellung wird
     * festgehalten, dass alle Produkte produziert und zur Auslieferung bereit sind. 
     */
    
    public void run(){
        // synchronisiertesPrintln("Produktionsmanager gestartet");
        while(true){ //unendliche Schlaufe
            Bestellung naechsteBestellung = zuVerarbeitendeBestellungen.peek();
            if(naechsteBestellung != null && meinLager.liefereMaterial(naechsteBestellung)){
                naechsteBestellung = zuVerarbeitendeBestellungen.poll();
                bestellungenInProduktion.add(naechsteBestellung);
                starteProduktion(naechsteBestellung);
            }
            for(Bestellung bestellung : bestellungenInProduktion){
            boolean alleProdukteProduziert = true;
            for(Produkt produkt : bestellung.liefereBestellteProdukte()){
                 if(produkt.getAktuellerZustand()!=3){
                     alleProdukteProduziert = false;
                     break;
                }
             }
             if(alleProdukteProduziert){
                 bestellungenInProduktion.remove(bestellung);
                 bestellung.setzeAlleProdukteProduziert();
             }             
         }            
         lasseThreadSchlafen(100);  
        }
    }
    
    /**
     * Lässt Thread schlafen, um die Bearbeitungszeit zu simulieren* 
     * @param  zeit  Dauer, wie lange Thread schläft: Der Thread wird für 10 Minuten (600.000 Millisekunden) bei Standardtüren oder 
     *  30 Minuten (1.800.000 Millisekunden) bei Premiumtüren schlafen gelegt
     */
    private void lasseThreadSchlafen(int zeit) {
      try {
         Thread.sleep((long)zeit);
      } catch (InterruptedException lasseSchlafenE) {
         lasseSchlafenE.printStackTrace();
      }
    
    }
   
    private void starteProduktion(Bestellung bestellung){
        // synchronisiertesPrintln("Produktionsmanager:  Produktion für Bestellung " + bestellung.gibBestellungsNr() " gestartet"); TODO Implementieren
        for(Produkt produkt : bestellung.liefereBestellteProdukte()){
            alloziereRoboter(produkt);
            produkt.naechsteProduktionsStation();
        }         
    }
    
    
     /**
     * An Liste zuVerarbeitendeBestellungen wird neue BEtsellung hinzugefügt
     * 
     * @param bestellung welche durch Schalf des Threads simuliert wird
     */   
    
    public void fuegeZuVerarbeitendeBestellungenHinzu(Bestellung bestellung){
        zuVerarbeitendeBestellungen.add(bestellung);
    }
    /**
     * Methode legt korrekte REiehenfolge fest
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
}
