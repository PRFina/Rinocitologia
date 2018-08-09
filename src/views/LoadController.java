package views;
import javafx.application.Platform;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import rinocitologia.Patient;
import utility.CodiceFiscale;
import utility.Utility;
import utility.DialogHelper;

import javax.swing.*;

public class LoadController implements Initializable {


    private Patient patient;

    @FXML
    TextField cfTxt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cfTxt.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
    }

    @FXML
    public void setPatient(Patient patient) {this.patient = patient;
        String label = "Paziente: " + patient.getFirstName() + " " + patient.getSurname();
        patientTxt.setText(label);
    }

    public Patient getPatient(){return patient;}


    @FXML
    private Label patientTxt;



    /*

    //FILE CHOOSER -> Selezioni solo file singoli
    //https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm

    private Desktop desktop = Desktop.getDesktop();

    final FileChooser fileChooser = new FileChooser();

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
    private void searchByCF(ActionEvent event) {
        if(cfTxt.getText().equals("")&&(cfTxt.getText().length() <16 || cfTxt.getText().length()>16)){
            DialogHelper.showAlert(Alert.AlertType.ERROR, "Errore", "Codice Fiscale errato.", "Inserisci un codice fiscale corretto per poter caricare i dati di un paziente.");
        } else {
            String target = patient.getPathData() + File.separator + cfTxt.getText();
            Path path = Paths.get(target);
            if(cfTxt.getText().length() == 16 && Files.exists(path)) {
                DialogHelper.showAlert(Alert.AlertType.INFORMATION, "Successo", "Successo.", "Paziente caricato con successo.");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.showAndWait();
                patient = Utility.readJson(cfTxt.getText());
                setPatient(patient);
                System.out.println(patient.toString());

            } else {
                DialogHelper.showAlert(Alert.AlertType.ERROR, "Errore", "Codice Fiscale errato.", "Inserisci un codice fiscale corretto per poter caricare i dati di un paziente.");
            }
        }
    }

    @FXML
    private void searchByFolder(ActionEvent event){
        Stage stage = new Stage();
        stage.setTitle("Seleziona cartella");
        String dir = null;
        final DirectoryChooser directoryChooser =
                new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(patient.getPathData()));
        final File selectedDirectory =
                directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            dir = selectedDirectory.getAbsolutePath();
        }
        String loadPath = dir + File.separator + "reports" + File.separator + "report.json";
        Path path = Paths.get(loadPath);
        if(Files.exists(path)){
            patient = Utility.readJson(path.toFile());
            setPatient(patient);
            System.out.println(patient.toString());
            DialogHelper.showAlert(Alert.AlertType.INFORMATION, "Caricamento Riuscito", "Caricamento riuscito.", "I dati del paziente sono stati caricati correttamente.");
        } else {
            DialogHelper.showAlert(Alert.AlertType.ERROR, "Errore", "Codice Fiscale errato.", "Seleziona una cartella valida per caricare il paziente.");
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
        controller.getInfo();
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
        System.out.println("\nSAVING SESSION");
        Utility util = new Utility(this.patient);
        util.writeLastSession();
        util.writeJson();

    }
}