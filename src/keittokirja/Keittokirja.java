package keittokirja;

import java.util.*;

/**
 * @author paavo
 * @author karri
 * @version 17.3.2020
 * @version 23.4.2020
 * Luokka, jonka pitäisi käyttää Reseptit, Liimat ja Raaka-aineet luokkia ja 
 * niitä alempia luokkia ja hallinoida kokonaisia reseptejä, joissa on mukana
 * ohjeet ja raaka-aineet ja kaikki tarvittava. 
 */
public class Keittokirja {
    
    
    private Reseptit reseptit = new Reseptit();
    private Aineet aineet = new Aineet();
    private Liimat liimat = new Liimat();
    
    
    /**
     * constructori joka alustaa keittokirjan
     */
    public Keittokirja() {
        
    }
    
    
    
    /**
     * Kloonaaminen muokkausikkunaa varten. Luodaan klooni, jotta
     * jos muokattu resepti ei tallennetakkaan niin klooni tuhoutuu
     * ja jos muokattu tallennetaan niin resepti laitetaan osoittamaan
     * kloonia ja vanha jää roskaksi
     */
    @Override
    public Keittokirja clone() throws CloneNotSupportedException {
        Keittokirja uusi;
        uusi = (Keittokirja) super.clone();
        return uusi;
    }
    
       
    /**
     * @param lisattava lisattava resepti
     * käytetään luokassa reseptit olevaa lisääjää,
     * sama idea kuin mäkihyppydemossa
     * @throws SailoException jos taulukko on täynnä
     */
    public void lisaa(Resepti lisattava) throws SailoException {
        reseptit.lisaaResepti(lisattava);
    }
    
    
    /**
     * @param lisattava lisattava raaka-aine
     * @throws SailoException jos raaka-aineet on täynnä
     */
    public void lisaa(Aine lisattava) throws SailoException {
        aineet.lisaaAine(lisattava);
    }
    
    
    /**
     * @param lisattava lisattava liima rivi
     * @throws SailoException jos ei mahdu
     */
    public void lisaaLiima(Liima lisattava) throws SailoException {
        liimat.lisaaLiima(lisattava);
    }
    
    
    /**
     * @param i indeksi mistä resepti
     * @return indeksissä oleva resepti
     * Käyttää reseptit luokan anna toimintoa
     */
    public Resepti annaResepti(int i) {
        return reseptit.anna(i);
    }
    
    
    /**
     * Reseptin
     * @param i reseptissä oleva ruokaID tähän
     * @return lista aineista, jotka tulevat reseptiin
     * @example
     * <pre name="test">
     * #import keittokirja.*;
     * #import java.util.*;
     * Keittokirja keittokirja = new Keittokirja();
     * Liima liima1 = new Liima(1, 0, "500g");
     * Liima liima2 = new Liima(1, 1, "400g");
     * Liima liima3 = new Liima(2, 2, "100g");
     * Aine aine1 = new Aine("peruna");
     * Aine aine2 = new Aine("pottu");
     * Aine aine3 = new Aine("perunia");
     * liima1.rekisteroi();         liima2.rekisteroi();        liima3.rekisteroi();
     * aine1.rekisteroi();          aine2.rekisteroi();         aine3.rekisteroi();
     * try {
     *     keittokirja.lisaaLiima(liima1);
     *     keittokirja.lisaaLiima(liima2);
     *     keittokirja.lisaaLiima(liima3);
     *     keittokirja.lisaa(aine1);     
     *     keittokirja.lisaa(aine2);     
     *     keittokirja.lisaa(aine3); 
     * } catch (SailoException e) {
     *      System.err.println(e.getMessage());
     * }
     *  
     * List<Aine> lista = new ArrayList<Aine>();
     * lista = keittokirja.annaAineet(1);
     * lista.size() === 2;
     * 
     * List<Aine> lista2 = new ArrayList<Aine>();
     * lista2 = keittokirja.annaAineet(2);
     * lista2.size() === 1;
     */
    public List<Aine> annaAineet(int i) {
        int[] t = liimat.annaLiimat(i);
        return aineet.annaAineet(t);
    }
    
    
    /**
     * @param i indeksi mistä otetaan
     * @return antaa yhden pyydetyn raaka aineen
     */
    public Aine annaAine(int i) {
        return aineet.annaAine(i);
    }
    
    
    /**
     * @param i mistä indeksiksistä otetaan
     * @return palauttaa liiman liimat taulukon paikasta i
     */
    public Liima annaLiima(int i) {
        return liimat.annaLiima(i);
    }
    
    
    /**
     * @return palauttaa reseptien lukumäärän
     */
    public int getReseptitLkm() {
        return reseptit.getLkm();
    }
    
    
    /**
     * @return raaka-ainelistan aineiden lukumaara
     */
    public int getAineetLkm() {
        return aineet.getLkm();
    }
    
