
/**
 * Premiumtuer ist eine Subklasse von Produkt. Premiumtür erweitert also Produkt.
 * 
 * @author SL
 * @version 1.0
 */
public class Premiumtuer extends Produkt
{
    // Instanzvariablen; Porduktbeschaffenheit ist nicht änderbar, d.h. final, Maschinen können evt. produktiver werden und Verpaclungen effizienter
    private static final int holzeinheiten = 4;
    private static final int schrauben = 5;
    private static final int glaseinheiten = 5;
    private static final int farbeinheiten = 1;
    private static int kartoneinheiten = 5;
    private static int produktionsZeit = 30;

    /**
     * Gibt die Anzahl der Holzeinheiten zurück.
     * 
     * @return Anzahl der Holzeinheiten der Premiumtuer
     */
    public static int getHolzeinheiten() {
        return holzeinheiten;
    }

    /**
     * Gibt die Anzahl der Schrauben zurück.
     * 
     * @return Anzahl der Schrauben der Premiumtuer
     */
    public static int getSchrauben() {
        return schrauben;
    }

    /**
     * Gibt die Anzahl der Glaseinheiten zurück.
     * 
     * @return Anzahl der Glaseinheiten der Premiumtuer
     */
    public static int getGlaseinheiten() {
        return glaseinheiten;
    }
    
    /**
     * Gibt die Farbeinheiten zurück.
     * 
     * @return Farbeinheiten der Premiumtuer
     */
    public static int getFarbeinheiten() {
        return farbeinheiten;
    }
    
    /**
     * Gibt die Kartoneinheiten zurück.
     * 
     * @return Kartoneinheiten der Premiumtuer
     */
    public static int getKartoneinheiten() {
        return kartoneinheiten;
    }
    
    /**
     * Gibt die Produktionszeit zurück.
     * 
     * @return Produktionszeit der Premiumtuer
     */
    public static int getProduktionsZeit() {
        return produktionsZeit;
    }
}
