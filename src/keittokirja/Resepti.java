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
 * Luodaan resepti, johon tulee tiedot reseptistä (nimi, ohjeet, valmistusaika, lähde, id)
 * Tämä yhdistetään oikeaan liimaan ja aineisiin, jolloin tulee kokonainen resepti keittokirja luokan avulla.
 *
 */
public class Resepti implements Cloneable{
    
    private String reseptinNimi;
    private String reseptinOhjeet;
    private String reseptinValmistusaika;
    private String reseptiLahde;
    private int reseptinId;
    private static int seuraavaId = 1;
    
    
    /**
     * tyhjä constructori
     */
    public Resepti() { 
        
    }
    
    
    /**
     * constructori
     * @param reseptinNimi reseptin nimi
     * @param reseptinOhjeet reseptin ohje
     * @param reseptinValmistusaika reseptin valmistusaika
     * @param reseptiLahde reseptin lähde
     */
    public Resepti(String reseptinNimi, String reseptinOhjeet, String reseptinValmistusaika, String reseptiLahde) { 
        this.reseptinNimi = reseptinNimi;
        this.reseptinOhjeet = reseptinOhjeet;
        this.reseptinValmistusaika = reseptinValmistusaika;
        this.reseptiLahde = reseptiLahde;
    }
    
    
    /** Rekisteröi reseptin ja kasvattaa id numeroa seuraavaa varten
     * @return reseptin id
     * @example
     * <pre name="test">
     * Resepti pyttis = new Resepti();
     * Resepti lasa = new Resepti();
     * pyttis.rekisteroi() === 2;       //koska jostain syystä toString testataan ensin ja se kerkeää ottamaan
     * lasa.rekisteroi() === 3;         //staattisen seuraavaId 1
     * </pre>
     */
    public int rekisteroi() {
        reseptinId = seuraavaId;
        seuraavaId += 1;
        return reseptinId;
    }
    
    
    /**
     * @return palauttaa reseptin nimen
     */
    public String getNimi() {
        return reseptinNimi;
    }
    
    
    /**
     * @return reseptin ohje merkkijonona
     */
    public String getOhjeet() {
        return reseptinOhjeet;
    }

    
    /**
     * @return reseptin ohje merkkijonona
     */
    public String getAika() {
        return reseptinValmistusaika;
    }
    
    
    /**
     * @return reseptin ohje merkkijonona
     */
    public String getLahde() {
        return reseptiLahde;
    }
    
        
    /**
     * @return palauttaa reseptin id numeron
     */
    public int getReseptinId() {
        return reseptinId;
    }

    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setReseptinId(int nr) {
        reseptinId = nr;
        if (reseptinId >= seuraavaId) seuraavaId = reseptinId + 1;
    }
    
    
    /**
     * Palauttaa reseptin tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return resepti tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Resepti lasagne = new Resepti();
     *   lasagne.parse("  1 |  Pyttipannu  |   Pilko perunat yms   | 20 minuuttia |  www.k-ruoka.com ");
     *   lasagne.toString().startsWith("1|Pyttipannu|Pilko") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        return "" + getReseptinId() + "|" + 
                    getNimi() + "|" + 
                    merkkijonoksi(getOhjeet()) + "|" + 
                    getAika()+ "|" +
                    getLahde();
    }
    
    
    /** Muuttaa ohjeen merkkijonoksi jossa ei ole rivinvaihtoja
     * KirjaGUIController lukiessa ohjeen osaa muuttaa "_" takaisin rivinvaihdoksi
     * @param jono jono josta etsitaan rivinvaihtoja
     * @return jono jossa ei ole rivinvaihtoja vaan "/n"
     */
    public static String merkkijonoksi(String jono) {
        String muutettu = jono.replaceAll("\\n","_");
        return muutettu;
    }


    /**
     * Selvitää jäsenen tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta jäsenen tiedot otetaan
     * 
     * @example
     * <pre name="test">
     * Resepti lasagne = new Resepti();
     * lasagne.parse("  1 | Pyttipannu |Pilko perunat yms | 20 minuuttia | www.k-ruoka.com");
     * lasagne.getReseptinId() === 1;
     * lasagne.toString().startsWith("1|Pyttipannu|Pilko") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *
     * lasagne.rekisteroi();
     * int n = lasagne.getReseptinId();
     * lasagne.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     * lasagne.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     * lasagne.getReseptinId() === n+20+1;
     * </pre>
     */
    public void parse(String rivi) { //reseptiid | nimi | ohje | aika | lähde
        StringBuilder sb = new StringBuilder(rivi);
        setReseptinId(Mjonot.erota(sb, '|', getReseptinId()));
        reseptinNimi = Mjonot.erota(sb, '|', getNimi());
        reseptinOhjeet = Mjonot.erota(sb, '|', getOhjeet());
        reseptinValmistusaika = Mjonot.erota(sb, '|', getAika());
        reseptiLahde = Mjonot.erota(sb, '|', getLahde());
        
    }
    
    
    @Override
    public boolean equals(Object resepti) {
        if ( resepti == null ) return false;
        return this.toString().equals(resepti.toString());
    }
    

    @Override
    public int hashCode() {
        return reseptinId;
    }
    
    
    /**Tulostaa reseptin tiedot
     * @param out tietovirta
     */
    public void tulosta(PrintStream out) {
        out.println("ID:" + " " + reseptinId);
        out.println("Nimi:" + " " + reseptinNimi);
        out.println("Ohjeet:" + " " + reseptinOhjeet);
        out.println("Valmistusaika:" + " " + reseptinValmistusaika);
        out.println("Lähde:" + " " + reseptiLahde);
    }
    
    
    /**
     * @param nimi nimi
     * @param ohjeet ohje
     * @param valmistusaika aika
     * @param lahde lähde
     * @example
     * <pre name="test">
     *  Resepti pyttipannu = new Resepti();
     *  pyttipannu.asetaTiedot("pitsa", "leivo", "60min", "internetti");
     *  pyttipannu.getNimi() === "pitsa";
     * </pre>
     */
    public void asetaTiedot(String nimi, String ohjeet, String valmistusaika, String lahde) {
        this.reseptinNimi = nimi;
        this.reseptinOhjeet = ohjeet;
        this.reseptinValmistusaika = valmistusaika;
        this.reseptiLahde = lahde;
    }
    
    
    /**
     * @param os tietovirtaaaaaa
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Kloonaaminen muokkausikkunaa varten. Luodaan klooni, jotta
     * jos muokattu resepti ei tallennetakkaan niin klooni tuhoutuu
     * ja jos muokattu tallennetaan niin resepti laitetaan osoittamaan
     * kloonia ja vanha jää roskaksi
     */
    @Override
    public Resepti clone() throws CloneNotSupportedException {
        Resepti uusi;
        uusi = (Resepti) super.clone();
        return uusi;
    }
    

    /**
     * @param args ei viela
     */
    public static void main(String[] args) {
        Resepti pyttipannu = new Resepti();
        Resepti pyttipannu2 = new Resepti();
        
        pyttipannu2.asetaTiedot("Lasagne", "Paistajliha yms", "1h", "karri");
        pyttipannu.rekisteroi();
        pyttipannu2.rekisteroi();
        
        //testataan
        
        pyttipannu.tulosta(System.out);
        pyttipannu2.tulosta(System.out);
        
        int juu =pyttipannu2.getReseptinId();
        System.out.println(juu);
        
        String testi = "Tahan tulee vali \n ja jatkuu";
        System.out.println(testi);
        testi = merkkijonoksi(testi);
        System.out.println(testi);
    }
}
    
    

