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
    private static final int PRODUKTIONSZEIT = 30;
    
    
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
     * Gibt die Anzahl der Kartoneinheiten zurück.
     * 
     * @return Kartoneinheiten der Premiumtuer
     */
    public static int getKartoneinheiten() {
        return KARTONEINHEITEN;
    }

    /**
     * Gibt die Produktionszeit zurück.
     * 
     * @return Produktionszeit der Premiumtuer
     */
    public static int getProduktionszeit() {
        return PRODUKTIONSZEIT;
    }
}
