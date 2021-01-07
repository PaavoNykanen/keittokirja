package fxKirja;

import fi.jyu.mit.fxgui.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author paavo
 * @author karri
 * @version 11.3.2020
 *Controlleri pienelle aloitusikkunalle josta vain painetaan aloita
 */

public class AloitusikkunaController implements ModalControllerInterface<String>{
    
    @FXML private Button kokkaamaanNappi;   
    
    
    /**
     * Kun ohjelmassa painetaan kokkaamaan nappia niin tämä aloitusruutu sulkeutuu
     */
    @FXML private void kokkaamaanNappi() {
        sulje();
    }
    
    /**
     * Ohjelman käynnistyessä tämä hyppää esiin ja kun painaa nappia niin tämä katoaa ja pääohjelma aukeaa
     */
    public static void kaynnista() {
        ModalController.showModal(AloitusikkunaController.class.getResource("Aloitusikkuna.fxml"), "Keittokirja", null, null);
    }
    
    /**
     * sulkee nappia painettaessa aloitusruudun
     */
    public void sulje() {
        ModalController.closeStage(kokkaamaanNappi);
    }

    
    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String arg0) {
        // TODO Auto-generated method stub
        
    }
}
