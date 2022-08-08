package kirja;

import javafx.application.Application;
import javafx.stage.Stage;
import kirjalista.Kirjalista;
import kirjalista.SailoException;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author a3ant
 * @version 15.2.2022
 *
 */
public class KirjaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("KirjaGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final KirjaGUIController kirjaCtrl = (KirjaGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("kirja.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Kirja");
            
          
            Kirjalista kirjalista = new Kirjalista();
            kirjaCtrl.setKirjalista(kirjalista);
            
            primaryStage.setOnCloseRequest((event) -> {
            	try {
					if (kirjaCtrl.voikoSulkea() ) event.consume();
				} catch (SailoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            });
            

            
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
             
    
    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}