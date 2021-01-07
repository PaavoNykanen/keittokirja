package keittokirja;

import java.io.OutputStream;
import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author paavo
 * @author karri
 * @version 17.3.2020
 * @version 23.4.2020
 * 
 * Liimaolio tietää, mihin reseptiin se kuuluu(ruokaId), mitä raaka-ainetta se edustaa(aineId),
 * kuinka paljon tätä raaka-ainetta tulee tähän reseptiin(maara), sekä oman järjestysnumeronsa(liimaId)
 *
 */
public class Liima {
    
    private int liimaId;
    private int ruokaId;
    private int aineId;
    private String maara ="";
    private static int seuraavaId = 1;
    
    
    /**
     * Konstruktori Liimalle
     * @param ruokaId ruuan id mihin aine ja maara sidotaan
     * @param aineId aineen id
     * @param maara maara
     */
    public Liima(int ruokaId, int aineId, String maara) {
        this.ruokaId = ruokaId;
        this.aineId = aineId;
        this.maara = maara;
    }
    
    
    /**
     * Tyhjä konstruktori sille, jos Liiman tiedot luetaan esim tiedostosta.
     */
    public Liima() {
        
    }
    
    
    /**Laittaa liiman id:ksi yhden isomman ja kasvattaa seuraavanid seuraavaa
     * rekisterointia varten
     * @return palauttaa liiman id
     * @example
     * <pre name="test">
     * Liima jliha = new Liima(1, 2, "500g");
     * Liima maito = new Liima(2, 4, "500g");
     * jliha.rekisteroi() === 4;        //toString testissä seuraavaId asetetaan jo 4
     * maito.rekisteroi() === 5;        //eikä suostu testaamaan rekisteroi() ensin :(
     * </pre>
     */
    public int rekisteroi() {
        liimaId = seuraavaId;
        seuraavaId += 1;
        return liimaId;
    }
    
    /**
     * @param aineid aineen id johon liitetaan
     * @param maarai kuinka paljon ainetta
     */
    public void asetaLiimaTiedot(int aineid, String maarai) {
        this.aineId = aineid;
        this.maara = maarai;
    }
    
    
    /**
     * @return palauttaa ruokalajiin liittyvän id:n
     */
    public int getRuokaId() {
        return ruokaId;
    }
    
    /**
     * @return palauttaa maaran
     */
    public String getMaara() {
        return maara;
    }
    
    /**
     * @return palauttaa aineeseen liittyvän id:n
     */
    public int getAineId() {
        return aineId;
    }
    
    
    /**
     * Palauttaa liiman tunnusnumeron.
     * @return liiman tunnusnumero
     */
    public int getLiimaId() {
        return liimaId;
    }


    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setLiimaId(int nr) {
        liimaId = nr;
        if (liimaId >= seuraavaId) seuraavaId = liimaId + 1;
    }
    
    
    /**
     * Palauttaa jäsenen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return jäsen tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Liima aine = new Liima();
     *   aine.parse("   3  |  2  |  54  | ripaus  ");
     *   aine.toString().startsWith("3|2|54|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        return "" + getLiimaId() + "|" + 
                    getRuokaId() + "|" + 
                    getAineId() + "|" + 
                    getMaara();
    }


    /**
     * Selvitää jäsenen tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta jäsenen tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Liima aine = new Liima();
     *   aine.parse("   3  |  2  |  54  | ripaus  ");
     *   aine.getLiimaId() === 3;
     *   aine.toString().startsWith("3|2|54|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *
     *   aine.rekisteroi();
     *   int n = aine.getLiimaId();
     *   aine.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   aine.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   aine.getLiimaId() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String rivi) { //liimaid | ruokaid | aineid | maara | 
        StringBuilder sb = new StringBuilder(rivi);
        setLiimaId(Mjonot.erota(sb, '|', getLiimaId()));
        ruokaId = Mjonot.erota(sb, '|', ruokaId);
        aineId = Mjonot.erota(sb, '|', aineId);
        maara = Mjonot.erota(sb, '|', maara);
        
    }
    
    
    @Override
    public boolean equals(Object liima) {
        if ( liima == null ) return false;
        return this.toString().equals(liima.toString());
    }
    

    @Override
    public int hashCode() {
        return liimaId;
    }
    
    
    /**
     * @param os tietovirtaaaaaa
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * @param out tietovirta
     */
    public void tulosta(PrintStream out) {
        out.println(ruokaId + ", " + aineId + ", " + maara + ", " + liimaId);
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Liima eka = new Liima(1,0,"3 kpl");
        eka.rekisteroi();
        
        Liima toka = new Liima(1,1, "500g");
        toka.rekisteroi();
        
        eka.tulosta(System.out);
        toka.tulosta(System.out);
    }
}
