package views;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.DocumentException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.converter.DateTimeStringConverter;
import org.controlsfx.control.textfield.TextFields;
import rinocitologia.Anamnesi;
import rinocitologia.Patient;
import utility.*;

//http://code.makery.ch/blog/javafx-dialogs-official/

public class AnagraficaController implements Initializable {

    private Patient patient;

    @FXML
    private ComboBox comuneNascitaBox, comuneResidenzaBox;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cfTxt.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));

        success.setVisible(false);
        ObservableList<String> observableListOfSomething = null;
        try {
            observableListOfSomething = FXCollections.observableArrayList(Utility.comuni());
        } catch (IOException e) {
            e.printStackTrace();
        }
        comuneNascitaBox.setItems(observableListOfSomething);
        comuneNascitaBox.setEditable(true);
        TextFields.bindAutoCompletion(comuneNascitaBox.getEditor(), comuneNascitaBox.getItems());

        comuneResidenzaBox.setItems(observableListOfSomething);
        comuneResidenzaBox.setEditable(true);
        TextFields.bindAutoCompletion(comuneResidenzaBox.getEditor(), comuneResidenzaBox.getItems());
    }




    public void setFields(){
        if (patient.getFirstName() != "Anonimo"){
            nameTxt.setText(patient.getFirstName());
            surnameTxt.setText(patient.getSurname());
        }
            if(patient.getCf()!= null)
                cfTxt.setText(patient.getCf().getCF());
            if(patient.getComuneNascita() != null)
                comuneNascitaBox.getSelectionModel().select(patient.getComuneNascita());
            if(patient.getComuneResidenza() != null)
                comuneResidenzaBox.getSelectionModel().select(patient.getComuneResidenza());


    }



    @FXML
    private void webcam(ActionEvent event) throws IOException {
        //ProcessBuilder pb = new ProcessBuilder("/Users/chiccolacriola/anaconda3/bin/python.app","webcam.py");
        ProcessBuilder pb = new ProcessBuilder("python","webcam.py");

        Process p = null;
        try {
            p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
            //cfTxt.setText(line);
            if(!line.equals("[]")){
                if(line.equals("Codice Fiscale non trovato")){
                    DialogHelper.showAlert(Alert.AlertType.WARNING, "Riprovare", "Codice Fiscale non rinvenuto", "Riprovare con una nuova foto. Provare a mettere meglio a fuoco il codice a barre.");
                } else {
                    if (DialogHelper.showConfirmationDialog("Codice Fiscale rinvenuto", line, "Confermare?") == true) {
                            /*
                            CodiceFiscale cf = new CodiceFiscale(cfTxt.getText());
                            patient.setCf(cf);
                            System.out.println(patient.toString());
                            patient.rename();
                            patient.setNewPath();
                            */
                        cfTxt.setText(line);
                    }
                }
            }
        }
    }

    final FileChooser fileChooser = new FileChooser();

    @FXML
    private void foto(ActionEvent event){
        try{
            Stage stage = new Stage();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                patient.setPathCFPhoto(file.getAbsolutePath());
            }
            //ProcessBuilder pb = new ProcessBuilder("/Users/chiccolacriola/anaconda3/bin/python.app","foto.py");
            ProcessBuilder pb = new ProcessBuilder("python","foto.py");
            Process p = pb.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                //cfTxt.setText(line);
                if(line.equals("Codice Fiscale non trovato")){
                    DialogHelper.showAlert(Alert.AlertType.WARNING, "Riprovare", "Codice Fiscale non rinvenuto", "Riprovare con una nuova foto. Provare a mettere meglio a fuoco il codice a barre.");
                } else {
                    if(DialogHelper.showConfirmationDialog("Codice Fiscale rinvenuto", line, "Confermare?") == true){
                        /*
                        CodiceFiscale cf = new CodiceFiscale(cfTxt.getText());
                        patient.setCf(cf);
                        System.out.println(patient.toString());
                        patient.rename();
                        patient.setNewPath();
                        */
                        cfTxt.setText(line);
                    }

                }
            }
            p.waitFor();

        }catch(Exception e){System.out.println(e);}
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
    TextField nameTxt, surnameTxt, cfTxt, dataTxt;

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
                /*
                if (Utility.checkPatientExist(cfTxt.getText())) {
                    DialogHelper.showAlert(Alert.AlertType.ERROR, "Errore", "Il paziente è già stato registrato","Caricare il paziente");
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

                } else {
                */
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Conferma");
                    alert.setHeaderText("Conferma cambiamento dati paziente");
                    alert.setContentText("Vuoi procedere?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        System.out.println(nameTxt.getText() + " " + surnameTxt.getText() + " " + cfTxt.getText());
                        CodiceFiscale cf = new CodiceFiscale(cfTxt.getText());

                        String oldSurname = patient.getSurname();

                        if (!nameTxt.getText().trim().isEmpty())
                            patient.setFirstName(nameTxt.getText());
                        if (!surnameTxt.getText().trim().isEmpty())
                            patient.setSurname(surnameTxt.getText());
                        patient.setCf(cf);
                        if (!comuneNascitaBox.getSelectionModel().isEmpty()) {
                            patient.setComuneNascita(comuneNascitaBox.getValue().toString());
                        } else {
                            patient.setComuneNascita("Non definito");
                        }
                        if (!comuneResidenzaBox.getSelectionModel().isEmpty()) {
                            patient.setComuneResidenza(comuneResidenzaBox.getValue().toString());
                        } else {
                            patient.setComuneResidenza("Non definito");
                        }
                        System.out.println(patient.toString());

                        SqliteHelper helper = new SqliteHelper(patient.getPathData() + File.separator + "test.db");
                        helper.updatePatient(oldSurname, patient.getFirstName(), patient.getSurname(), patient.getCf().getCF(), patient.getCf().getYear(), patient.getCf().getMonth(), patient.getCf().getDay(), patient.getCf().getSex());


                        patient.rename();
                        success.setVisible(true);
                        patientTxt.setText("Paziente: " + patient.getFirstName() + " " + patient.getSurname());
                        patient.setNewPath();
                        Utility util = new Utility(patient);
                        util.writeJson();
                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("Home.fxml"));
                        try {
                            Loader.load();
                        } catch (IOException ex){
                            Logger.getLogger(AnagraficaController.class.getName()).log(Level.SEVERE,null, ex);
                        }
                        HomeController controller = Loader.getController();
                        controller.setPatient(patient);
                        controller.setInfo();


                        //Inizio Carica View
                        Parent p = Loader.getRoot();
                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.setOnHidden(e -> {
                            controller.shutdown();
                            Platform.exit();
                        });
                        stage.setScene(new Scene(p));
                        stage.show();
                    //}
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
        controller.setInfo();


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
        if(patient.getAnamnesiList().size()==0){
            DialogHelper.showAlert(Alert.AlertType.WARNING, "Anamnesi assente", "Non è possibile accedere alla schermata Diagnosi senza avere l'anamnesi del paziente", "Verrà reindirizzato per la compilazione della prima anamnesi.");
            anamnesiCaller(event);
        }else{
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("Diagnosi.fxml"));
            try {
                Loader.load();
            } catch (IOException ex){
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null, ex);
            }
            DiagnosiController controller = Loader.getController();
            controller.setPatient(patient);
            controller.setDati();

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

    @FXML
    private void reportCaller(ActionEvent event)  throws IOException{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Report.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null, ex);
        }
        ReportController controller = Loader.getController();
        controller.setPatient(patient);
        controller.setListView();


        //Inizio Carica View
        Parent p = Loader.getRoot();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(p));
        stage.show();
    }

    public void shutdown() {
        System.out.println("\nSAVING SESSION");
        Utility util = new Utility(this.patient);
        util.writeLastSession();
        util.writeJson();
        try {
            util.writePdfReport();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
}
