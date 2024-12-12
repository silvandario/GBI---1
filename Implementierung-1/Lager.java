import java.util.*;

public class Lager {
    private static final int MAX_HOLZEINHEITEN = 1000;
    private static final int MAX_SCHRAUBEN = 5000;
    private static final int MAX_FARBEINHEITEN = 500;
    private static final int MAX_KARTONEINHEITEN = 300;
    private static final int MAX_GLASEINHEITEN = 200;

    private int vorhandeneHolzeinheiten;
    private int vorhandeneSchrauben;
    private int vorhandeneFarbeinheiten;
    private int vorhandeneKartoneinheiten;
    private int vorhandeneGlaseinheiten;

    private Lieferant lieferant;

    public Lager() {
        vorhandeneHolzeinheiten = MAX_HOLZEINHEITEN;
        vorhandeneSchrauben = MAX_SCHRAUBEN;
        vorhandeneFarbeinheiten = MAX_FARBEINHEITEN;
        vorhandeneKartoneinheiten = MAX_KARTONEINHEITEN;
        vorhandeneGlaseinheiten = MAX_GLASEINHEITEN;
        lieferant = null;
    }

    public int gibBeschaffungsZeit(Bestellung kundenBestellung) {
        int[] benoetigteRessourcen = berechneBenoetigteRessourcen(kundenBestellung);
        if (sindMaterialienVorhanden(benoetigteRessourcen)) {
            return 0;
        } else {
            return 2;
        }
    }

    public boolean liefereMaterial(Bestellung kundenBestellung) {
        int[] benoetigteRessourcen = berechneBenoetigteRessourcen(kundenBestellung);
        if (sindMaterialienVorhanden(benoetigteRessourcen)) {
            zieheMaterialAb(benoetigteRessourcen);
            return true;
        } else {
            lagerAuffuellen();
            return false;
        }
    }

    public void lagerAuffuellen() {
    if (vorhandeneHolzeinheiten == MAX_HOLZEINHEITEN &&
        vorhandeneSchrauben == MAX_SCHRAUBEN &&
        vorhandeneFarbeinheiten == MAX_FARBEINHEITEN &&
        vorhandeneKartoneinheiten == MAX_KARTONEINHEITEN &&
        vorhandeneGlaseinheiten == MAX_GLASEINHEITEN) {
        System.out.println("Bestellung nicht getätigt! Das Lager ist voll");
        return;
    }
    
    System.out.println("Lager wird aufgefüllt...");
    vorhandeneHolzeinheiten = MAX_HOLZEINHEITEN;
    vorhandeneSchrauben = MAX_SCHRAUBEN;
    vorhandeneFarbeinheiten = MAX_FARBEINHEITEN;
    vorhandeneKartoneinheiten = MAX_KARTONEINHEITEN;
    vorhandeneGlaseinheiten = MAX_GLASEINHEITEN;
    System.out.println("--- Lager erfolgreich aufgefüllt ---");
    }

    public void lagerBestandAusgeben() {
        System.out.println("-----------Lagerbestand-----------");
        System.out.println(vorhandeneHolzeinheiten + " Holzeinheiten vorhanden");
        System.out.println(vorhandeneSchrauben + " Schrauben vorhanden");
        System.out.println(vorhandeneFarbeinheiten + " Farbeinheiten vorhanden");
        System.out.println(vorhandeneKartoneinheiten + " Kartoneinheiten vorhanden");
        System.out.println(vorhandeneGlaseinheiten + " Glaseinheiten vorhanden");
        System.out.println("----------- ----------- -----------");
    }
 

