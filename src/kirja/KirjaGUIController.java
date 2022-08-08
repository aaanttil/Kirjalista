package kirja;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
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
   
	
	private Kirjalista kirjalista;

	@FXML private TextField hakuehto;
	@FXML private ComboBoxChooser<String> cbKentat;
	@FXML private Label labelVirhe;
	@FXML private ListChooser<Kirja> chooserKirjat;

    @FXML private StringGrid<Kirja> tableKirjat;
    
    @FXML private ListChooser<Avainsana> listAvainsanat;
    
    @FXML private TableColumn<Kirja, String> kirjaNimi;
	@FXML private TableColumn<Kirja, String> kirjailijaNimi;
	@FXML private TableColumn<Kirja, Integer> vuosi;
	@FXML private TableColumn<Kirja, Integer> arvosana;
	@FXML private TableColumn<Kirja, String> tilaNimi;
	@FXML private TableColumn<Kirja, Integer> kieli;
	@FXML private TableColumn<Kirja, Integer> aloitusPvm;
	@FXML private TableColumn<Kirja, Integer> lopetusPvm;
	@FXML private TableColumn<Kirja, Integer> sivumaara;

	
	@FXML private void handleHakuehto() {
		String hakukentta = cbKentat.getSelectedText();
		String ehto = hakuehto.getText();
		if ( ehto.isEmpty() )
			naytaVirhe(null);
		else
			naytaVirhe("Ei osata viel‰ hakea " + hakukentta + ": " + ehto);
	}
	
   
	@FXML private void handleTallenna() {
        tallenna();
	}
	
	@FXML private void handleLueTiedosto() {
		lueTiedosto();
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
        Dialogs.showMessageDialog("Ei osata viel‰ poistaa kirjaa");
	}
	                   
	@FXML private void handleLisaaAvainsana() {
		uusiAvainsana();
	}
	
	
	private String tallenna() {
		try {
			kirjalista.tallenna();
			return null;
		}	catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
		}	
	}
	
	private String lueTiedosto() {
		try {
			kirjalista.lueTiedostosta();
			Kirja uusi = new Kirja();
			System.out.println("ok");
			for(int i = 0; i < kirjalista.getKirjoja(); i++) {
				uusi = kirjalista.annaKirja(i);
		        tableKirjat.add(uusi, uusi.getNimi(), uusi.getKirjailija(), Integer.toString(uusi.getVuosi()), uusi.getTila(), uusi.getKieli(), Integer.toString(uusi.getSivut()), Integer.toString(uusi.getArvosana()), uusi.getAloitusPvm().toString(), uusi.getLopetusPvm().toString());      
			}		
			return null;
		} catch (SailoException e) {
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
		}
		
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
	
	public boolean voikoSulkea() throws SailoException {
		 tallenna();
		 return true;
	 }
	 
	 


    @FXML private void handleUusiKirja() {   		
        uusiKirja();
    }

    
    @FXML private void handleUusiAvainsana() {
        uusiAvainsana();  
    }

    
    @FXML void handleNaytaavainsanat() {
		naytaAvainsana();
    }
    
    /////////////////----------------
    

    /**
     * luo uuden kirjan, antaa sen attribuuteille arvot ja lis‰‰ kirjan stringgridiin   
     */
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
        
        tableKirjat.add(uusi, uusi.getNimi(), uusi.getKirjailija(), Integer.toString(uusi.getVuosi()), uusi.getTila(), uusi.getKieli(), Integer.toString(uusi.getSivut()), Integer.toString(uusi.getArvosana()), uusi.getAloitusPvm().toString(), uusi.getLopetusPvm().toString());      
    }
   

    public void initialize(URL url, ResourceBundle bundle) {
    	alusta();
    }

    /**
     * asettaa kirjalistan
     * @param kirjalista
     */
    public void setKirjalista(Kirjalista kirjalista) {
    	this.kirjalista = kirjalista;
    }
    
    protected void alusta() {

    }

    /**
     * n‰ytt‰‰ tableKirjat-stringgridist‰ valitun kirjan avainsanat listchooserissa
     */
    private void naytaAvainsana() {	
    	listAvainsanat.clear();
        Kirja uusi = tableKirjat.getObject();
		List<Avainsana> avsanat = kirjalista.annaAvainsanat(uusi.getTunnusNro());
        for (Avainsana avs:avsanat) {
        	listAvainsanat.add(avs.getAvainsana(), avs);
        }

	}

    /**
     * lis‰‰ stringgridist‰ valitulle kirjalle avainsanan
     */
    public void uusiAvainsana() { 
       	Avainsana avs = new Avainsana();
        Kirja uusi = tableKirjat.getObject();
    	avs.rekisteroi();     
    	int tunnus = uusi.getTunnusNro();
    	System.out.println(tunnus);
    	avs.vastaaJotain(tunnus);
    	kirjalista.lisaa(avs);
        naytaAvainsana();
    } 
}


