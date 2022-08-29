package kirja;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kirjalista.Avainsana;
import kirjalista.Kirja;
import kirjalista.Kirjalista;

public class KirjaMuokkausGUIController implements ModalControllerInterface<Kirja>,Initializable {
		
    @FXML private TextField aloitusPvm;
    @FXML private TextField arvosana;
    @FXML private TextArea avainsanat;
    @FXML private TextField kirjaNimi;
    @FXML private TextField kirjailijaNimi;
    @FXML private TextField tila;
    @FXML private TextField kieli;
    @FXML private TextField vuosi;
    @FXML private TextArea kommentit;
    @FXML private TextField lopetusPvm;
    @FXML private TextField sivujaLuettu;
    
    @FXML private GridPane gridKirja;

	@FXML private void handleApply() {
		System.out.println(avainsanat.getText());
	}
    
    @FXML private void handleCancel() {
        kirjaKohdalla = null;
    }

    @FXML private Kirja handleOK() {
    	kasitteleAvainsanat();
    	return kirjaKohdalla;
    }

	@Override
	public Kirja getResult() {
		return kirjaKohdalla;
	}

	@Override
	public void handleShown() {
        kirjaNimi.requestFocus();
      
	}
	
	public static Kirjalista kirjalista = KirjaGUIController.getKirjalista();
    private TextField edits[];
    private Kirja kirjaKohdalla;
	
	@Override
	public void setDefault(Kirja oletus) {
		kirjaKohdalla = oletus;
		naytaKirja(edits, kirjaKohdalla, avainsanat);
		
	}

	/**
	 * n‰ytt‰‰ kirjan tiedot textfieldeiss‰ 
	 * @param edits taulukko textfieldeist‰
	 * @param kirja kirja jonka tiedot n‰ytet‰‰n
	 * @param avainsanat kirjan avainsanat
	 */
    public static void naytaKirja(TextField[] edits, Kirja kirja, TextArea avainsanat) {
        if (kirja == null) return;
        edits[0].setText(kirja.getNimi());
        edits[1].setText(kirja.getKirjailija());        
        edits[2].setText(Integer.toString(kirja.getArvosana()));
        edits[3].setText(kirja.getAloitusPvm().toString());
        edits[4].setText(kirja.getLopetusPvm().toString());
        edits[5].setText(Integer.toString(kirja.getVuosi()));
        edits[6].setText(kirja.getTila());
        edits[7].setText(kirja.getKieli());
        edits[8].setText(Integer.toString(kirja.getSivut()));

        List<Avainsana> avsanat = kirjalista.annaAvainsanat(kirja.getTunnusNro());
        for (Avainsana avs:avsanat) {
        	avainsanat.appendText(avs.getAvainsana() + ",");
        }
    }
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();  
	}
	
	/**
	 * alustaa tekstikent‰t
	 */
    protected void alusta() {
        edits = new TextField[]{kirjaNimi, kirjailijaNimi, arvosana, aloitusPvm, lopetusPvm, vuosi, tila, kieli, sivujaLuettu};
        int i = 0;
        for (TextField edit : edits) {
        	int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosKirjaan(k, (TextField)(e.getSource())));
        }

    }
	
    /**
     * lis‰‰ kirjalle textareaan kirjoitetut avainsanat 
     */
    private void kasitteleAvainsanat() {
		String s = avainsanat.getText();
		List<String> avsanaLista = Arrays.asList(s.split(","));
		for (String ss : avsanaLista) {
			int k = 0;
			Avainsana avsana = new Avainsana();
			List<Avainsana> avsanat = kirjalista.annaAvainsanat(kirjaKohdalla.getTunnusNro());
	        for (Avainsana avs:avsanat) {
	        	if (avs.getAvainsana().equals(ss)) { 
	        		k = 1;
	        	}
	        }	
	        if (k == 0)  {
	        		System.out.println("uusi avainsana");
	        		avsana.uusiAvainsana(kirjaKohdalla.getTunnusNro(), ss);	
	        		kirjalista.lisaa(avsana);
	        	}	 
	        }
		}

    /**
     * uodaan kirjan kysymisdialogi ja palautetaan kirja muutettuna tai null
     * @param modalityStage mille ollaan modaalisia
     * @param oletus mit‰ dataa n‰ytet‰‰n oletuksena
     * @return null jos painetaan cancel, muuten muutettu kirja
     */
	public static Kirja kysyKirja(Stage modalityStage, Kirja oletus) {
        return ModalController.<Kirja, KirjaMuokkausGUIController>showModal(
        		KirjaMuokkausGUIController.class.getResource("KirjaMuokkausGUIView.fxml"),
                    "Kirja",
                    modalityStage, oletus, null 
                );
    }

    /**
     * tekee muutokset kirjaan teksikenttien muutosten perusteella
     * @param k
     * @param edit
     */
    private void kasitteleMuutosKirjaan(int k, TextField edit) {
    	String s = edit.getText();
    	kirjaKohdalla.aseta(k, s);
    }
    
    
}
