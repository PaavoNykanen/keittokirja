package fxKirja;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import keittokirja.Aine;
import keittokirja.Keittokirja;
import keittokirja.Liima;
import keittokirja.Resepti;
import keittokirja.SailoException;

/**
 * @author paavo
 * @author karri
 * @version 23.4.2020
 * Controlleri muokkausikkunalle. Tämä kysyy käyttäjältä muutettavan tai uuden reseptin tietoja ja muuttelee niitä
 * 
 * 
 * Kun painaa uusi resepti ja peruuta ja uudelleen uusi resepti ja peruuta niin tulee erroria ja ohjelma menee rikki
 * TODO joskus:^^
 */

public class MuokkausikkunaController implements ModalControllerInterface<Resepti>, Initializable{
    private Keittokirja keittokirja;
    private Resepti reseptiKohdalla;
    private static int aineita;
    
    @FXML private Button tallennaNappi;
    @FXML private Button peruutaNappi;
    @FXML private Button aineNappi;
    @FXML private TextField nimiKentta;
    @FXML private TextArea ohjeKentta;
    @FXML private TextField aikaKentta;
    @FXML private TextField lahdeKentta;
    @FXML private StringGrid<Aine> aineetKentta;
    
    
    @FXML private void tallennaNappi() {
        Tallenna();
    }
    
    @FXML private void peruutaNappi() {
        Peruuta();
    }
    
