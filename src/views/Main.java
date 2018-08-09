package views;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import rinocitologia.*;
import utility.Sequence;
import utility.Utility;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oXCToo
 */
public class Main extends Application {
    private Patient patient;
    private HomeController controller;

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Home.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null, ex);
        }

        controller = Loader.<HomeController>getController();
        Sequence seq = new Sequence();
        int number = 0;
        try {
            File f = new File(seq.getPATH());

            if (f.exists()){
                number = seq.readSeq() + 1;
                seq.writeSeq(number);
            } else {
                seq.writeSeq(number);

            }
            System.out.println(seq.readSeq());
        } catch(Exception e){
            e.printStackTrace();
        }
        patient = new Patient("Anonimo", Integer.toString(number));
        //patient = new Patient();
        Utility util = new Utility(patient);
        controller.setPatient(patient);
        System.out.println(patient.getFirstName());



        Parent p = Loader.getRoot();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("safari.png")));
        stage.setTitle("Cell Explorer");
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
