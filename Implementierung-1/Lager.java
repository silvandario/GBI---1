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
        lieferant = new Lieferant(this); // Lieferant initialisieren
        lieferant.start(); // Lieferant-Thread starten
    }

    public synchronized boolean isLeer() {
        return vorhandeneHolzeinheiten == 0 ||
               vorhandeneSchrauben == 0 ||
               vorhandeneFarbeinheiten == 0 ||
               vorhandeneKartoneinheiten == 0 ||
               vorhandeneGlaseinheiten == 0;
    }

    public synchronized void lagerAuffuellen() {
    if (isVoll()) {
        // Directly return the expected message without extra logs
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

public boolean isVoll() {
    return vorhandeneHolzeinheiten == MAX_HOLZEINHEITEN &&
           vorhandeneSchrauben == MAX_SCHRAUBEN &&
           vorhandeneFarbeinheiten == MAX_FARBEINHEITEN &&
           vorhandeneKartoneinheiten == MAX_KARTONEINHEITEN &&
           vorhandeneGlaseinheiten == MAX_GLASEINHEITEN;
}
    public synchronized void wareLiefern() {
        System.out.println("Lager: Ware wird geliefert...");
        vorhandeneHolzeinheiten = MAX_HOLZEINHEITEN;
        vorhandeneSchrauben = MAX_SCHRAUBEN;
        vorhandeneFarbeinheiten = MAX_FARBEINHEITEN;
        vorhandeneKartoneinheiten = MAX_KARTONEINHEITEN;
        vorhandeneGlaseinheiten = MAX_GLASEINHEITEN;
        System.out.println("Lager: Auffüllung abgeschlossen!");
        lagerBestandAusgeben();
    }

    public synchronized void verbraucheMaterial(int holz, int schrauben, int farbe, int karton, int glas) {
        if (vorhandeneHolzeinheiten >= holz &&
            vorhandeneSchrauben >= schrauben &&
            vorhandeneFarbeinheiten >= farbe &&
            vorhandeneKartoneinheiten >= karton &&
            vorhandeneGlaseinheiten >= glas) {

            vorhandeneHolzeinheiten -= holz;
            vorhandeneSchrauben -= schrauben;
            vorhandeneFarbeinheiten -= farbe;
            vorhandeneKartoneinheiten -= karton;
            vorhandeneGlaseinheiten -= glas;

            System.out.println("Lager: Materialien erfolgreich verbraucht.");
            if (isLeer()) {
                System.out.println("Lager: Materialien niedrig, Auffüllung erforderlich.");
                lagerAuffuellen();
            }
        } else {
            System.out.println("Lager: Nicht genügend Materialien verfügbar!");
        }
    }

        public String gibLagerBestandAlsString() {
        return "-----------Lagerbestand-----------\n" +
               vorhandeneHolzeinheiten + " Holzeinheiten vorhanden\n" +
               vorhandeneSchrauben + " Schrauben vorhanden\n" +
               vorhandeneFarbeinheiten + " Farbeinheiten vorhanden\n" +
               vorhandeneKartoneinheiten + " Kartoneinheiten vorhanden\n" +
               vorhandeneGlaseinheiten + " Glaseinheiten vorhanden\n" +
               "----------- ----------- -----------";
    }
    
    public void lagerBestandAusgeben() {
        System.out.println(gibLagerBestandAlsString());
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
        synchronisiertesPrintln("Materialmangel! Bestellung " + kundenBestellung.gibBestellungsNr() + " kann nicht bearbeitet werden.");
        return false;
    }

}
public void synchronisiertesPrintln(String output) {
        synchronized (System.out) {
            System.out.println(output);
        }
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