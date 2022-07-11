package kirja;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.List; 

import kirjalista.Kirja;
import kirjalista.Kirjalista;
import kirjalista.Avainsana;
import kirjalista.SailoException;

import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * @author a3ant
 * @version 17.2.2022
 *
 */
public class KirjaGUIController {

	@FXML private TextField hakuehto;
	@FXML private ComboBoxChooser<String> cbKentat;
	@FXML private Label labelVirhe;
	@FXML private ListChooser<Kirja> chooserKirjat;
	    
    @FXML private StringGrid<Kirja> tableKirjat;

	@FXML private TableView<Kirja> tableView;
	@FXML private TableColumn<Kirja, String> kirjaNimi;
	@FXML private TableColumn<Kirja, String> kirjailijaNimi;
	@FXML private TableColumn<Kirja, Integer> vuosi;
	@FXML private TableColumn<Kirja, Integer> arvosana;
	@FXML private TableColumn<Kirja, String> tilaNimi;
	@FXML private TableColumn<Kirja, Integer> aloitusPvm;
	@FXML private TableColumn<Kirja, Integer> lopetusPvm;
	@FXML private TableColumn<Kirja, Integer> sivumaara;


	
	
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
	
	

	private void naytaVirhe(String virhe) {	        
	    if ( virhe == null || virhe.isEmpty() ) {
	         labelVirhe.setText("");
	         labelVirhe.getStyleClass().removeAll("virhe");
	         return;
	    }
	          labelVirhe.setText(virhe);
	          labelVirhe.getStyleClass().add("virhe");
    }
		       
	 
	@FXML private void handleTietoja() {
	   ModalController.showModal(KirjaGUIController.class.getResource("aboutView.fxml"), "Kirja", null, "");
	}
	
	public boolean voikoSulkea() {
		 tallenna();
		 return true;
	 }
	 
	 
    private Kirjalista kirjalista;
    private Kirja kirjaKohdalla;
    private TextArea areaKirja = new TextArea();


    @FXML private void handleUusiKirja() {   		
        uusiKirja();
    }

    
    @FXML private void handleUusiAvainsana() {
        uusiAvainsana();  
    }

    
    public void uusiAvainsana() { 
        if ( kirjaKohdalla == null ) return;  
        Avainsana avs = new Avainsana();  
        avs.rekisteroi();  
        avs.vastaaJotain(kirjaKohdalla.getTunnusNro());  
        kirjalista.lisaa(avs);  
        hae(kirjaKohdalla.getTunnusNro());          
    } 
   
    private void uusiKirja() {
        Kirja uusi = new Kirja();
        uusi.rekisteroi();
        uusi.vastaaValtio();
        try {
            kirjalista.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        tableKirjat.add(uusi, uusi.getNimi(), uusi.getKirjailija(), Integer.toString(uusi.getVuosi()));

        ////hae(uusi.getTunnusNro());
    }
    
    protected void hae(int knro) {

        int index = 0;
        for (int i = 0; i < kirjalista.getKirjoja(); i++) {
            Kirja kirja = kirjalista.annaKirja(i);
            if (kirja.getTunnusNro() == knro) index = i;
            tableKirjat.add(kirja.getNimi());
        }
    }

    public void initialize(URL url, ResourceBundle bundle) {
    	alusta();
    }


// ------------------------- omia    
    
    
    /**
     * 
     * @param kirjalista
     */
    public void setKirjalista(Kirjalista kirjalista) {
    	this.kirjalista = kirjalista;
    }
    
    protected void alusta() {
    	
    }

    

}


