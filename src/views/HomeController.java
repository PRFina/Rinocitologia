package views;
import javafx.application.Platform;

import java.awt.*;
import java.io.File;
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
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import rinocitologia.Patient;
import utility.Utility;

import javax.swing.*;

public class HomeController implements Initializable {


    private Patient patient;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}



    public Patient getPatient(){return patient;}


    @FXML
    private Label pathLbl, patientTxt;

    @FXML
    public void setPatient(Patient patient) {this.patient = patient;
        String label = "Paziente: " + patient.getFirstName() + " " + patient.getSurname();
        patientTxt.setText(label);
    }


    //private Desktop desktop = Desktop.getDesktop();

    //final FileChooser fileChooser = new FileChooser();


    /*

    //FILE CHOOSER -> Selezioni solo file singoli
    //https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm


    @FXML
    private void search(ActionEvent event){
        Stage stage = new Stage();
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            openFile(file);
        }
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                    HomeController.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }

    */

    @FXML
    private void search(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Seleziona cartella");
        final DirectoryChooser directoryChooser =
                new DirectoryChooser();
        final File selectedDirectory =
                directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            selectedDirectory.getAbsolutePath();
            pathLbl.setText(selectedDirectory.getAbsolutePath());
        }

    }



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
        stage.setOnHidden(e -> {
            controller.shutdown();
            Platform.exit();
        });
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
        controller.setTiles();

        //Inizio Carica View
        Parent p = Loader.getRoot();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setOnHidden(e -> {
            controller.shutdown();
            Platform.exit();
        });
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
        stage.setOnHidden(e -> {
            controller.shutdown();
            Platform.exit();
        });
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
        stage.setOnHidden(e -> {
            controller.shutdown();
            Platform.exit();
        });
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
        stage.setOnHidden(e -> {
            controller.shutdown();
            Platform.exit();
        });
        stage.setScene(new Scene(p));
        stage.show();
    }


    @FXML
    private void loadCaller(ActionEvent event)  throws IOException{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Load.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null, ex);
        }
        LoadController controller = Loader.getController();
        controller.setPatient(patient);



        //Inizio Carica View
        Parent p = Loader.getRoot();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(p));
        stage.setOnHidden(e -> {
            controller.shutdown();
            Platform.exit();
        });
        stage.show();
    }

    public void shutdown() {
        System.out.println("\nSAVING SESSION");
        Utility util = new Utility(this.patient);
        util.writeLastSession();
        util.writeJson();

    }
}