    @FXML private void aineNappi() {
        uusiAine();
    }
    
    
    /**
     * Tallentaa reseptin ja sulkee muokkausikkunan
     */
    public void Tallenna() {
        tallennaResepti();
        tallennaAineet();
        keittokirja.poistaTurhat();
        if (reseptiKohdalla.getNimi() == null) {
            Dialogs.showMessageDialog("Nimi ei voi olla tyhjä");
            return;
        }
        keittokirja.tallenna();
        ModalController.closeStage(peruutaNappi);    
    }
    
    
    /**
     * Sulkee ikkunan tallentamatta
     */
    public void Peruuta() {
        ModalController.closeStage(peruutaNappi);
    }
    
    
    /**
     * @param modalitystage mille ollaan modaalisia, pitäisi olla null aina eli pääohjelmalle
     * @param resepti keittokirja, jonka reseptiä muokataan joka kirjoitetaan muokkausikkunaan
     * @param keittokirja keittokirja
     * @return palautetaan resepti
     */
    public static Resepti muokkaaResepti(Stage modalitystage, Resepti resepti, Keittokirja keittokirja) {
        return ModalController.<Resepti, MuokkausikkunaController>showModal(MuokkausikkunaController.class.getResource("Muokkausikkuna.fxml"), 
                "Muokkaus", modalitystage, resepti, ctrl -> { ctrl.setKirja(keittokirja);}
                );
    }


    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    
    /**
     * Alustaa tekstikentän ja reseptilistan tyhjaksi.
     * Lisää myös kuuntelijan reseptilistalle.
     */
    private void alusta() {
        nimiKentta.clear();
        ohjeKentta.clear();
        aikaKentta.clear();
        lahdeKentta.clear();
        aineetKentta.clear();
        String[] otsikot = {"Määrät", "Aineet"};
        aineetKentta.initTable(otsikot);
        aineetKentta.setColumnWidth(0, 120);
        aineetKentta.setColumnWidth(1, 300);
        aineetKentta.setEditable(true);
        aineetKentta.setPlaceholder(new Label("Ei aineita vielä!"));
    }
        
        
    /** Ottaa reseptin tiedot kenttiin
     * @param resepti resepti jonka tietoja muutetaan
     */
    public void naytaResepti(Resepti resepti) {
        if (resepti == null) return;
        nimiKentta.setText(resepti.getNimi());
        aikaKentta.setText(resepti.getAika());
        lahdeKentta.setText(resepti.getLahde());
        String ohje = reseptiKohdalla.getOhjeet();
        if (ohje !=null ) {
            String muutettu = ohje.replaceAll("_","\n");
            ohjeKentta.setText(muutettu);
        }
    }
    
    
    /**
     * Laittaa stringGridiin uuden rivin johon voi kirjoittaa aineita
     */
    public void uusiAine() {
        aineetKentta.add("Määrä", "Raaka-aine");
        aineita++;
    }
    
    
    /**
     * @param resepti resepti, jonka tietoja muutetaan
     */
    public void asetaAineet(Resepti resepti) {
        List<Aine> aineet = keittokirja.annaAineet(resepti.getReseptinId());
        
        ArrayList<String> maarat = keittokirja.annaLiimaMaara(resepti.getReseptinId());
        
        for (int i=0; i<maarat.size(); i++) {
            if (maarat.size() == 0)return;
            aineetKentta.add(maarat.get(i), aineet.get(i).getNimi());
            aineita++;
        }
    }
    
    
    /**
     * Ottaa reseptin tiedot kentistä ja asettaa ne reseptille joka palautetaan
     */
    public void tallennaResepti() {
        String nimi = nimiKentta.getText();
        String ohjeet = ohjeKentta.getText();
        String valmistusaika = aikaKentta.getText();
        String lahde = lahdeKentta.getText();
        reseptiKohdalla.asetaTiedot(nimi, ohjeet, valmistusaika, lahde);
    }
    
    
    /**
     * Tallentaa aineet
     */
    public void tallennaAineet() {
        ArrayList<Liima> liimat = keittokirja.annaLiimatIdlla(reseptiKohdalla.getReseptinId());

        Aine aine = new Aine();
        for (int i=0; i<aineita; i++) { 
            if (reseptiKohdalla == null) return;
            if (i>=liimat.size()) { // jos menee enemman kuin mitä reseptillä oli aiemmin liimoja eli näissä luodaan uusia liimoja
                aine = keittokirja.onkoVarattu(aineetKentta.get(i, 1)); //palauttaa aineen, jolla on etsitty nimi, tai null jos ei loydy
                if (aine == null) { //jos aine on null, tehdään uusi aine ja liima 
                    Aine uusi = new Aine(aineetKentta.get(i, 1));
                    uusi.rekisteroi();
                    Liima uusiL = new Liima(reseptiKohdalla.getReseptinId(), uusi.getAineenId(), aineetKentta.get(i, 0));
                    uusiL.rekisteroi();
                    try {
                        keittokirja.lisaaLiima(uusiL);
                        keittokirja.lisaa(uusi);
                    } catch (SailoException e) {
                        System.err.println(e.getMessage());
                    }
                }
                else { //jos aine on jo olemassa
                    Liima uusiL = new Liima(reseptiKohdalla.getReseptinId(), aine.getAineenId(), aineetKentta.get(i, 0));
                    uusiL.rekisteroi();
                    try {
                        keittokirja.lisaaLiima(uusiL);
                    } catch (SailoException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
            else if (liimat.get(i).getMaara() != aineetKentta.get(i, 0)) { // täällä liimojen pitäisi olla valmiina ja niitä muokataan
                aine = keittokirja.onkoVarattu(aineetKentta.get(i, 1));
                if (aine == null) { // sama tarkistus eli onko aine jo vai tuleeko null
                    Aine uusi = new Aine(aineetKentta.get(i, 1));
                    uusi.rekisteroi();
                    liimat.get(i).asetaLiimaTiedot(uusi.getAineenId(), aineetKentta.get(i, 0));
                    try {
                        keittokirja.lisaa(uusi);
                    } catch (SailoException e) {
                        System.err.println(e.getMessage());
                    }
                    continue;
                }
               liimat.get(i).asetaLiimaTiedot(aine.getAineenId(), aineetKentta.get(i, 0));
            }
        }
    }
    
    
    @Override
    public void handleShown() {
        //
    }

    @Override
    public Resepti getResult() {
        return reseptiKohdalla;
    }

    @Override
    public void setDefault(Resepti resepti) {
        reseptiKohdalla = resepti;
        naytaResepti(reseptiKohdalla);
    }

    /**
     * @param kkirja keittokirja-olio
     */
    public void setKirja(Keittokirja kkirja) {
        this.keittokirja = kkirja;
        asetaAineet(reseptiKohdalla);
    }

}
