package views;
import com.itextpdf.text.DocumentException;
import javafx.application.Platform;

import java.io.*;
import java.net.URL;
import java.util.Properties;
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
import utility.Sequence;
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
                Properties properties = new Properties();
                try(InputStream iostream = getClass().getClassLoader()
                        .getResourceAsStream("config.properties")){
                    properties.load(iostream);
                }catch (Exception e){
                    e.printStackTrace();
                }


                if(DialogHelper.showConfirmationDialog("ATTENZIONE", "L'operazione potrebbe impiegare vari minuti.", "Sicuro di voler continuare?")){

                    String pythonPath = properties.getProperty("python_interpreter_path");
                    String pythonScripts = "."+ File.separator + properties.getProperty("python_scripts_path");

                    //Start biofilm extraction
                    if(biofilmBox.isSelected()) {
                        ProcessBuilder pbBiofilm = new ProcessBuilder(pythonPath, properties.getProperty("python_biofilm_script"));
                        pbBiofilm.directory(new File(pythonScripts));
                        pbBiofilm.inheritIO();
                        Process pBiofilm = pbBiofilm.start();
                        pBiofilm.waitFor();
                        DialogHelper.showAlert(Alert.AlertType.INFORMATION,"Biofilm Detection", "Biofilm Detection Completata", "Ok per continuare");
                    }

                    // Start cell extraction
                    ProcessBuilder pb = new ProcessBuilder(pythonPath, properties.getProperty("python_extraction_script"));
                    pb.directory(new File(pythonScripts));
                    pb.inheritIO();
                    Process p = pb.start();
                    p.waitFor();

                    //Start cell classification
                    ProcessBuilder pbClass = new ProcessBuilder(pythonPath,properties.getProperty("python_classification_script"));
                    pbClass.directory(new File(pythonScripts));
                    pbClass.inheritIO();
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


            }catch(Exception e){e.printStackTrace();}
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
    private void newPatient(ActionEvent event) throws IOException {
        if(DialogHelper.showConfirmationDialog("Confermare creazione nuovo paziente", "Sicuro di voler creare un nuovo paziente?", "Verrai portato alla schermata di classificazione cellule")){
            Utility util = new Utility(patient);
            //util.writeLastSession();
            try {
                util.writePdfReport();
                util.writeJson();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
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
            Utility newPatient = new Utility(patient);
            this.setPatient(patient);
        }

    }


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
