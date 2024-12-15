
/**
 * Standardtuer ist eine Subklasse von Produkt. Standardtuer erweitert demnach Produkt.
 * 
 * @author Silvan Ladner
 * @version 2.0
 */
public class Standardtuer extends Produkt {
    // Instanzvariablen -> Konstanten für die Porduktion einer Einheit
    // Diese sind fix (final)
    private static final int HOLZEINHEITEN = 2;
    private static final int SCHRAUBEN = 10;
    private static final int FARBEINHEITEN = 2;
    private static final int KARTONEINHEITEN = 1;
    private static final int PRODUKTIONSZEIT = 10; // Zur verkürzung der Tests wird anstelle 1o Min 600.000 Millisekunden bzw 1/60 davon 10 zur Bechleunigung der Tests verwendet
    
    /**
     * Konstruktor ruft Superklasse-Konstruktor auf
     */
    
    public Standardtuer() {
        super();
    }

    /**
     * Gibt die Anzahl der Holzeinheiten zurück.
     * 
     * @return Anzahl der Holzeinheiten der Standardtuer
     */
    public static int getHolzeinheiten() {
        return HOLZEINHEITEN;
    }

    /**
     * Gibt die Anzahl der Schrauben zurück.
     * 
     * @return Anzahl der Schrauben der Standardtuer
     */
    public static int getSchrauben() {
        return SCHRAUBEN;
    }

    /**
     * Gibt die Anzahl der Farbeinheiten zurück.
     * 
     * @return Anzahl der Farbeinheiten der Standardtuer
     */
    public static int getFarbeinheiten() {
        return FARBEINHEITEN;
    }

    /**
     * Gibt die Anzahl der Kartoneinheiten zurück.
     * 
     * @return Anzahl der Kartoneinheiten der Standardtuer
     */
    public static int getKartoneinheiten() {
        return KARTONEINHEITEN;
    }

    /**
     * Gibt die Produktionszeit zurück.
     * 
     * @return Produktionszeit der Standardtuer
     */
    @Override
    public int getProduktionszeit() {
        return PRODUKTIONSZEIT;
    }
}