    private int[] berechneBenoetigteRessourcen(Bestellung kundenBestellung) {
        int benoetigtesHolz = 0, benoetigteSchrauben = 0, benoetigteFarbe = 0, benoetigterKarton = 0, benoetigtesGlas = 0;
        for (Produkt produkt : kundenBestellung.gibBestellteProdukte()) {
            if (produkt instanceof Standardtuer) {
                benoetigtesHolz += Standardtuer.getHolzeinheiten();
                benoetigteSchrauben += Standardtuer.getSchrauben();
                benoetigteFarbe += Standardtuer.getFarbeinheiten();
                benoetigterKarton += Standardtuer.getKartoneinheiten();
            } else if (produkt instanceof Premiumtuer) {
                benoetigtesHolz += Premiumtuer.getHolzeinheiten();
                benoetigteSchrauben += Premiumtuer.getSchrauben();
                benoetigteFarbe += Premiumtuer.getFarbeinheiten();
                benoetigterKarton += Premiumtuer.getKartoneinheiten();
                benoetigtesGlas += Premiumtuer.getGlaseinheiten();
            }
        }
        return new int[] { benoetigtesHolz, benoetigteSchrauben, benoetigteFarbe, benoetigterKarton, benoetigtesGlas };
    }

    private boolean sindMaterialienVorhanden(int[] benoetigteRessourcen) {
        return vorhandeneHolzeinheiten >= benoetigteRessourcen[0] &&
               vorhandeneSchrauben >= benoetigteRessourcen[1] &&
               vorhandeneFarbeinheiten >= benoetigteRessourcen[2] &&
               vorhandeneKartoneinheiten >= benoetigteRessourcen[3] &&
               vorhandeneGlaseinheiten >= benoetigteRessourcen[4];
    }

    private void zieheMaterialAb(int[] benoetigteRessourcen) {
        vorhandeneHolzeinheiten -= benoetigteRessourcen[0];
        vorhandeneSchrauben -= benoetigteRessourcen[1];
        vorhandeneFarbeinheiten -= benoetigteRessourcen[2];
        vorhandeneKartoneinheiten -= benoetigteRessourcen[3];
        vorhandeneGlaseinheiten -= benoetigteRessourcen[4];
    }
    
    public void wareLiefern(){
        vorhandeneHolzeinheiten=MAX_HOLZEINHEITEN;
        vorhandeneSchrauben=MAX_SCHRAUBEN;
        vorhandeneFarbeinheiten=MAX_FARBEINHEITEN;
        vorhandeneKartoneinheiten=MAX_KARTONEINHEITEN;
        vorhandeneGlaseinheiten=MAX_GLASEINHEITEN;
        System.out.println("Lager: Ware wurde geliefert! ");   
        lagerBestandAusgeben();
    }

    // Getter-Methoden
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

    // Setter-Methoden
    public void setVorhandeneHolzeinheiten(int menge) {
        if (menge >= 0 && menge <= MAX_HOLZEINHEITEN) {
            vorhandeneHolzeinheiten = menge;
        } else {
            throw new IllegalArgumentException("Ungültige Menge für Holzeinheiten.");
        }
    }

    public void setVorhandeneSchrauben(int menge) {
        if (menge >= 0 && menge <= MAX_SCHRAUBEN) {
            vorhandeneSchrauben = menge;
        } else {
            throw new IllegalArgumentException("Ungültige Menge für Schrauben.");
        }
    }

    public void setVorhandeneFarbeinheiten(int menge) {
        if (menge >= 0 && menge <= MAX_FARBEINHEITEN) {
            vorhandeneFarbeinheiten = menge;
        } else {
            throw new IllegalArgumentException("Ungültige Menge für Farbeinheiten.");
        }
    }

    public void setVorhandeneKartoneinheiten(int menge) {
        if (menge >= 0 && menge <= MAX_KARTONEINHEITEN) {
            vorhandeneKartoneinheiten = menge;
        } else {
            throw new IllegalArgumentException("Ungültige Menge für Kartoneinheiten.");
        }
    }

    public void setVorhandeneGlaseinheiten(int menge) {
        if (menge >= 0 && menge <= MAX_GLASEINHEITEN) {
            vorhandeneGlaseinheiten = menge;
        } else {
            throw new IllegalArgumentException("Ungültige Menge für Glaseinheiten.");
        }
    }
}