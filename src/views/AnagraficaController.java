package views;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import rinocitologia.Anamnesi;
import rinocitologia.Patient;
import utility.*;

//http://code.makery.ch/blog/javafx-dialogs-official/

public class AnagraficaController implements Initializable {

    private Patient patient;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cfTxt.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));

        success.setVisible(false);
    }

    public void setFields(){
        if (patient.getFirstName() != "Anonimo"){
            nameTxt.setText(patient.getFirstName());
            surnameTxt.setText(patient.getSurname());
            if(patient.getCf()!= null)
                cfTxt.setText(patient.getCf().getCF());
        }
    }

    @FXML
    Label patientTxt;

    @FXML
    public void setPatient(Patient patient) {this.patient = patient;
        String label = "Paziente: " + patient.getFirstName() + " " + patient.getSurname();
        patientTxt.setText(label);
    }

    public Patient getPatient() {return patient;}

    @FXML
    TextField nameTxt, surnameTxt, cfTxt;

    @FXML
    TitledPane success;

    /**
     * Handles personal changes (informations).
     * @param event
     */
    @FXML
    private void insertData(ActionEvent event){
        if(cfTxt.getText().equals("")&&(cfTxt.getText().length() <16 || cfTxt.getText().length()>16)){
            DialogHelper.showAlert(Alert.AlertType.ERROR, "Errore", "Codice Fiscale errato.", "Il codice fiscale è fondamentale qualora il paziente non voglia rimanere anonimo.\nAssicurati di averlo inserito o di averlo inserito correttamente. \nIl codice fiscale deve essere lungo 16 caratteri.");
        } else {
            if(cfTxt.getText().length() == 16) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conferma");
                alert.setHeaderText("Conferma cambiamento dati paziente");
                alert.setContentText("Vuoi procedere?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    System.out.println(nameTxt.getText() + " " + surnameTxt.getText() + " " + cfTxt.getText());
                    CodiceFiscale cf = new CodiceFiscale(cfTxt.getText());
                    if (!nameTxt.getText().trim().isEmpty())
                        patient.setFirstName(nameTxt.getText());
                    if (!surnameTxt.getText().trim().isEmpty())
                        patient.setSurname(surnameTxt.getText());
                    patient.setCf(cf);
                    System.out.println(patient.toString());
                    patient.rename();
                    success.setVisible(true);
                    patientTxt.setText("Paziente: " + patient.getFirstName() + " " + patient.getSurname());
                    patient.setNewPath();
                    Utility util = new Utility(patient);
                    util.writeJson();
                }
            }
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
            Logger.getLogger(AnagraficaController.class.getName()).log(Level.SEVERE,null, ex);
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
            Logger.getLogger(AnagraficaController.class.getName()).log(Level.SEVERE,null, ex);
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

        if(patient.getAnamnesiList().size() == 0){
            Anamnesi anam = new Anamnesi();

            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("Questions.fxml"));
            try {
                Loader.load();
            } catch (IOException ex) {
                Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE, null, ex);
            }
            QuestionsController controller = Loader.getController();
            controller.setPatient(patient);

        /*
        currentAnamnesi.setLacrimazione(true);
        currentAnamnesi.setFotofobia(true);
        */
            controller.setCurrentAnamnesi(anam);
            controller.setInfo();


            //Inizio Carica View
            Parent p = Loader.getRoot();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setOnHidden(e -> {
                controller.shutdown();
                Platform.exit();
            });
            stage.setScene(new Scene(p));
            stage.show();


        } else {
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("Anamnesi.fxml"));
            try {
                Loader.load();
            } catch (IOException ex){
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null, ex);
            }
            AnamnesiController controller = Loader.getController();
            controller.setPatient(patient);

            controller.setAnamesiListView();
            //controller.getInfo();
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

    }

    @FXML
    private void diagnosisCaller(ActionEvent event)  throws IOException{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Diagnosi.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(AnagraficaController.class.getName()).log(Level.SEVERE,null, ex);
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
            Logger.getLogger(AnagraficaController.class.getName()).log(Level.SEVERE,null, ex);
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
            Logger.getLogger(AnagraficaController.class.getName()).log(Level.SEVERE,null, ex);
        }
        LoadController controller = Loader.getController();
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


    public void shutdown() {
        System.out.println("\nSAVING SESSION");
        Utility util = new Utility(this.patient);
        util.writeLastSession();
        util.writeJson();
    }
}
