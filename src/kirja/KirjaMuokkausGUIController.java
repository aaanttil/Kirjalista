package kirja;

import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class KirjaMuokkausGUIController implements ModalControllerInterface<String> {

    @FXML
    private TextField aloitusPvm;

    @FXML
    private TextField arvosana;

    @FXML
    private TextArea avainsanat;

    @FXML
    private GridPane gridKirja;

    @FXML
    private TextField kirjaNimi;

    @FXML
    private TextField kirjailijaNimi;

    @FXML
    private TextArea kommentit;

    @FXML
    private TextField lopetusPvm;

    @FXML
    private TextField sivujaLuettu;

    @FXML
    private TextField vuosi;

	@FXML private void handleApply() {
		System.out.println(avainsanat.getText());
	}
    
    @FXML
    void handleDefaultCancel(ActionEvent event) {

    }

    @FXML
    void handleDefaultOK(ActionEvent event) {

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
