package fxKirja;

import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import keittokirja.Aine;
import keittokirja.Keittokirja;
import keittokirja.Liima;
import keittokirja.Resepti;
import keittokirja.SailoException;



/**
 * @author karri
 * @author paavo
 * @version 17.3.2020
 * @version 23.4.2020
 * 
 * Controlleri pääohjelmalle jossa on kaikki mitä tapahtuu käyttöliittymän inputeista. Tämä käyttää myös muita tietorakenneluokkia, että 
 * saa reseptit ja aineet näkymään käyttöliittymään
 * 
 * Vastaa kaikesta käyttöliittymän asioista
 * 
 * _____________________________________________________________________________________________________________
 * Jos on tehty yli 1 resepti ja kaikki reseptit poistetaan, tulee viimeisen poistamisesta nullpointterException, 
 * ellei välissä ole suljettu sovellusta
 * 
 * TODO joskus:^^
 */
 
public class KirjaGUIController implements Initializable {

    private Keittokirja keittokirja;
    private Resepti reseptiKohdalla;

    @FXML
    private MenuButton hakuehtoDrop;
    @FXML
    private MenuItem hakuRaaka;
    @FXML
    private MenuItem hakuReseptit;
    @FXML
    private TextField hakuPalkki;
    @FXML
    private ListChooser<Resepti> reseptiLista;
    @FXML
    private MenuItem tallennaMenu;
    @FXML
    private MenuItem tulostaMenu;
    @FXML
    private MenuItem lopetaMenu;
    @FXML
    private MenuItem muokkaaMenu;
    @FXML
    private MenuItem poistaMenu;
    @FXML
    private MenuItem apuaMenu;
    @FXML
    private MenuItem tietojaMenu;
    @FXML
    private TextArea ohjeLaatikko; 
    @FXML
    private TextField valmistusAika;
    @FXML
    private TextField lahde;
    @FXML
    private ListChooser<Aine> aineLista;
    @FXML
    private ListChooser<Liima> maaraLista;
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();        
    }
    
    
    /**
     * Avaa alkuun kokkauskysymys dialogin, siinä painetaan vaan kokkaamaan niin lähtee pois
     */
    public void avaa() {
        AloitusikkunaController.kaynnista();
        lueTiedosto();
    }
    
    
    @FXML private void handleTulosta() {
        TulostusController tulostusCtrl = TulostusController.tulosta(null);
        tulostaValitut(tulostusCtrl.getTextArea());
    }


    @FXML private void hakemaan(KeyEvent event) { //KeyEvent
        String str = hakuPalkki.getText() + event.getText();
        if (hakuresepti == true) {
            reseptiHaku(str);
        }
        if (hakuraaka == true) {
            rAineHaku(str);
        }
    }
    

    /**
     * Tämä aukaisee reseptinmuokkausikkunan, aktivoituu kun painetaan yläpalkista muokkaa/lisaa resepti
     */
    @FXML
    void muokkaamaan(MouseEvent event) { //MouseEvent
        if (event.getClickCount() == 2) {
            muokkaa();
        }
    }
    
    
    /**
     * Tämä aukaisee reseptinmuokkausikkunan, aktivoituu kun painetaan yläpalkista muokkaa/lisaa resepti
     */
    @FXML
    void muokkaamaan1() {
        muokkaa();
    }
    
    
    /**
     * Tästä luodaan uusi resepti reseptilistaan
     */
    @FXML
    void lisaaResepti() {
        uusiResepti();
    }


    @FXML
    void poistaResepti() { //ActionEvent
        if (reseptiKohdalla == null) return;
        keittokirja.poista(reseptiKohdalla);
        int index = reseptiLista.getSelectedIndex();
        hae(0);
        reseptiLista.setSelectedIndex(index);
    }

    
    @FXML
    void quit() { //ActionEvent
        if (keittokirja.getReseptitLkm() == 0) Platform.exit();
        else {
            tallenna();
            Platform.exit();
        }
        
    }

    
    @FXML
    void showHelp() { //ActionEvent
        ModalController.showModal(KirjaGUIController.class.getResource("ApuaView.fxml"), "Apua", null, "");
    }

    
    @FXML
    void showInfo() { //ActionEvent
        ModalController.showModal(KirjaGUIController.class.getResource("AboutView.fxml"), "Kirja", null, "");
    }

    
    @FXML
    void tallenna() { //ActionEvent
        keittokirja.poistaTurhat();
        keittokirja.tallenna();
    }


    @FXML
    void vaihdaRaaka() { //ActionEvent
        hakuehtoDrop.setText("Hae raaka-aineella");
        hakuraaka = true;
        hakuresepti = false;
    }
    

    @FXML
    void vaihdaReseptit() { //ActionEvent
        hakuehtoDrop.setText("Hae reseptin nimellä");
        hakuresepti = true;
        hakuraaka = false;
	}
    
    
    /*----------------------------------------------------------------------------------------------------------*/
    
    
    private boolean hakuresepti = false;
    private boolean hakuraaka = false;
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     *///TODO: Toteuta niin, että on bool muuttuja, joka on false aluksi, mutta jos jotain muutetaan ohjelman sisällä, bool muuttuu true
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    
    /**
     * Lukee tiedostoja ja jos niissä on tietoja niin kirjoittelee ne käyttöliittymään
     */
    private void lueTiedosto() {
        keittokirja.lueTiedosto();
        hae(0);
    }
    
    
    /**
     * Avaa muokkausikkunan
     */
    private void muokkaa() {
        if (reseptiKohdalla == null) return;
        try {
            Resepti resepti = new Resepti();
            resepti = MuokkausikkunaController.muokkaaResepti(null, reseptiKohdalla.clone(), keittokirja);
            if (resepti == null) return;
            keittokirja.korvaaTaiLisaa(resepti);
            hae(resepti.getReseptinId());
        } catch (CloneNotSupportedException e) {
            //
        }
    }
    
    
    /**
     * @param keittokirja keittokirja jota käytetään, jos tämän työn tekisi pitemmälle,
     * voisi tässä valita eri keittokirjoista mitä käytetään esim jälkiruuille oma
     * kirja. Sama idea kuin vesan kelmien kerho kaikista kerhoista
     */
    public void setKirja(Keittokirja keittokirja) {
        this.keittokirja = keittokirja;
    }
    
    
    /**
     * Luo uuden reseptin jolle käyttäjä myöhemmin antaa arvot ja lisää sen keittokirjaan
     * Tällä hetkellä annetut arvot menisivät constructorille suoraan. Jos tarvitsee niin voisi tehdä myös aseta arvot aliohjelman.
     */
    private void uusiResepti() {
        try {
            Resepti uusiResepti = new Resepti(); 
            uusiResepti.rekisteroi();
            uusiResepti = MuokkausikkunaController.muokkaaResepti(null, uusiResepti, keittokirja);
            if (uusiResepti.getNimi() == null) return;
            keittokirja.lisaa(uusiResepti);
            hae(uusiResepti.getReseptinId());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelma reseptissä " + e.getMessage());
            return;
        }
    }
    
    
    private void reseptiHaku(String str) {
        reseptiLista.clear();
        
        String reg = ".*" + str.toUpperCase() + ".*";
                
        int index = 0;
        for (int i = 0; i < keittokirja.getReseptitLkm(); i++) {
            Resepti resepti = keittokirja.annaResepti(i);
            if (resepti.getNimi().toUpperCase().matches(reg)) {
                reseptiLista.add(resepti.getNimi(), resepti);
            }
        }
        reseptiLista.setSelectedIndex(index);
        
    }
    
    private void rAineHaku(String str) {
        reseptiLista.clear();
        
        String reg = ".*" + str.toUpperCase() + ".*";
        
        for (int i = 0; i < keittokirja.getReseptitLkm(); i++) {
            Resepti resepti = keittokirja.annaResepti(i);
            List<Aine> aineet = keittokirja.annaAineet(resepti.getReseptinId());
            for (int j = 0; j < aineet.size(); j++) {
                if (aineet.get(j).getNimi().toUpperCase().matches(reg)) {
                    reseptiLista.add(resepti.getNimi(), resepti); 
                    break;
                }
            }
        }
        reseptiLista.setSelectedIndex(0);
    }
    
    
    /**
     * Hakee reseptien tiedot reseptien listaan listchooseriin.
     * Asettaa valituksi myös viimeksi lisätyn reseptin.
     * @param id reseptinId joka halutaan hakea.
     */
    private void hae(int id) {
        int apu = id;
        reseptiLista.clear();
        aineLista.clear();
        maaraLista.clear();
        
        if (id <= 0) {
            Resepti kohdalla = reseptiKohdalla;
            if (kohdalla != null) apu = kohdalla.getReseptinId();
        }
        
        
        int index = 0;
        for (int i = 0; i < keittokirja.getReseptitLkm(); i++) {
            Resepti resepti = keittokirja.annaResepti(i);
            if (resepti.getReseptinId() == apu) index = i;
            reseptiLista.add(resepti.getNimi(), resepti);
        }
        reseptiLista.setSelectedIndex(index);
    }
    
    
    /**
     * Näyttää valitun reseptin tekstikenttään ja aineet ja maarat jotka kuuluvat sille reseptille listoihin. Tyhjentää ensin kaikki tekstikentät
     * ja listat
     */
    private void naytaResepti() {
        reseptiKohdalla = reseptiLista.getSelectedObject();
        if (reseptiKohdalla == null) return;
        
        aineLista.clear();
        ohjeLaatikko.setText("");
        maaraLista.clear();

        tulosta();
    }
    
    
    /**
     * Tulostaa reseptin ja maarat oikeisiin kohtiin.
     * Silmukassa käydään keittokirjan liimat läpi ja katsoo että jos reseptinId on sama kuin liiman ruokaId, niin tulostaa maaran.
     * Sitten tarkistetaan myös että saadaan keittokirjasta aine jonka aineId on sama kuin liiman aineId. Jos on niin ainekkin
     * tulostetaan. Liimatiedostot pitää luoda siten että ei voi tulla tapausta missä tulostuisi pelkkä määrä ilman ainetta.
     */
    private void tulosta() {
        //tässä reseptin tulostus
        String ohje = reseptiKohdalla.getOhjeet();
        String muutettu = ohje.replaceAll("_","\n");
        ohjeLaatikko.setText(muutettu);
        valmistusAika.setText(reseptiKohdalla.getAika());
        lahde.setText(reseptiKohdalla.getLahde());
        
        
        for (int i = 0; i < keittokirja.getLiimatLkm(); i++) {
            Liima liima = keittokirja.annaLiima(i);
            if (reseptiKohdalla.getReseptinId() == liima.getRuokaId()) {
                maaraLista.add(liima.getMaara(), liima);
                Aine aine = keittokirja.annaAine(liima.getAineId());
                if (liima.getAineId() == aine.getAineenId()) {
                   aineLista.add(aine.getNimi(), aine);
                }
            }
        }
    }

    
    /**
     * Alustaa tekstikentän ja reseptilistan tyhjaksi.
     * Lisää myös kuuntelijan reseptilistalle.
     */
    private void alusta() {
        reseptiLista.clear();
        reseptiLista.addSelectionListener(e -> naytaResepti());
        reseptiLista.setPlaceholder(new Label("Ei vielä reseptejä!"));
    }
    
    
    /**
     * Tulostaa listassa olevat reseptit tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValitut(TextArea text) {
        int i = reseptiKohdalla.getReseptinId();
        List<Aine> aineet = keittokirja.annaAineet(i);
        ArrayList<String> maarat = keittokirja.annaLiimaMaara(i);
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Resepti: " + reseptiKohdalla.getNimi() + "\n");
            for (int j = 0; j < aineet.size(); j++) {
                os.println(maarat.get(j) + "  --  " + aineet.get(j).getNimi() + "\n");
            }
            os.println("Ohje: " + reseptiKohdalla.getOhjeet() + "\n");
            os.println("Valmistusaika: " + reseptiKohdalla.getAika() + "\n");
            os.println("Lähde: " + reseptiKohdalla.getLahde() + "\n");
        }
    } 
}

