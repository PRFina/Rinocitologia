package views;
import javafx.application.Platform;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import rinocitologia.Patient;
import utility.Utility;

public class HomeController implements Initializable {


    private Patient patient;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void setPatient(Patient patient) {this.patient = patient;}

    public Patient getPatient(){return patient;}

    public void print(){System.out.println(patient.toString());}

    /*
     *
     * START SIDEBAR COMMANDS
     *
     */

    @FXML
    private void insertCells(ActionEvent event)  throws IOException{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Home.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null, ex);
        }
        HomeController controller = Loader.getController();
        controller.setPatient(patient);


        //Inizio Carica View
        Parent p = Loader.getRoot();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(p));
        stage.show();
    }

    @FXML
    private void revisionCaller(ActionEvent event)  throws IOException{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Revisione.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null, ex);
        }
        RevisioneController controller = Loader.getController();
        controller.setPatient(patient);

        //Inizio Carica View
        Parent p = Loader.getRoot();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(p));
        stage.show();
    }


    @FXML
    private void anamnesiCaller(ActionEvent event)  throws IOException{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Anamnesi.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null, ex);
        }
        AnamnesiController controller = Loader.getController();
        controller.setPatient(patient);
        //Inizio Carica View
        Parent p = Loader.getRoot();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(p));
        stage.show();
    }

    @FXML
    private void diagnosisCaller(ActionEvent event)  throws IOException{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Diagnosi.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null, ex);
        }
        DiagnosiController controller = Loader.getController();
        controller.setPatient(patient);

        //Inizio Carica View
        Parent p = Loader.getRoot();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(p));
        stage.show();
    }

    @FXML
    private void anagraficaCaller(ActionEvent event)  throws IOException{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Anagrafica.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null, ex);
        }
        AnagraficaController controller = Loader.getController();
        controller.setPatient(patient);



        //Inizio Carica View
        Parent p = Loader.getRoot();
        if (patient.getFirstName() != "Anonimo"){
            controller.setFields();
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(p));
        stage.show();
    }

    public void shutdown() {
        System.out.println("SAVING SESSION");
        Utility util = new Utility(patient);
        util.writeLastSession();
    }
}
