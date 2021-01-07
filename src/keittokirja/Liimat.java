package keittokirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

//import fi.jyu.mit.fxgui.Dialogs;
import keittokirja.Liima;
/**
 * @author paavo
 * @author karri
 * @version 17.3.2020
 * @version 23.4.2020
 * 
 * Tämä luokka kerää kaikki liimat yhteen taulukkoon ja sitä voidaan sitten käydä läpi
 * kun etsitään tiettyyn reseptiin liittyvät raaka-aineet. Osaa lukea ja tallentaa liimat.dat tiedostoa
 */
public class Liimat {

    private static int max = 5;
    private static int lkm = 0;
    private Liima[] alkiot = new Liima[max];
    private static String tiedostoSijainti = "datat/liimat.dat";
    private static boolean muutettu = false;

    
    
    /**
     * @param koko miten iso taulukko
     */
    public Liimat(int koko) {
        max = koko;
    }
    
    
    /**
     * perus constructori
     */
    public Liimat() {
        
    }
    
    
    /**
     * Liiman lisäys taulukkoon. Jos ei mahdu, niin tehdään uusi isompi taulukko
     * @param lisattava lisättävä liima-olio
     * @throws SailoException jos tietorakenteesta loppuu tila kesken
     */
    public void lisaaLiima(Liima lisattava) throws SailoException {
        if (lkm >= max) {
            max=max+5;
            Liimat uusi = new Liimat(max);
            for(int i=0; i<alkiot.length; i++) {
                uusi.alkiot[i]=this.alkiot[i];
            }
            this.alkiot = uusi.alkiot;
        }
        alkiot[lkm] = lisattava;
        lkm++;
        muutettu=true;
    }
    
    
    /**
     * @param ruokaId Liimassa se id, joka liittää tietyt raaka-aineet tiettyihin ruokalajeihin
     * @return lista raaka-aineiden ID:stä
     * @example
     * <pre name="test">
     * #import java.util.ArrayList;
     * #import java.util.Arrays;
     * #import keittokirja.SailoException;
     * Liimat liimat = new Liimat();
     * Liima liima1 = new Liima(1, 1, "500g");
     * Liima liima2 = new Liima(1, 2, "200g");
     * Liima liima3 = new Liima(1, 3, "300g");
     * Liima liima4 = new Liima(2, 5, "400g");
     * liima1.rekisteroi();     liima2.rekisteroi();    liima3.rekisteroi();    liima4.rekisteroi();
     * try {
     * liimat.lisaaLiima(liima1);
     * liimat.lisaaLiima(liima2);
     * liimat.lisaaLiima(liima3);
     * liimat.lisaaLiima(liima4);
     * } catch (SailoException e) {
     *      System.err.println(e.getMessage());
     * }
     * int[] t = liimat.annaLiimat(1);
     * Arrays.toString(t) === "[1, 2, 3]";
     * Liimat.setLkm(0);
     * </pre>
     */
    public int[] annaLiimat(int ruokaId) {

        ArrayList<Integer> lista = new ArrayList<Integer>(); //arraylist siksi, että sillä saadaan juuri oikean kokoinen taulukko lopuksi
        
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getRuokaId() == ruokaId) {
                lista.add(alkiot[i].getAineId());
            }
        }
        //tehdään integerarraylististä int[] 
        int[] ret = new int[lista.size()];
        for (int i=0; i < ret.length; i++) {
            ret[i] = lista.get(i).intValue();
        }
        return ret; 
    }
    
    
    /**
     * @param ruokaid ruokaid jonka liimat halutaan
     * @return liimat jotka kuuluvat ruualle
     */
    public ArrayList<Liima> annaLiimatIdlla(int ruokaid) {
        ArrayList<Liima> lista = new ArrayList<Liima>();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getRuokaId() == ruokaid) {
                lista.add(alkiot[i]);
            }
        }
        return lista;
    }
    
    
    /**
     * @param id reseptin id jonka liimat halutaan
     * @return palauttaa liimat 
     */
    public Liima[] annaLiimatTaulukko(int id) {
        int j=0;
        Liima[] liimat = new Liima[alkiot.length];
        for (int i=0; i<alkiot.length; i++) {
            if (alkiot[i].getRuokaId() == id) {
                liimat[j] = alkiot[i];
                j++;
            }
        }
        return liimat;
    }
    
    
    /**
     * @param ruokaId Liimassa se id, joka liittää tietyt raaka-aineet tiettyihin ruokalajeihin
     * @return lista raaka-aineiden määristä
     * @example
     * <pre name="test">
     * #import java.util.ArrayList;
     * #import java.util.Arrays;
     * #import keittokirja.SailoException;
     * Liimat liimat = new Liimat();
     * Liima liima1 = new Liima(1, 1, "500g");
     * Liima liima2 = new Liima(1, 2, "200g");
     * Liima liima3 = new Liima(1, 3, "300g");
     * Liima liima4 = new Liima(2, 5, "400g");
     * liima1.rekisteroi();     liima2.rekisteroi();    liima3.rekisteroi();    liima4.rekisteroi();
     * try {
     * liimat.lisaaLiima(liima1);
     * liimat.lisaaLiima(liima2);
     * liimat.lisaaLiima(liima3);
     * liimat.lisaaLiima(liima4);
     * } catch (SailoException e) {
     *      System.err.println(e.getMessage());
     * }
     * ArrayList<String> t = liimat.annaLiimaMaara(1);
     * t.toString() === "[500g, 200g, 300g]";
     * Liimat.setLkm(0);
     * </pre>
     */
    public ArrayList<String> annaLiimaMaara(int ruokaId) {

        ArrayList<String> lista = new ArrayList<String>();
        
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getRuokaId() == ruokaId) {
                lista.add(alkiot[i].getMaara());
            }
        }
        return lista;
    }
    
    
    /**
     * @return palauttaa montako liimaa on
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * @param i indeksi josta halutaan liima
     * @return palauttaa liiman tietystä paikasta
     */
    public Liima annaLiima(int i) {
        return alkiot[i];
    }
    
    
    /**
     * Tallentaa omaan tiedostoonsa reseptien tiedot
     * @example
     * <pre name="test">
     *  Liimat liimat1 = new Liimat(5);
     *   Liima lisattava = new Liima(1,1,"500g");
     *  lisattava.rekisteroi();
     *  try {
     *       liimat1.lisaaLiima(lisattava);
     *   } catch (SailoException e) {
     *       e.printStackTrace();
     *   } 
     *  Liimat.setTiedostoSijainti("tiedostoTestit/liimatTesti.dat");
     *  liimat1.tallenna();
     *  liimat1.lueTiedosto();
     *  liimat1.annaLiima(0).getLiimaId() === 9; // Eli tässä liimoja on kaksi samanlaista vaikka luotiin itse vain yksi, sillä talenna tallenti liiman tiedot
     *  liimat1.annaLiima(1).getLiimaId() === 9; // ja lueTiedosto loi niillä uuden liiman.
     *  liimat1.annaLiima(2).getLiimaId() === 9; #THROWS NullPointerException //tämä menee yli indexin koska vain kaksi liimaa
     *  
     * Liimat.setLkm(0);
     * </pre>
     *///TODO: Toteuta niin, että on bool muuttuja, joka on false aluksi, mutta jos jotain muutetaan ohjelman sisällä, bool muuttuu true
    public void tallenna() {
        if (!muutettu)return;
        File tied = new File(tiedostoSijainti);
        try (PrintStream file = new PrintStream((tied.getCanonicalPath()))) {
            for (int i = 0; i< this.getLkm(); i++) {
                Liima liima = annaLiima(i);
                file.println(liima.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        muutettu=false;
    }
    
    
    /**
     * Lukee liimat.dat ja luo siitä liima olion
     */
    public void lueTiedosto() {
        File tied = new File(tiedostoSijainti);
        try (Scanner file = new Scanner(new FileInputStream(tied))) {
            String rivi = null;
            while (file.hasNext()) {
                rivi = file.nextLine();
                rivi = rivi.trim();
                if ("".equals(rivi)) continue;
                Liima liima = new Liima();
                liima.parse(rivi);
                try {
                    lisaaLiima(liima);
                } catch (SailoException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Tiedosto ei aukea! "+e.getMessage());
        }
    }
    
    
    /**
     * @param poistettava reseptin id, johon liittyvät liimat pitää tuhota
     * @example
     * <pre name="test">
     * #import java.util.ArrayList;
     * #import java.util.Arrays;
     * #import keittokirja.SailoException;
     * Liimat liimat = new Liimat();
     * Liima liima1 = new Liima(1, 1, "500g");
     * Liima liima2 = new Liima(1, 2, "200g");
     * Liima liima3 = new Liima(1, 3, "300g");
     * Liima liima4 = new Liima(2, 5, "400g");
     * liima1.rekisteroi();     liima2.rekisteroi();    liima3.rekisteroi();    liima4.rekisteroi();
     * try {
     *      liimat.lisaaLiima(liima1);
     *      liimat.lisaaLiima(liima2);
     *      liimat.lisaaLiima(liima3);
     *      liimat.lisaaLiima(liima4);
     * } catch (SailoException e) {
     *      System.err.println(e.getMessage());
     * }
     * liimat.poistaLiimat(1);
     * liimat.getLkm() === 1;
     * Liimat.setLkm(0);
     * </pre>
     */
    public void poistaLiimat(int poistettava) {
        Liima[] uusi = new Liima[alkiot.length];
        int j = 0;
        for (int i=0; i<alkiot.length; i++) {
            if (alkiot[i] == null) continue;
            int id = alkiot[i].getRuokaId();
            if (id == poistettava) {
                lkm--;
                continue;
            }
            uusi[j] = alkiot[i];
            j++;
        }
        muutettu=true;
        this.alkiot = uusi;
    }
    
    
    /**
     * poistetaan tyhjät liimat
     */
    public void poistaTurhat() {
        Liima[] uusi = new Liima[alkiot.length];
        int j = 0;
        for (int i = 0; i < uusi.length; i++) {
            if (alkiot[i] == null) continue;
            if (alkiot[i].getMaara().equals("") || alkiot[i].getMaara().equals("Määrä")) {
                lkm--;
                continue;
            }
            uusi[j] = alkiot[i];
            j++;
        }
        this.alkiot = uusi;
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Liimat liimat = new Liimat(max);
        Liima eka = new Liima(1,1,"500g");
        Liima toka = new Liima(1,2, "3");
        Liima kolmas = new Liima(2,3, "10 kpl");
        
        eka.rekisteroi();
        toka.rekisteroi();
        kolmas.rekisteroi();
        
        try {
            liimat.lisaaLiima(eka);
            liimat.lisaaLiima(toka);
            liimat.lisaaLiima(kolmas);
            liimat.lisaaLiima(toka);
            liimat.lisaaLiima(kolmas);
            liimat.lisaaLiima(eka);
            liimat.lisaaLiima(eka);
            liimat.lisaaLiima(eka);
            liimat.lisaaLiima(eka);

        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }
        
        liimat.poistaLiimat(eka.getRuokaId());
        liimat.tallenna();
        for (int i = 0; i<liimat.getLkm(); i++) {
            System.out.println(liimat.annaLiima(i).toString());
        }
        ArrayList<String> t = liimat.annaLiimaMaara(2);
        
        System.out.println(t.toString());
    }
    
    
    /** Asetaa staattisen muuttujan, käytetään perjaatteessa vain testeille
     * @param polku polku jonka päässä on tiedostot
     */
    public static void setTiedostoSijainti(String polku) {
        tiedostoSijainti = polku;
    }
    
    
    /**
     * Tämä voi asettaa lkm, tehty testejä varten
     * @param nro numero joksika halutaan lukumäärä
     */
    public static void setLkm(int nro) {
        lkm=nro;
    }
}
