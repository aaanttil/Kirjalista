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
import kirjalista.Kirjat;
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
   
	
	private static Kirjalista kirjalista;

	@FXML private TextField hakuehto;
	@FXML private ComboBoxChooser<String> cbKentat;
	@FXML private ListChooser<Kirja> chooserKirjat;
    
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

	
    @FXML
    private Tab tabKaikki;
    @FXML
    private Tab tabKesken;
    @FXML
    private Tab tabKeskenjat;
    @FXML
    private Tab tabHaluanlukea;
    @FXML
    private Tab tabLuetut;
    @FXML
    private StringGrid<Kirja> tableHaluanlukea;
    @FXML
    private StringGrid<Kirja> tableKaikki;
    @FXML
    private StringGrid<Kirja> tableKesken;
    @FXML
    private StringGrid<Kirja> tableKeskenjat;
    @FXML
    private StringGrid<Kirja> tableLuetut;
	
	
    private Kirja kirjaKohdalla;

	
	@FXML private void handleHakuehto() {
        hae(); 
	}
	
	/**
	 * hakee stringgrideihin hakuehdon t‰ytt‰v‰t kirjat
	 */
    protected void hae() {
    	
    	Kirja haettava = new Kirja();
    	
        String ehto = hakuehto.getText();
        ehto = ehto.toLowerCase();
        System.out.println(ehto);
        tableKaikki.clear();
        tableLuetut.clear();
        tableKesken.clear();
        tableHaluanlukea.clear();
        tableKeskenjat.clear();
        
        if (cbKentat.getSelectionModel().getSelectedIndex() == 0) {
        	for (int i = 0; i < kirjalista.getKirjoja(); i++) {
        		haettava = kirjalista.annaKirja(i);
        		if (haettava.getNimi().toLowerCase().contains(ehto)) {
        			tableKaikki.add(haettava, haettava.getNimi(), haettava.getKirjailija(), Integer.toString(haettava.getVuosi()), haettava.getTila(), haettava.getKieli(), Integer.toString(haettava.getSivut()), Integer.toString(haettava.getArvosana()), haettava.getAloitusPvm().toString(), haettava.getLopetusPvm().toString());
        			muutTabit(haettava, haettava.getTila());
        		}
        	}
        }
        if (cbKentat.getSelectionModel().getSelectedIndex() == 1) {
        	for (int i = 0; i < kirjalista.getKirjoja(); i++) {
        		haettava = kirjalista.annaKirja(i);
        		if (haettava.getKirjailija().toLowerCase().contains(ehto)) {
        			tableKaikki.add(haettava, haettava.getNimi(), haettava.getKirjailija(), Integer.toString(haettava.getVuosi()), haettava.getTila(), haettava.getKieli(), Integer.toString(haettava.getSivut()), Integer.toString(haettava.getArvosana()), haettava.getAloitusPvm().toString(), haettava.getLopetusPvm().toString());
        			muutTabit(haettava, haettava.getTila());
        		} else System.out.println(haettava.getNimi());
        	}
        }

       
        if (cbKentat.getSelectionModel().getSelectedIndex() == 2) {
        	for (int i = 0; i < kirjalista.getKirjoja(); i++) {
        		haettava = kirjalista.annaKirja(i);
        		if (String.valueOf(haettava.getVuosi()).contains(ehto)) {
        			tableKaikki.add(haettava, haettava.getNimi(), haettava.getKirjailija(), Integer.toString(haettava.getVuosi()), haettava.getTila(), haettava.getKieli(), Integer.toString(haettava.getSivut()), Integer.toString(haettava.getArvosana()), haettava.getAloitusPvm().toString(), haettava.getLopetusPvm().toString()); 
        			muutTabit(haettava, haettava.getTila());
        		}
        	}
        }
        
        if (cbKentat.getSelectionModel().getSelectedIndex() == 3) {	
        	for (int i = 0; i < kirjalista.getKirjoja(); i++) {
        		haettava = kirjalista.annaKirja(i);
        		List<Avainsana> avsanat = kirjalista.annaAvainsanat(haettava.getTunnusNro());
        		for (Avainsana avs:avsanat) {
        			if (avs.getAvainsana().toLowerCase().contains(ehto)) {
        				tableKaikki.add(haettava, haettava.getNimi(), haettava.getKirjailija(), Integer.toString(haettava.getVuosi()), haettava.getTila(), haettava.getKieli(), Integer.toString(haettava.getSivut()), Integer.toString(haettava.getArvosana()), haettava.getAloitusPvm().toString(), haettava.getLopetusPvm().toString());
        				muutTabit(haettava, haettava.getTila());
        				break;
        			}
        		}
        	}
        }   
    }


	
   
	@FXML private void handleTallenna() {
        tallenna();
	}
	
	@FXML private void handlePoistaAVS() {
		Avainsana avs = new Avainsana();
		avs = listAvainsanat.getSelectedObject();
        kirjalista.poista(avs);
        naytaAvainsana();     
	}
	
	@FXML private void handleLueTiedosto() {
		lueTiedosto();
	}
	
	
	@FXML private void handleLopeta() {
		tallenna();
		Platform.exit();
	}
	
	
	@FXML private void handleMuokkaaKirja() {
		muokkaa();
	}
	

	@FXML private void handleLisaaKirja() {
        lisaaKirja();
	}

	
	@FXML private void handlePoistaKirja() {
		Kirja poistettava = tableKaikki.getObject();
		poistaKirja(poistettava);
	}
	                   
	@FXML private void handleLisaaAvainsana() {
		uusiAvainsana();
	}
	

	/**
	 * avaa uuden muokkausikkunan, ja samalla luo uuden kirjaolion
	 */
    protected void lisaaKirja() {
        try {
            Kirja uusi = new Kirja();
            uusi = KirjaMuokkausGUIController.kysyKirja(null, uusi); 
            if ( uusi == null ) return; 
            uusi.rekisteroi(); 
            kirjalista.lisaa(uusi);
            hae();
            
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
    }
	
	/**
	 * k‰sittelee kirjan muokkaamisen
	 */
	private void muokkaa() {
		kirjaKohdalla = tableKaikki.getObject();
		try {
			Kirja kirja;
			kirja = KirjaMuokkausGUIController.kysyKirja(null, kirjaKohdalla.clone());
			if (kirja == null) return;
			kirjalista.korvaaTaiLisaa(kirja);
			hae();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		} catch (SailoException e) {
			 Dialogs.showMessageDialog(e.getMessage()); 
		}
	}
	
	/**
	 * tallentaa kirjalistan kirjat ja avainsanat 
	 * @return
	 */
	private String tallenna() {
		try {
			kirjalista.tallenna();
			return null;
		}	catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
		}	
	}
	
	/**
	 * lukee kirjat ja avainsanat tiedostoista
	 * @return null jos onnistuu, muuten virhe
	 */
	private String lueTiedosto() {
		try {
			tableKaikki.clear();
			tableLuetut.clear();
			tableKesken.clear();
			tableKeskenjat.clear();
			tableHaluanlukea.clear();
			kirjalista.lueTiedostosta();
			Kirja uusi = new Kirja();
			System.out.println("ok");
			for(int i = 0; i < kirjalista.getKirjoja(); i++) {
				uusi = kirjalista.annaKirja(i);
		        tableKaikki.add(uusi, uusi.getNimi(), uusi.getKirjailija(), Integer.toString(uusi.getVuosi()), uusi.getTila(), uusi.getKieli(), Integer.toString(uusi.getSivut()), Integer.toString(uusi.getArvosana()), uusi.getAloitusPvm().toString(), uusi.getLopetusPvm().toString()); 
		        String tila = uusi.getTila();
		        muutTabit(uusi, tila);
		        System.out.println(i);
			}
			return null;
		} catch (SailoException e) {
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
		}
		
	}
	
	/**
	 * apuohjelma joka lis‰‰ kirjat muissa tabeiss‰ olevii stringgrideihin tilan perusteella
	 * @param uusi lis‰tt‰v‰ kirja
	 * @param tila attribuutti, jonka perusteella lis‰t‰‰n tabeissa oleviin stringgrideihin
	 */
	private void muutTabit(Kirja uusi, String tila) {
        if (tila.equals("Luettu")) {
        	tableLuetut.add(uusi, uusi.getNimi(), uusi.getKirjailija(), Integer.toString(uusi.getVuosi()), uusi.getTila(), uusi.getKieli(), Integer.toString(uusi.getSivut()), Integer.toString(uusi.getArvosana()), uusi.getAloitusPvm().toString(), uusi.getLopetusPvm().toString());      
        }
        if (tila.equals("Kesken")) {
        	tableKesken.add(uusi, uusi.getNimi(), uusi.getKirjailija(), Integer.toString(uusi.getVuosi()), uusi.getTila(), uusi.getKieli(), Integer.toString(uusi.getSivut()), Integer.toString(uusi.getArvosana()), uusi.getAloitusPvm().toString(), uusi.getLopetusPvm().toString());      
        }
        if (tila.equals("Haluan lukea")) {
        	tableHaluanlukea.add(uusi, uusi.getNimi(), uusi.getKirjailija(), Integer.toString(uusi.getVuosi()), uusi.getTila(), uusi.getKieli(), Integer.toString(uusi.getSivut()), Integer.toString(uusi.getArvosana()), uusi.getAloitusPvm().toString(), uusi.getLopetusPvm().toString());      
        }
        if (tila.equals("Keskeytetty")) {
        	tableKeskenjat.add(uusi, uusi.getNimi(), uusi.getKirjailija(), Integer.toString(uusi.getVuosi()), uusi.getTila(), uusi.getKieli(), Integer.toString(uusi.getSivut()), Integer.toString(uusi.getArvosana()), uusi.getAloitusPvm().toString(), uusi.getLopetusPvm().toString());      
        }
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
        
        tableKaikki.add(uusi, uusi.getNimi(), uusi.getKirjailija(), Integer.toString(uusi.getVuosi()), uusi.getTila(), uusi.getKieli(), Integer.toString(uusi.getSivut()), Integer.toString(uusi.getArvosana()), uusi.getAloitusPvm().toString(), uusi.getLopetusPvm().toString());      
        muutTabit(uusi, uusi.getTila());
    }
   
    /**
     * poistaa kirjan
     * @param poistettava
     */
    public void poistaKirja(Kirja poistettava) {
    	if (poistettava == null) return;
    	kirjalista.poista(poistettava);
    	tableKaikki.clear();
		tableLuetut.clear();
		tableKesken.clear();
		tableKeskenjat.clear();
		tableHaluanlukea.clear();
    	Kirja kirja = new Kirja();
    	for(int i = 0; i < kirjalista.getKirjoja(); i++) {
    		kirja = kirjalista.annaKirja(i);
    		tableKaikki.add(kirja, kirja.getNimi(), kirja.getKirjailija(), Integer.toString(kirja.getVuosi()), kirja.getTila(), kirja.getKieli(), Integer.toString(kirja.getSivut()), Integer.toString(kirja.getArvosana()), kirja.getAloitusPvm().toString(), kirja.getLopetusPvm().toString());
    		muutTabit(kirja, kirja.getTila());
    	}
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
        try {
    	Kirja uusi = tableKaikki.getObject();
		List<Avainsana> avsanat = kirjalista.annaAvainsanat(uusi.getTunnusNro());
        for (Avainsana avs:avsanat) {
        	listAvainsanat.add(avs.getAvainsana(), avs);
        }
        } catch (Exception e) {
        }
	}

    /**
     * lis‰‰ stringgridist‰ valitulle kirjalle avainsanan
     */
    public void uusiAvainsana() { 
       	Avainsana avs = new Avainsana();
        Kirja uusi = tableKaikki.getObject();
    	avs.rekisteroi();     
    	int tunnus = uusi.getTunnusNro();
    	System.out.println(tunnus);
    	avs.vastaaJotain(tunnus);
    	kirjalista.lisaa(avs);
        naytaAvainsana();
    }


	public boolean avaa() {
		// TODO Auto-generated method stub
		return false;
	}


	public void lueTiedosto(String string) {
		// TODO Auto-generated method stub
		
	}
	
	public static Kirjalista getKirjalista() {
		return kirjalista;
	}
}


