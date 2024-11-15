
/**
 * 
 * @author Silvan Dario Ladner
 * @version 1
 * Klasse Lager verwaltet ie für die Herstellung von Türen benötigte Materialie.
   */
import java.util.*;

public class Lager {
    

    private static final int maxHolzeinheiten = 1000;
    private static final int maxSchrauben = 5000;
    private static final int maxFarbeinheiten = 500;
    private static final int maxKartoneinheiten = 300;
    private static final int maxGlaseinheiten = 200;
    
    private int vorhandeneHolzeinheiten;
    private int vorhandeneSchrauben;
    private int vorhandeneFarbeinheiten;
    private int vorhandeneKartoneinheiten;
    private int vorhandeneGlaseinheiten;   

    private Lieferant lieferant;

    /**
     * Konstruktor der Klasse Lager
     */
    public Lager()
    {   
        // das Lager ist "voll"
        vorhandeneHolzeinheiten = maxHolzeinheiten;
        vorhandeneSchrauben = maxSchrauben;
        vorhandeneFarbeinheiten = maxFarbeinheiten;
        vorhandeneKartoneinheiten = maxKartoneinheiten;
        vorhandeneGlaseinheiten = maxGlaseinheiten;
        lieferant = new Lieferant();
    }
    
    /**
     * Die Methode gibBeschaffungsZeit liefert die Beschaffungszeit in Einheiten von Tagen
     * Gemaess Aufgabenstellung sind es 
     * 0 Tage, wenn alle Materialien vorhanden sind beziehungsweise
     * 2 Tage, wenn nicht alle Materialen vorhanden sind und es nachbestellt werden muss
     *
     * @param Bestellung BEstelliung vom Kunden mit allen bestellten Produkten als 
     * @return Beschaffungszeit in Einheiten von Tagen
     */   
    
    public int gibBeschaffungsZeit(Bestellung kundenBestellung){
        // Variablen werden nacher mit benötigter MEnge aufaddiert        
        int benoetigtesHolz = 0;
        int benoetigteSchrauben = 0;
        int benoetigteFarbe = 0;
        int benoetigterKarton = 0;
        int benoetigtesGlas = 0;
        
        for(Produkt produkt : kundenBestellung.gibBestellteProdukte()){
            //alle Prudkte werden nun im EInzelnen untersucht
            if(produkt instanceof Standardtuer){ 
                //falls Standardtuere, dann braucht es Material der Standardtuerere
                benoetigtesHolz = benoetigtesHolz + Standardtuer.getHolzeinheiten();
                benoetigteSchrauben = benoetigteSchrauben + Standardtuer.getSchrauben();
                benoetigteFarbe = benoetigteFarbe + Standardtuer.getFarbeinheiten();
                benoetigterKarton = benoetigterKarton + Standardtuer.getKartoneinheiten();
     
            }else if(produkt instanceof Premiumtuer){
                //falls Premiumtuer, dann braucht es Material der Premiumtuer
                benoetigtesHolz = benoetigtesHolz + Premiumtuer.getHolzeinheiten();
                benoetigteSchrauben = benoetigteSchrauben + Premiumtuer.getSchrauben();
                benoetigteFarbe = benoetigteFarbe + Premiumtuer.getFarbeinheiten();
                benoetigterKarton = benoetigterKarton + Premiumtuer.getKartoneinheiten();
                benoetigtesGlas = Premiumtuer.getGlaseinheiten();
            }
        }        
        // Check, ob wir die Materialien auf Lager haben: Falls ja, BEschaffungszeot=0 (gemaess Aufgabenstellung), sonst 2 Tage
        if(vorhandeneHolzeinheiten >= benoetigtesHolz && vorhandeneSchrauben >= benoetigteSchrauben &&  
            vorhandeneGlaseinheiten >= benoetigtesGlas && vorhandeneFarbeinheiten >= benoetigteFarbe && 
            vorhandeneKartoneinheiten >= benoetigterKarton){
                return 0;             
            }else{ 
                return 2;   
        }
    }

   
    /**
     * Methode lagerAuffuellen bestellt alle notwendigen Materialien nach, so dass das Lager wieder voll ist.
     */
    public void lagerAuffuellen(){
        // Logik: Es muss so viel fehlende Einheiten bestellt werden, wie die Differenz zischen maximaler Lagerkapizität und aktuell vorhandenem Bestand ist
        int mussBestellenHolzeinheiten=maxHolzeinheiten-vorhandeneHolzeinheiten;
        int mussBestellenSchrauben=maxSchrauben-vorhandeneSchrauben;
        int mussBestellenFarbeinheiten=maxFarbeinheiten-vorhandeneFarbeinheiten;
        int mussBestellenKartoneinheiten=maxKartoneinheiten-vorhandeneKartoneinheiten;
        int mussBestellenGlaseinheiten=maxGlaseinheiten-vorhandeneGlaseinheiten;
        if (mussBestellenHolzeinheiten + mussBestellenSchrauben + mussBestellenFarbeinheiten + mussBestellenKartoneinheiten + mussBestellenGlaseinheiten == 0) {
            System.out.println("Bestellung nicht getätigt! Das Lager ist voll :)");
        } else {
            // Im Lager: Boolean true
        if(lieferant.wareBestellen(mussBestellenHolzeinheiten,mussBestellenSchrauben,mussBestellenFarbeinheiten,mussBestellenKartoneinheiten,mussBestellenGlaseinheiten)){
            vorhandeneHolzeinheiten=maxHolzeinheiten;
            vorhandeneSchrauben=maxSchrauben;
            vorhandeneFarbeinheiten=maxFarbeinheiten;
            vorhandeneKartoneinheiten=maxKartoneinheiten;
            vorhandeneGlaseinheiten=maxGlaseinheiten;
            System.out.println("---Lager wieder bei 100% aufgefüllt");
        }else{
            System.out.println("Bestellung hat nicht funktioniert! Schade :(");
        }
        }
        
    }
    