    /**
     * @return liimojen lukumaara
     */
    public int getLiimatLkm() {
        return liimat.getLkm();
    }
    
    
    /**
     * @param ruokaId Liimassa se id, joka liittää tietyt raaka-aineet tiettyihin ruokalajeihin
     * @return lista raaka-aineiden ID:stä
     */
    public int[] annaLiimat(int ruokaId) {
        return liimat.annaLiimat(ruokaId);
    }
    
    
    /**
     * @param ruokaId ruuan id
     * @return palauttaa liimat jotka kuuluu ruokaan
     */
    public ArrayList<Liima> annaLiimatIdlla(int ruokaId) {
        return liimat.annaLiimatIdlla(ruokaId);
    }
    
    
    /**
     * @param ruokaId Liimassa se id, joka liittää tietyt raaka-aineet tiettyihin ruokalajeihin
     * @return lista raaka-aineiden ID:stä
     */
    public ArrayList<String> annaLiimaMaara(int ruokaId) {
        return liimat.annaLiimaMaara(ruokaId);
    }
    
    
    /**
     * tallentaa tiedostoihin niiden tiedot
     */
    public void tallenna() {
        reseptit.tallenna();
        liimat.tallenna();
        aineet.tallenna();
    }
    
    
    /**
     * @param reseptiKohdalla resepti joka halutaan poistaa ja siihen liittyvät liimat poistetaan myös
     */
    public void poista(Resepti reseptiKohdalla) {
        if ( reseptiKohdalla == null ) return;
        liimat.poistaLiimat(reseptiKohdalla.getReseptinId());
        reseptit.poistaResepti(reseptiKohdalla);
    }
    
    
    /**
     * poistetaan tyhjät aineet
     */
    public void poistaTurhat() {
        Aine aine;
        for (int i = 1; i < aineet.getLkm(); i++) {
            aine = aineet.annaAine(i);
            if (aine == null) break;
            if (aine.getNimi().equals("") || aine.getNimi().equals("Raaka-aine")) {
                aineet.poistaAine(aine);                
            }
        }
        liimat.poistaTurhat();
    }
    
    
    /**
     * Lukee tiedostot ja asettelee ne käyttöliittymään
     */
    public void lueTiedosto() {
        reseptit.lueTiedosto();
        liimat.lueTiedosto();
        aineet.lueTiedosto();
    }
    
    
    /**
     * @param args ei viela
     */
    public static void main(String[] args) {
        Keittokirja keittokirja = new Keittokirja();

        Resepti pyttipannu = new Resepti();
        Aine peruna = new Aine("peruna");
        peruna.rekisteroi();
        Aine peruna2 = new Aine("Jauheliha");
        peruna2.rekisteroi();
        Resepti pyttipannu2 = new Resepti();

        pyttipannu.rekisteroi();
        pyttipannu2.rekisteroi();

        pyttipannu2.asetaTiedot("Lasagne", "Paista jliha yms", "1h", "karri");

        try {
            keittokirja.lisaa(pyttipannu);
            keittokirja.lisaa(pyttipannu2);
            keittokirja.lisaa(peruna);
            keittokirja.lisaa(peruna2);
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }


        //testataan luokkaa, ekat 2 on oikein ja loput tulostetut on oletusksena pyttipannuja,
        //id muuttuu vain ekoissa kahdessa koska muita ei rekisteröidä
        for (int i =0; i<keittokirja.getReseptitLkm(); i++) {
            Resepti resepti = keittokirja.annaResepti(i);
            resepti.tulosta(System.out);

            // Tällä tarkistetaan onko aineetid sama kuin reseptin aineen id,
            //jos on niin tulostetaan aine
            for (int j =1; j<keittokirja.getAineetLkm(); j++) {
                Aine aine = keittokirja.annaAine(j);
                if (aine.getAineenId() == resepti.getReseptinId()) {
                    aine.tulosta(System.out);
                }
            }
        }
    }


    /**
     * @param resepti resepti joka muutetaan tai lisataan
     * Tarkistaa korvataanko vai luodaanko uusi
     */
    public void korvaaTaiLisaa(Resepti resepti) {
        reseptit.korvaaTaiLisaa(resepti);
    }



    /** Tarkistaa onko nimi jo varattu
     * @param nimi aine jonka nimi etsitaan
     * @return palauttaa aineen, null jos ei ole
     */
    public Aine onkoVarattu(String nimi) {
        return aineet.onkoVarattu(nimi);
    }
}
