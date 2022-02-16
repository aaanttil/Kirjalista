package kirja;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author a3ant
 * @version 15.2.2022
 *
 */
public class KirjaMuokkausMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("KirjaMuokkausGUIView.fxml"));
            final Pane root = ldr.load();
            //final KirjaMuokkausGUIController kirjamuokkausCtrl = (KirjaMuokkausGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("kirjamuokkaus.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("kirjaMuokkaus");
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