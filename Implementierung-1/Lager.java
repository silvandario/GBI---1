
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
        vorhandeneHolzeinheiten = maxHolzeinheiten;
        vorhandeneSchrauben = maxSchrauben;
        vorhandeneFarbeinheiten = maxFarbeinheiten;
        vorhandeneKartoneinheiten = maxKartoneinheiten;
        vorhandeneGlaseinheiten = maxGlaseinheiten;
        lieferant = new Lieferant();
    }
    
    /**
     * Methode gibBeschaffungsZeit liefert die Beschaffungszeit in Tagen:  adfsfsadsdd
     * 0 Tage, wenn alle Materialien vorhanden sind und  asDSADSDSADSAD
     * 2 Tage, wenn diese nachbestellt werden müssen sadsadsadsadxsaxadsd
     *
     * @param Bestellung  eine Kundenbestellung mit allen bestellten Produkten im Array of Produkt aDXSAXSADSAd
     * @return Beschaffungszeit in Tagen DSADadsxa
     */   
    
    public int gibBeschaffungsZeit(Bestellung kundenBestellung){
        int benoetigtesHolz = 0;
        int benoetigteSchrauben = 0;
        int benoetigteFarbe = 0;
        int benoetigterKarton = 0;
        int benoetigtesGlas = 0;
        
        for(Produkt produkt : kundenBestellung.gibBestellteProdukte()){
            if(produkt instanceof Standardtuer){
                benoetigtesHolz = benoetigtesHolz + Standardtuer.gibBenoetigteHolzeinheiten();
                benoetigteSchrauben = benoetigteSchrauben + Standardtuer.gibBenoetigteSchrauben();
                benoetigteFarbe = benoetigteFarbe + Standardtuer.gibBenoetigteFarbeinheiten();
                benoetigterKarton = benoetigterKarton + Standardtuer.gibBenoetigteKartoneinheiten();
     
            }else if(produkt instanceof Premiumtuer){
                benoetigtesHolz = benoetigtesHolz + Premiumtuer.gibBenoetigteHolzeinheiten();
                benoetigteSchrauben = benoetigteSchrauben + Premiumtuer.gibBenoetigteSchrauben();
                benoetigteFarbe = benoetigteFarbe + Premiumtuer.gibBenoetigteFarbeinheiten();
                benoetigterKarton = benoetigterKarton + Premiumtuer.gibBenoetigteKartoneinheiten();
                benoetigtesGlas = Premiumtuer.gibBenoetigteAnzahlKissen();
            }
        }        
        if(vorhandeneHolzeinheiten >= benoetigtesHolz && vorhandeneSchrauben >= benoetigteSchrauben &&  
            vorhandeneGlas >= benoetigtesGlas && vorhandeneFarbeinheiten >= benoetigteFarbe && 
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
        int mussBestellenHolzeinheiten=maxHolzeinheiten-vorhandeneHolzeinheiten;
        int mussBestellenSchrauben=maxSchrauben-vorhandeneSchrauben;
        int mussBestellenFarbeinheiten=maxFarbeinheiten-vorhandeneFarbeinheiten;
        int mussBestellenKartoneinheiten=maxKartoneinheiten-vorhandeneKartoneinheiten;
        int mussBestellenGlaseinheiten=maxGlaseinheiten-vorhandeneGlaseinheiten;   
        
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
