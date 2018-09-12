package views;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//import py4j.GatewayServer;
import py4j.GatewayServer;
import rinocitologia.*;
import utility.Sequence;
import utility.SqliteHelper;
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
    GatewayServer server;

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
/*
        //DA COMMENTARE
        Anamnesi anamnesi1 = new Anamnesi();
        anamnesi1.setTime();
        anamnesi1.setLacrimazione(true);
        patient.addAnamnesi(anamnesi1);
        Anamnesi anamnesi2 = new Anamnesi();
        anamnesi2.setTime();
        anamnesi2.setLacrimazione(false);
        patient.addAnamnesi(anamnesi2);
        Anamnesi anamnesi3 = new Anamnesi();
        anamnesi3.setTime();
        anamnesi3.setLacrimazione(false);
        patient.addAnamnesi(anamnesi3);
        //FINE COMMENTO
*/
        //patient = new Patient();
        Utility util = new Utility(patient);
        controller.setPatient(patient);
        System.out.println(patient.getFirstName());
        SqliteHelper helper = new SqliteHelper(patient.getPathData() + File.separator + "test.db");
        SqliteHelper.createNewDatabase();
        SqliteHelper.createNewTable();
        helper.insertPatient(patient.getFirstName(), patient.getSurname());
        helper.selectAllCfNotNull();

        server = new GatewayServer(patient, 25335);
        server.start();



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
        server.shutdown();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
