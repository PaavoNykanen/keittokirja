package fxKirja;
	
import javafx.application.Application;
import javafx.stage.Stage;
import keittokirja.Keittokirja;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * @author karri
 * @author paavo
 * @version 13.1.2020
 * @version 23.4.2020 ei korjauksia :)
 *
 * Pääluokka, josta avataan Käyttöliittymä ja asetetaan keittokirja johon muut luokat liittyvät
 */
public class KirjaMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    final FXMLLoader ldr = new FXMLLoader(getClass().getResource("KirjaGUIView.fxml"));
			final BorderPane root = (BorderPane)ldr.load();
			final KirjaGUIController kirjaController = ldr.getController();
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("kirja.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Keittokirja");
			
            primaryStage.setOnCloseRequest((event) -> {
                if ( !kirjaController.voikoSulkea() ) event.consume();
            });
			
			Keittokirja keittokirja = new Keittokirja();
			kirjaController.setKirja(keittokirja);
			
	        primaryStage.show(); //avaa ohjelman pääsivun
	        kirjaController.avaa(); // avaa alkunäytön pääsivun päälle
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Käynnistää ohjelman
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
