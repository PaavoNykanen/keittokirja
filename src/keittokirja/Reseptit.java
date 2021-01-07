package keittokirja;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import keittokirja.Resepti;

/**
 * @author paavo
 * @author karri
 * @version 17.3.2020
 * @version 23.4.2020
 * 
 * Luokka joka kontrolloi reseptejä, sisältää resepteille tietorakenteen (taulukkomainen)
 * Osaa tallentaa ja lukea reseptit.dat tiedostoa.
 */
public class Reseptit {
    
    private static int MAX_LUKU = 5;
    private static int lkm = 0;
    private Resepti[] alkiot = new Resepti[MAX_LUKU];
    private static String tiedostoSijainti = "datat/reseptit.dat";
    private static boolean muutettu = false;
    
    
    /**
     * Constructor
     */
    public Reseptit() {
        for (int i = 0; i<MAX_LUKU; i++) {
            alkiot[i] = new Resepti();
        }
    }
    

    /**
     * Tallentaa omaan tiedostoonsa reseptien tiedot
     * @example
     * <pre name="test">
     *  Reseptit reseptit1 = new Reseptit();
     *  Resepti pyttipannu1 = new Resepti();
     *  pyttipannu1.rekisteroi();
     *  pyttipannu1.asetaTiedot("Lasagne", "Paista jliha yms", "1h", "karri");
     *  try {
     *       reseptit1.lisaaResepti(pyttipannu1);
     *  } catch (keittokirja.SailoException e) {
     *       e.printStackTrace();
     *  } 
     *  Reseptit.setTiedostoSijainti("tiedostoTestit/reseptiTesti.dat");
     *  reseptit1.tallenna();
     *  reseptit1.lueTiedosto();
     *  reseptit1.anna(0).getReseptinId() === 1; //nämä testaa että vaikka loimme resepteja vain yhden, niin tallenna tallenti ja sen ja lueTiedosto 
     *  reseptit1.anna(1).getReseptinId() === 1; //loi uuden reseptin joka on siihen identtinen
     *  reseptit1.anna(2).getReseptinId() === 1; #THROWS IndexOutOfBoundsException //tämä menee yli indexin koska vain kaksi reseptiä
     *  
     * Reseptit.setLkm(0);
     * </pre>
     *///TODO: Toteuta niin, että on bool muuttuja, joka on false aluksi, mutta jos jotain muutetaan ohjelman sisällä, bool muuttuu true
    public void tallenna() {
        if (!muutettu) return;
        File tied = new File(tiedostoSijainti);
        try (PrintStream file = new PrintStream((tied.getCanonicalPath()))) {
            for (int i = 0; i< this.getLkm(); i++) {
                Resepti resepti = anna(i);
                file.println(resepti.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        muutettu=false;
    }
    
    
    /**
     * Lukee reseptit.dat tiedoston ja ottaa sieltä reseptin kaikki asiat
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
                Resepti resepti = new Resepti();
                resepti.parse(rivi);
                try {
                    lisaaResepti(resepti);
                } catch (SailoException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Tiedosto ei aukea! "+e.getMessage());
        }
    }
    
    
    /**
     * @param args ei viela
     * 
     */
    public static void main(String[] args) {
        //mainista kaikki sälä otettu pois :)
    }
    
    
    /**
     * @param lisattava resepti joka lisätään
     * @throws SailoException poikkeustilanne liikaa alkioita
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #THROWS IOException
     * #import java.io.IOException;
     * Reseptit.setLkm(0); 
     * Reseptit reseptit = new Reseptit();
     * Resepti pytti = new Resepti(), lasa = new Resepti();
     * reseptit.getLkm() === 0;
     * reseptit.lisaaResepti(pytti); reseptit.getLkm() === 1;
     * reseptit.lisaaResepti(lasa); reseptit.getLkm() === 2;
     * reseptit.lisaaResepti(pytti); reseptit.getLkm() === 3;
     * reseptit.lisaaResepti(pytti); reseptit.getLkm() === 4;
     * reseptit.lisaaResepti(pytti); reseptit.getLkm() === 5;
     * Reseptit.setLkm(0);
     * </pre>
     */
    public void lisaaResepti(Resepti lisattava) throws SailoException {
        if (lkm >= alkiot.length) {
            MAX_LUKU=MAX_LUKU+5;
            Reseptit uusi = new Reseptit();
            for(int i=0; i<alkiot.length; i++) {
                uusi.alkiot[i]=this.alkiot[i];
            }
            this.alkiot = uusi.alkiot;
        }
        alkiot[lkm] = lisattava;
        lkm++;
        muutettu= true;
    }
    
    
    /**
     * @param i indeksi josta resepti otetaan
     * @return palauttaa etsityn reseptin
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Resepti anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
        
    }
    
    
    /**
     * @return palauttaa reseptien lukumaaran
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Tämä voi asettaa lkm, tehty testejä varten
     * @param nro numero joksika halutaan lukumäärä
     */
    public static void setLkm(int nro) {
        lkm=nro;
    }
    
    
    /** Asetaa staattisen muuttujan, käytetään perjaatteessa vain testeille
     * @param polku polku jonka päässä on tiedostot
     */
    public static void setTiedostoSijainti(String polku) {
        tiedostoSijainti = polku;
    }


    /**
     * @param resepti resepti jota muutettu/uusi
     * Resepti katsotaan onko jo olemassa id:n mukaan
     * jos on niin se ylikirjoitetaan, jos ei ole
     * niin luodaan uusi
     */
    public void korvaaTaiLisaa(Resepti resepti) {
        int id = resepti.getReseptinId();
        for (int i = 0; i<lkm; i++) {
            if (alkiot[i].getReseptinId() == id) {
                alkiot[i] = resepti;
                muutettu=true;
                return;
            }
        }
        try {
            lisaaResepti(resepti);
        } catch (SailoException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param reseptiKohdalla poistettava
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #THROWS IOException
     * #import java.io.IOException;
     * Reseptit.setLkm(0); 
     * Reseptit reseptit = new Reseptit();
     * Resepti pytti = new Resepti(), lasa = new Resepti(), keitto = new Resepti();
     * reseptit.getLkm() === 0;
     * reseptit.lisaaResepti(pytti); reseptit.getLkm() === 1;
     * pytti.rekisteroi();
     * reseptit.lisaaResepti(lasa); reseptit.getLkm() === 2;
     * lasa.rekisteroi();
     * reseptit.lisaaResepti(keitto); reseptit.getLkm() === 3;
     * keitto.rekisteroi();
     * reseptit.getLkm() === 3;
     * reseptit.poistaResepti(pytti);
     * reseptit.getLkm() === 2;
     * Reseptit.setLkm(0);
     * </pre>
     */
    public void poistaResepti(Resepti reseptiKohdalla) {
        Resepti[] uusi = new Resepti[alkiot.length-1];
        int j = 0;
        for (int i=0; i<alkiot.length; i++) {
            int id = alkiot[i].getReseptinId();
            if (id == reseptiKohdalla.getReseptinId()) {
                lkm--;
                continue;
            }
            uusi[j] = alkiot[i];
            j++;
        }
        this.alkiot = uusi;
    }
}
