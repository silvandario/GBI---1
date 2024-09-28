
/**
 * Standardtuer ist eine Subklasse von Produkt. Standardtuer erweitert demnach Produkt.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Standardtuer extends Produkt
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private static final int holzeinheiten = 2;
    private static final int schrauben = 10;
    private static final int farbeinheiten = 2;
    private static final int kartoneinheiten = 1;
    private static final int produktionsZeit = 10;
    
    /**
     * Gibt die Anzahl der Holzeinheiten zurück.
     * 
     * @return Anzahl der Holzeinheiten der Standardtuer
     */
    public static int getHolzeinheiten() {
        return holzeinheiten;
    }

    /**
     * Gibt die Anzahl der Schrauben zurück.
     * 
     * @return Anzahl der Schrauben der Standardtuer
     */
    public static int getSchrauben() {
        return schrauben;
    }
    
    /**
     * Gibt die Farbeinheiten zurück.
     * 
     * @return Farbeinheiten der Standardtuer
     */
    public static int getFarbeinheiten() {
        return farbeinheiten;
    }
    
    /**
     * Gibt die Kartoneinheiten zurück.
     * 
     * @return Kartoneinheiten der Standardtuer
     */
    public static int getKartoneinheiten() {
        return kartoneinheiten;
    }
    
    /**
     * Gibt die Produktionszeit zurück.
     * 
     * @return Produktionszeit der Standardtuer
     */
    public static int getProduktionsZeit() {
        return produktionsZeit;
    }
}
