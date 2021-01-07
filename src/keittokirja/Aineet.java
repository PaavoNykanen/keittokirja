package keittokirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;


/**
 * @author paavo
 * @author karri
 * @version 17.3.2020
 * @version 23.4.2020
 * 
 * Luokka keittokirjan raaka-aineille. Eli tämä on ns. säiliöluokka Aine-olioille. Osaa tallentaa ja lukea aineet.dat tiedostoa
 */
public class Aineet {
    
    private ArrayList<Aine> alkiot = new ArrayList<Aine>();
    private static String tiedostoSijainti = "datat/aineet.dat";
    private static boolean muutettu = false;
    
    
    /**
     * Constructor jossa alustetaan aineet-luokka
     */
    public Aineet() {

    }
    
    
    /**
     * Lisätään listaan raaka-aine. 
     * @param aine raaka-aine joka lisätään raaka-aineiden listaan.
     * @example
     * #import java.util.ArrayList;
     * <pre name="test">
     * 
     * Aineet aineet = new Aineet();
     * Aine aine1 = new Aine("peruna");
     * Aine aine2 = new Aine("pottu");
     * 
     * aineet.lisaaAine(aine1);
     * aineet.getLkm() === 1;
     * 
     * aineet.lisaaAine(aine2);
     * aineet.getLkm() === 2;
     * </pre>
     */
    public void lisaaAine(Aine aine){
        alkiot.add(aine);
        muutettu=true;
    }
    
    
    /**
     * @param aine raaka-aine joka poistetaan raaka-aineiden listasta
     */
    public void poistaAine(Aine aine) {
        alkiot.remove(aine);
        muutettu=true;
    }
    
    
    /**
     * 
     * @return palauttaa montako ainetta on listassa
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Palauttaa listan aineista, jotka kuuluvat tiettyyn reseptiin
     * @param ret liima-olioista kerätty taulukko, joka kertoo mitkä raaka-aineet kuuluvat tietylle reseptille
     * @return palauttaa listan aineista, jotka kuuluvat tietylle reseptille
     */
    public List<Aine> annaAineet(int[] ret) {
        
        List<Aine> loydetyt = new ArrayList<Aine>();

        for (int i = 0; i < ret.length; i++) {
            loydetyt.add(annaAine(ret[i]));
        }
        return loydetyt;
    }
    
    /**
     * @param id raaka aineen id
     * @return Aine-olio.
     */
    public Aine annaAine(int id) {
        for (Aine i : alkiot) {
            if (i.getAineenId() == id) return i;
        }
        for (Aine i : alkiot) {
            if (i.getNimi().equals("")) return i;   //poistaTurhat()-metodia varten
        }
        return null;
    }
    
    /**
     * Tallentaa omaan tiedostoonsa reseptien tiedot
     * @example
     * <pre name="test">
     *  Aineet aineet1 = new Aineet();
        Aine peruna1 = new Aine("peruna");
     *  peruna1.rekisteroi();
     *  aineet1.lisaaAine(peruna1);
     *  Aineet.setTiedostoSijainti("tiedostoTestit/aineetTesti.dat");
     *  aineet1.tallenna();
     *  aineet1.getLkm() === 1;
     *  aineet1.lueTiedosto();
     *  aineet1.getLkm() === 2;
     * </pre>
     */ 
    public void tallenna() {
        if (!muutettu) return;
        File tied = new File(tiedostoSijainti);
        try (PrintStream file = new PrintStream((tied.getCanonicalPath()))) {
            for (int i = 0; i< alkiot.size(); i++) {
                Aine aine = alkiot.get(i);
                file.println(aine.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        muutettu = false;
    }
    
    
    /**
     * Lukee aineet.dat tiedoston ja käsittelee siitä aine olion
     * Testattu tallenna metodissa.
     */
    public void lueTiedosto() {
        File tied = new File(tiedostoSijainti);
        try (Scanner file = new Scanner(new FileInputStream(tied))) {
            String rivi = null;
            while (file.hasNext()) {
                rivi = file.nextLine();
                rivi = rivi.trim();
                if ("".equals(rivi)) continue;
                Aine aine = new Aine();
                aine.parse(rivi);
                lisaaAine(aine);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Tiedosto ei aukea! "+e.getMessage());
        }
    }

    
    
    /**
     * @param args  ei käytössä
     */
    public static void main(String[] args) {
        //main tyhjätty, ettei aiheuta vahingossakaan ongelmia käyttöliittymälle :)
    }
    
    
    /** Asetaa staattisen muuttujan, käytetään perjaatteessa vain testeille
     * @param polku polku jonka päässä on tiedostot
     */
    public static void setTiedostoSijainti(String polku) {
        tiedostoSijainti = polku;
    }


    /** Katsoo onko jollain jo nimi jota tuodaan
     * @param nimi nimi joka tarkistetaan
     * @return palauttaa true jos on jo sen niminen aine
     */
    public Aine onkoVarattu(String nimi) {
        for (int i = 0; i<alkiot.size(); i++) {
            if (alkiot.get(i).getNimi().equals(nimi)) {
                return alkiot.get(i);
            }
        }
        return null;
    }
}
