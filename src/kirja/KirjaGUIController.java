package kirja;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author a3ant
 * @version 15.2.2022
 *
 */
public class KirjaGUIController {

	@FXML private TextField hakuehto;
	@FXML private ComboBoxChooser<String> cbKentat;
	@FXML private Label labelVirhe;
	
	@FXML private void handleHakuehto() {
		String hakukentta = cbKentat.getSelectedText();
		String ehto = hakuehto.getText();
		if ( ehto.isEmpty() )
			naytaVirhe(null);
		else
			naytaVirhe("Ei osata vielä hakea " + hakukentta + ": " + ehto);
	}
	   
	@FXML private void handleTallenna() {
        tallenna();
	}
	
	@FXML private void handleLopeta() {
		tallenna();
		Platform.exit();
	}
	
	
	@FXML private void handleMuokkaaKirja() {
		ModalController.showModal(KirjaGUIController.class.getResource("KirjaMuokkausGUIView.fxml"), "Kirja", null, "");
	}
	
	@FXML private void handleLisaaKirja() {
        ModalController.showModal(KirjaGUIController.class.getResource("KirjaMuokkausGUIView.fxml"), "Kirja", null, "");
	}
	
	@FXML private void handlePoistaKirja() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa kirjaa");
	}
	
	private void tallenna() {
		Dialogs.showMessageDialog("Ei toimi vielä :(");
	}
	
	public void initialize(URL url, ResourceBundle bundle) {
		 
	}
	 

	private void naytaVirhe(String virhe) {	        
	    if ( virhe == null || virhe.isEmpty() ) {
	         labelVirhe.setText("");
	         labelVirhe.getStyleClass().removeAll("virhe");
	         return;
	    }
	          labelVirhe.setText(virhe);
	          labelVirhe.getStyleClass().add("virhe");
    }
		       
	 
	
	
	public boolean voikoSulkea() {
		 tallenna();
		 return true;
	 }
	 
	 
}
