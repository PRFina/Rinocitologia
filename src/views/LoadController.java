package views;
import com.itextpdf.text.DocumentException;
import javafx.application.Platform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.apache.commons.io.FileUtils;
import rinocitologia.Anamnesi;
import rinocitologia.Patient;
import utility.Sequence;
import utility.Utility;
import utility.DialogHelper;

import javax.swing.filechooser.FileSystemView;

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

    /**
     * Let the medic load a patient in memory (reading from json) by CF (aka Codice Fiscale) and handles its input.
     * @param event
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
                patient = Utility.readJson(cfTxt.getText());
                setPatient(patient);
                System.out.println(patient.toString());

                //CALL INSERT CELLS
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("Home.fxml"));
                try {
                    Loader.load();
                } catch (IOException ex){
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null, ex);
                }
                HomeController controller = Loader.getController();
                controller.setPatient(patient);
                controller.setInfo();


                //Inizio Carica View
                Parent p = Loader.getRoot();
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(p));
                stage.show();

            } else {
                DialogHelper.showAlert(Alert.AlertType.ERROR, "Errore", "Codice Fiscale errato.", "Inserisci un codice fiscale corretto per poter caricare i dati di un paziente.");
            }
        }
    }

    /**
     * Opens a Directory Chooser to let the medic load a patient in memory (reading from json) by selecting his relative folder.
     * @param event
     */
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

            //CALL INSERT CELLS
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("Home.fxml"));
            try {
                Loader.load();
            } catch (IOException ex){
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null, ex);
            }
            HomeController controller = Loader.getController();
            controller.setPatient(patient);
            controller.setInfo();


            //Inizio Carica View
            Parent p = Loader.getRoot();
            Stage newstage = (Stage)((Node)event.getSource()).getScene().getWindow();
            newstage.setScene(new Scene(p));
            newstage.show();
        } else {
            DialogHelper.showAlert(Alert.AlertType.ERROR, "Errore", "Codice Fiscale errato.", "Seleziona una cartella valida per caricare il paziente.");
        }
        //patient.addAllElements();

    }

    @FXML
    private void searchPatient(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Pazienti.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null, ex);
        }

        PazientiController controller = Loader.<PazientiController>getController();
        controller.setPatient(patient);

        //Inizio Carica View
        Parent p = Loader.getRoot();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(p));
        stage.show();


    }

    @FXML
    private void export(ActionEvent event) throws IOException {
        String source = patient.getPath();
        File srcDir = new File(source);

        String destination = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + File.separator + "Desktop";
        File destDir = new File(destination);

        try {
            FileUtils.copyDirectory(srcDir, destDir);
            FileUtils.copyDirectoryToDirectory(srcDir,destDir);
            DialogHelper.showAlert(Alert.AlertType.INFORMATION, "Export", "Export Riuscito", "La cartella del paziente è disponibile sul Desktop");
            System.out.println("Fatto");
        } catch (IOException e) {
            e.printStackTrace();
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

            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("Home.fxml"));
            try {
                Loader.load();
            } catch (IOException ex) {
                Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE, null, ex);
            }
            HomeController controller = Loader.getController();
            controller.setPatient(patient);
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
        controller.setInfo();


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
            Logger.getLogger(LoadController.class.getName()).log(Level.SEVERE,null, ex);
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
