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
public class KirjaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("KirjaGUIView.fxml"));
            final Pane root = ldr.load();
            //final KirjaGUIController kirjaCtrl = (KirjaGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("kirja.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Kirja");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei k�yt�ss�
     */
    public static void main(String[] args) {
        launch(args);
    }
}