    /**
     * Methode lagerBestandAusgeben gibt den aktuellen Lagerbestand aus, aufgeordnet in alle Materialien 
    */    
    
    public void lagerBestandAusgeben(){
            System.out.println("-----------Lagerbestand----------- ");
            System.out.println(vorhandeneHolzeinheiten + " Holzeinheiten vorhanden");
            System.out.println(vorhandeneSchrauben +" Schrauben vorhanden");
            System.out.println(vorhandeneFarbeinheiten+ " Farbeinheiten vorhanden");
            System.out.println(vorhandeneKartoneinheiten + " Kartoneinheiten vorhanden");
            System.out.println(vorhandeneGlaseinheiten + " Glaseinheiten vorhanden");            
            System.out.println("----------- ----------- -----------"); 
    
    }
    /**
     * Setter-Methoden, um den Lagerbestand anzupassen. So nimmt das Lager an Bestand ab, wenn Betsellung angenommen wird, da Materialien verarbeitet werden (IN der Praxis (und evtl. weiterer
     * Implementierung) wird dies verfeinert, da zwischen den Events Zeit vergaht und das Lager nicht direkt geleert wird bzw. in ein Zwischenlager kommt/ nach und nach geleert wird etc
     */
    public void setVorhandeneHolzeinheiten(int menge) {
        if (menge >= 0 && menge <= maxHolzeinheiten) {
            vorhandeneHolzeinheiten = menge;
        } else {
            throw new IllegalArgumentException("Ungültige Menge für Holzeinheiten.");
        }
    }

    public void setVorhandeneSchrauben(int menge) {
        if (menge >= 0 && menge <= maxSchrauben) {
            vorhandeneSchrauben = menge;
        } else {
            throw new IllegalArgumentException("Ungültige Menge für Schrauben.");
        }
    }

    public void setVorhandeneFarbeinheiten(int menge) {
        if (menge >= 0 && menge <= maxFarbeinheiten) {
            vorhandeneFarbeinheiten = menge;
        } else {
            throw new IllegalArgumentException("Ungültige Menge für Farbeinheiten.");
        }
    }

    public void setVorhandeneKartoneinheiten(int menge) {
        if (menge >= 0 && menge <= maxKartoneinheiten) {
            vorhandeneKartoneinheiten = menge;
        } else {
            throw new IllegalArgumentException("Ungültige Menge für Kartoneinheiten.");
        }
    }

    public void setVorhandeneGlaseinheiten(int menge) {
        if (menge >= 0 && menge <= maxGlaseinheiten) {
            vorhandeneGlaseinheiten = menge;
        } else {
            throw new IllegalArgumentException("Ungültige Menge für Glaseinheiten.");
        }
    }

    /**
     * entsprechende Methode zur Reduktion des Lagerbestands nach einer Bestellung, die bei bestellung aufgerufgen wird
     */
    public void lagerbestandReduzieren(Bestellung kundenBestellung) {
        for (Produkt produkt : kundenBestellung.gibBestellteProdukte()) {
            if (produkt instanceof Standardtuer) {
                vorhandeneHolzeinheiten -= Standardtuer.getHolzeinheiten();
                vorhandeneSchrauben -= Standardtuer.getSchrauben();
                vorhandeneFarbeinheiten -= Standardtuer.getFarbeinheiten();
                vorhandeneKartoneinheiten -= Standardtuer.getKartoneinheiten();
            } else if (produkt instanceof Premiumtuer) {
                vorhandeneHolzeinheiten -= Premiumtuer.getHolzeinheiten();
                vorhandeneSchrauben -= Premiumtuer.getSchrauben();
                vorhandeneFarbeinheiten -= Premiumtuer.getFarbeinheiten();
                vorhandeneKartoneinheiten -= Premiumtuer.getKartoneinheiten();
                vorhandeneGlaseinheiten -= Premiumtuer.getGlaseinheiten();
            }
        }
    }
    /**
         * Getter-Methoden, um den aktuellen Lagerbestand abzurufen.
         */
        public int getVorhandeneHolzeinheiten() {
            return vorhandeneHolzeinheiten;
        }

    public int getVorhandeneSchrauben() {
        return vorhandeneSchrauben;
    }

    public int getVorhandeneFarbeinheiten() {
        return vorhandeneFarbeinheiten;
    }

    public int getVorhandeneKartoneinheiten() {
        return vorhandeneKartoneinheiten;
    }

    public int getVorhandeneGlaseinheiten() {
        return vorhandeneGlaseinheiten;
    }
    
}
