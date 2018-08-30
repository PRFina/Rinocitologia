package views;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.DocumentException;
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
import rinocitologia.*;
import utility.*;

public class AnamnesiController implements Initializable {

    private Patient patient;

    private Anamnesi currentAnamnesi;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private Label patientTxt;


    @FXML
    public void setPatient(Patient patient) {
        this.patient = patient;
        String label = "Paziente: " + patient.getFirstName() + " " + patient.getSurname();
        patientTxt.setText(label);
    }

    @FXML
    private ListView<String> anamnesiListView;


    /**
     * Displays a list of Anamnesi (showing dates of creation).
     * If clicked, calls Questions passing informations about it to edit Anamnesi.
     */
    public void setAnamesiListView(){

        for(Anamnesi el:patient.getAnamnesiList()){
            anamnesiListView.getItems().add(el.getTime());
            System.out.println(el.getTime());
        }

        anamnesiListView.setOnMouseClicked(event -> {
            String selectedItem = anamnesiListView.getSelectionModel().getSelectedItem().toString();
            currentAnamnesi = patient.getAnamnesi(selectedItem);

            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("Questions.fxml"));
            try {
                Loader.load();
            } catch (IOException ex) {
                Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE, null, ex);
            }
            QuestionsController controller = Loader.getController();
            controller.setPatient(patient);

            controller.setCurrentAnamnesi(currentAnamnesi);
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

        });
    }

    /**
     * Calls Question passing a new instance of Anamnesi to create it.
     * @param event
     * @throws IOException
     */
    @FXML
    void addAnam(ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Questions.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        QuestionsController controller = Loader.getController();
        controller.setPatient(patient);
        currentAnamnesi = new Anamnesi();
        /*
        currentAnamnesi.setLacrimazione(true);
        currentAnamnesi.setFotofobia(true);
        */
        controller.setCurrentAnamnesi(currentAnamnesi);
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


    /*
     *
     * START SIDEBAR COMMANDS
     *
     */

    @FXML
    private void anamnesiCaller(ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Anamnesi.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AnamnesiController controller = Loader.getController();
        if (patient.getAnamnesiList() == null) {
            Anamnesi anam = new Anamnesi();
            patient.addAnamnesi(anam);
        }
        controller.setPatient(patient);

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

    @FXML
    private void insertCells(ActionEvent event) throws IOException {
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


    @FXML
    private void revisionCaller(ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Revisione.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        RevisioneController controller = Loader.getController();
        controller.setPatient(patient);
        controller.setTiles();

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


    @FXML
    private void diagnosisCaller(ActionEvent event)  throws IOException{
        if(patient.getAnamnesiList().size()==0){
            DialogHelper.showAlert(Alert.AlertType.WARNING, "Anamnesi assente", "Non è possibile accedere alla schermata Diagnosi senza avere l'anamnesi del paziente", "Verrà reindirizzato per la compilazione della prima anamnesi.");
            addAnam(event);
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
    private void anagraficaCaller(ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Anagrafica.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AnagraficaController controller = Loader.getController();
        controller.setPatient(patient);

        //Inizio Carica View
        Parent p = Loader.getRoot();
        if (patient.getFirstName() != "Anonimo") {
            controller.setFields();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setOnHidden(e -> {
            controller.shutdown();
            Platform.exit();
        });
        stage.setScene(new Scene(p));
        stage.show();
    }

    @FXML
    private void loadCaller(ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Load.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        LoadController controller = Loader.getController();
        controller.setPatient(patient);


        //Inizio Carica View
        Parent p = Loader.getRoot();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(p));
        stage.setOnHidden(e -> {
            controller.shutdown();
            Platform.exit();
        });
        stage.show();
    }

    public void shutdown() {
        // cleanup code here...
        System.out.println("\nSAVING SESSION");
        Utility util = new Utility(patient);
        util.writeLastSession();
        try {
            util.writePdfReport();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }


}

    /*
    public void getInfo(){
        String anamFis = "";
        if (patient.getAnamnesi().getTipoParto() != "")
            anamFis = anamFis + "Tipo di parto: "+patient.getAnamnesi().getTipoParto();
        if (patient.getAnamnesi().getEsecuzioneParto() != "")
            anamFis = anamFis + "\nTipo di esecuzione del parto: "+patient.getAnamnesi().getEsecuzioneParto();
        if (patient.getAnamnesi().getSviluppo() != "")
            anamFis = anamFis + "\nTipo di sviluppo vegetativo e relazionale: "+patient.getAnamnesi().getSviluppo();
        if (patient.getAnamnesi().getScuola() != "")
            anamFis = anamFis + "\nLivello scolastico: "+patient.getAnamnesi().getScuola();
        if (patient.getAnamnesi().getEta() != "")
            anamFis = anamFis + "\nEtà: "+patient.getAnamnesi().getEta();
        if (patient.getAnamnesi().getAttivita() != "")
            anamFis = anamFis + "\nLivello attività fisica: "+patient.getAnamnesi().getAttivita();
        if (patient.getAnamnesi().getAlimentazione() != "")
            anamFis = anamFis + "\nAbitudini alimentari: "+patient.getAnamnesi().getAlimentazione();
        if (patient.getAnamnesi().getRiposo() != "")
            anamFis = anamFis + "\nRitmo sonno/sveglia: "+patient.getAnamnesi().getRiposo();
        if (patient.getAnamnesi().getStress() != "")
            anamFis = anamFis + "\nStress fisico/psicologico accumulato: "+patient.getAnamnesi().getStress();
        if (patient.getAnamnesi().getFumatore() != "")
            anamFis = anamFis + "\nFumatore: "+patient.getAnamnesi().getFumatore();
        if (patient.getAnamnesi().getAlcool() != "")
            anamFis = anamFis + "\nUso di alcolici: "+patient.getAnamnesi().getAlcool();
        if (patient.getAnamnesi().getDroga() != "")
            anamFis = anamFis + "\nUso di droghe: "+patient.getAnamnesi().getDroga();
        if (patient.getAnamnesi().getDroga() != "")
            anamFis = anamFis + "\nAssunzione di caffeina: "+patient.getAnamnesi().getCaffeina();
        if (patient.getAnamnesi().getAllergia() != "")
            anamFis = anamFis + "\nAllergie: "+patient.getAnamnesi().getAllergia();

        textAnamFam.setText(patient.getAnamnesi().getAnamFamiliare());
        textAnamFis.setText(anamFis);
        textAnamPatProssima.setText(patient.getAnamnesi().getAnamProssima());
        textAnamPatRemota.setText(patient.getAnamnesi().getAnamRemota());
    }
    */
