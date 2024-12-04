import java.util.LinkedList;

/**
 * Beschreiben Sie hier die Klasse Roboter.
 * 
 * @author Silvan
 * @version 1
 * 
 * In Übungslektion bei Alex: Montage_roboter, etc. -> nur eine Variante ist notwendig, einfach zum zeigen, dass es funktioniert
 */

public class Roboter
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    //Name
    private String name;
    //Warteschlange
    private LinkedList<Produkt> warteschlange = new LinkedList<Produkt>();
    //Zeit
    int produktionsZeit;

    /**
     * Konstruktor
     * 
     * @param name des Roboters
     */
    public Roboter(String name){
        this.name = name;
    }
    
    /**
     * Beschreibung
     */
    public void run(){
        while(true){ // unendliche Schleife , um immer wieder zu schauen, ob neue Produkte, welche produziert werden müssen, eingetroffen sind
            if(warteschlange.peek() != null) { //Condition, um keine Leere zu bekommen
                Produkt produkt = warteschlange.poll(); // nächstes Element wird weggenommen (ist verfügbar aber geht weg, praktisch für uns)
                // synchronisiertesPrintln("Roboter " + this.name +": nimmt " + produkt + " aus der Warteschlange"); noch nciht IMplementiert             
                produziereProdukt(produkt);
                produkt.naechsteProduktionsStation();
            }
            lasseThreadSchlafen(1000);
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
   
   /**
     * Die Produktion eines Produkts wird simuliert indem man den Thread schlafen legt
     */
    public void produziereProdukt(Produkt produkt){
        //synchronisiertesPrintln("Roboter " + this.name +": produziert " + produkt);
        lasseThreadSchlafen(produktionsZeit);
        //synchronisiertesPrintln("Roboter " + this.name +": hat " + produkt + " fertig produziert");
    }
    
   /**
     * Gibt den Namen des Roboters
     * 
     * @return Name vom Roboter
     */
    
    public String gibName(){
        return name;
    }
    
   /**
     * Roboter erhält neues PRodukt 
     * 
     */
    
    public void fuegeProduktHinzu(Produkt produkt){
        warteschlange.add(produkt); 
    }
}
