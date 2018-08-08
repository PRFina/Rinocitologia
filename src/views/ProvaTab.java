package views;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import rinocitologia.*;
import utility.Sequence;
import filemngr.*;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oXCToo
 */
public class ProvaTab extends Application {
    private Patient patient;
    private RevisioneController controller;

    @Override
    public void start(Stage stage) throws Exception{

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Revisione.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null, ex);
        }

        controller = Loader.<RevisioneController>getController();
        patient = new Patient();
        controller.setPatient(patient);
        controller.setTiles();


        Parent p = Loader.getRoot();
        stage.setScene(new Scene(p));
        stage.setOnHidden(e -> {
            controller.shutdown();
            Platform.exit();
        });
        stage.show();
    }

    @Override
    public void stop(){
        System.out.println("Stage is closing");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
