package views;
import com.itextpdf.text.DocumentException;
import javafx.application.Platform;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import rinocitologia.Anamnesi;
import rinocitologia.Patient;
import utility.DialogHelper;
import utility.Utility;

public class HomeController implements Initializable {


    private Patient patient;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}



    public Patient getPatient(){return patient;}


    @FXML
    private Label pathLbl, patientTxt;

    @FXML
    private CheckBox biofilmBox;

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

    public void setInfo(){
        if(patient.getPathCampi() != null)
            pathLbl.setText(patient.getPathCampi());
    }

    /**
     * Display Directory Chooser to select the folder containing Cells images (taken with microscope) from medic's pc.
     * @param event
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
            patient.setPathCampi(selectedDirectory.getAbsolutePath());
        }

    }

    @FXML
    private void start(ActionEvent event) {
        if(patient.getPathCampi() != null){
            try{
                //ProcessBuilder pb = new ProcessBuilder("/Users/chiccolacriola/anaconda3/bin/python.app","foto.py");

                if(DialogHelper.showConfirmationDialog("ATTENZIONE", "L'operazione potrebbe impiegare vari minuti.", "Sicuro di voler continuare?")){

                    if(biofilmBox.isSelected() == true) {
                        ProcessBuilder pbBiofilm = new ProcessBuilder("python", "biofilm_detection_java.py");
                        Process pBiofilm = pbBiofilm.start();
                        pBiofilm.waitFor();
                        DialogHelper.showAlert(Alert.AlertType.INFORMATION,"Biofilm Detection", "Biofilm Detection Completata", "Ok per continuare");
                    }

                    ProcessBuilder pb = new ProcessBuilder("python","watershed_extraction.py");
                    Process p = pb.start();
                    p.waitFor();
                    ProcessBuilder pbClass = new ProcessBuilder("python","classificatore.py");
                    Process pClass = pbClass.start();
                    pClass.waitFor();
                    DialogHelper.showAlert(Alert.AlertType.INFORMATION,"Classificazione", "Estrazione e classificazione completate", "Ok per continuare");

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
                    Parent parent = Loader.getRoot();
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setOnHidden(e -> {
                        controller.shutdown();
                        Platform.exit();
                    });
                    stage.setScene(new Scene(parent));
                    stage.show();


                }


            }catch(Exception e){System.out.println(e);}
        } else {
            DialogHelper.showAlert(Alert.AlertType.ERROR, "ERRORE", "Selezionare la cartella contenente i campi", "Occorre selezionare una cartella contenente i campi prelevati e da esaminare.");
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
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null, ex);
        }
        AnagraficaController controller = Loader.getController();
        controller.setPatient(patient);



        //Inizio Carica View
        Parent p = Loader.getRoot();
        //if (patient.getFirstName() != "Anonimo"){
            controller.setFields();
        //}
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
