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
 * Luokka yhtä raaka-ainetta varten.
 * Aine tietää ainoastaan oman järjestysnumeronsa(aineenId) sekä oman nimensä(aineenNimi)
 */
public class Aine {
    
    private int aineenId;
    private String aineenNimi;
    private static int seuraavaId = 1;
    
    
    /**Constructori johon annetaan 
     * @param nimi asettaa aineen nimen
     */
    public Aine(String nimi) {
        aineenNimi = nimi;
    }
    
    
    /**Constructori johon ei anneta mitään tietoa.
     */
    public Aine() {
        
    }
    
    
    
    /**
     * Palauttaa jäsenen tunnusnumeron.
     * @return jäsenen tunnusnumero
     */
    public int getAineenId() {
        return aineenId;
    }


    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setAineenId(int nr) {
        aineenId = nr;
        if (aineenId >= seuraavaId) seuraavaId = aineenId + 1;
    }
    
    
    /** Rekisteröi aineen ja kasvattaa id numeroa seuraavaa varten
     * @return raaka-aineen id
     * @example
     * <pre name="test">
     * Aine x = new Aine("kanaa");
     * Aine y = new Aine("majoneesia");
     * x.rekisteroi() === 1;
     * y.rekisteroi() === 2;
     * </pre>
     */
    public int rekisteroi() {
        aineenId = seuraavaId;
        seuraavaId += 1;
        return aineenId;
    }
    
    
    /**
     * Palauttaa jäsenen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return jäsen tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Aine aine = new Aine();
     *   aine.parse("   3  | Ankka");
     *   aine.toString().startsWith("3|A") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        return "" + getAineenId() + "|" + aineenNimi;
    }


    /**
     * Selvitää jäsenen tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta jäsenen tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Aine aine = new Aine();
     *   aine.parse("   3  |  Ankka  ");
     *   aine.getAineenId() === 3;
     *   aine.toString().startsWith("3|Ankka") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *
     *   aine.rekisteroi();
     *   int n = aine.getAineenId();
     *   aine.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   aine.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   aine.getAineenId() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setAineenId(Mjonot.erota(sb, '|', getAineenId()));
        aineenNimi = Mjonot.erota(sb, '|', aineenNimi);
    }
    
    
    @Override
    public boolean equals(Object aine) {
        if ( aine == null ) return false;
        return this.toString().equals(aine.toString());
    }
    

    @Override
    public int hashCode() {
        return aineenId;
    }
    
    
    /**
     * @return raaka-aineen id
     */
    public String getNimi() {
        return aineenNimi;
    }
    
    
    /**Asettaa aineelle nimen
     * @param nimi nimi joka aineelle halutaan
     */
    public void setNimi(String nimi) {
        this.aineenNimi = nimi;
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
        out.print("ID:" + " " + aineenId + ", ");
        out.println("Nimi:" + " " + aineenNimi);
    }
    
    
    /**
     * @param args ei käytössä viel
     */
    public static void main(String[] args) {
        Aine peruna = new Aine("peruna");
        peruna.rekisteroi();
        peruna.tulosta(System.out);
        Aine jauheliha = new Aine("Jauheliha");
        jauheliha.rekisteroi();
        jauheliha.tulosta(System.out);
        Aine peruna3 = new Aine("peruna3");
        peruna3.rekisteroi();
        peruna3.tulosta(System.out);
        
    }
}
