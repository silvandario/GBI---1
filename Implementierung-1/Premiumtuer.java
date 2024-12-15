/**
 * Premiumtuer ist eine Subklasse von Produkt. Premiumtür erweitert also Produkt.
 * 
 * @author
 * @version 2.0
 */
public class Premiumtuer extends Produkt {
    // Instanzvariablen -> Konstanten für die Porduktion einer Einheit
    // Diese sind fix (final)
    private static final int HOLZEINHEITEN = 4;
    private static final int SCHRAUBEN = 5;
    private static final int GLASSEINHEITEN = 5;
    private static final int FARBEINHEITEN = 1;
    private static final int KARTONEINHEITEN = 5;
    private static final int PRODUKTIONSZEIT = 30; // Zur verkürzung der Tests wird anstelle 3o Min 1.800.000 Millisekunden bzw 1/60 davon 30 zur Bechleunigung der Tests verwendet
    
    
    /**
     * Konstruktor ruft Superklasse-Konstruktor auf
     */
    public Premiumtuer() {
        super();
    }

    /**
     * Gibt die Anzahl der Holzeinheiten zurück.
     * 
     * @return Anzahl der Holzeinheiten der Premiumtuer
     */
    public static int getHolzeinheiten() {
        return HOLZEINHEITEN;
    }

    /**
     * Gibt die Anzahl der Schrauben zurück.
     * 
     * @return Anzahl der Schrauben der Premiumtuer
     */
    public static int getSchrauben() {
        return SCHRAUBEN;
    }

    /**
     * Gibt die Anzahl der Glaseinheiten zurück.
     * 
     * @return Anzahl der Glaseinheiten der Premiumtuer
     */
    public static int getGlaseinheiten() {
        return GLASSEINHEITEN;
    }

    /**
     * Gibt die Anzahl der Farbeinheiten zurück.
     * 
     * @return Farbeinheiten der Premiumtuer
     */
    public static int getFarbeinheiten() {
        return FARBEINHEITEN;
    }
    
    /**
     * Gibt die Produktionszeit zurück.
     * 
     * @return Produktionszeit der Premiumtuer
     */
    @Override
    public int getProduktionszeit() {
        return PRODUKTIONSZEIT;
    }

    /**
     * Gibt die KARTONEINHEITEN zurück.
     * 
     * @return KARTONEINHEITEN der Premiumtuer
     */
    public static int getKartoneinheiten() {
        return KARTONEINHEITEN;
    }
 
}
