package views;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import rinocitologia.Patient;
import utility.Utility;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionsController  implements Initializable {

    @FXML
    private TextArea textFam;

    @FXML
    private TextArea textPross;

    @FXML
    private TextArea textRem;

    @FXML
    private ComboBox<String> comboBoxTipoParto;

    @FXML
    private ComboBox<String> comboBoxEsecuzioneParto;

    @FXML
    private ComboBox<String> comboBoxSviluppo;

    @FXML
    private ComboBox<String> comboBoxScuola;

    @FXML
    private ComboBox<String> comboBoxAttivita;

    @FXML
    private ComboBox<String> comboBoxAlimentazione;

    @FXML
    private ComboBox<String> comboBoxRiposo;

    @FXML
    private ComboBox<String> comboBoxStress;

    @FXML
    private ComboBox<String> comboBoxDroga;

    @FXML
    private ComboBox<String> comboBoxFumatore;

    @FXML
    private ComboBox<String> comboBoxAlcolici;

    @FXML
    private ComboBox<String> comboBoxCaffeina;

    @FXML
    private TextField textFieldAllergia;

    @FXML
    private TextField textFieldEta;

    private AnamnesiController anamnesiController;

    private Patient patient;

    public void setPatient(Patient patient) {this.patient = patient;}


    /*
     *
     * START SIDEBAR COMMANDS
     *
     */

    @FXML
    private void anamnesiCaller(ActionEvent event)  throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Anamnesi.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE,null, ex);
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
    private void insertCells(ActionEvent event)  throws IOException{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Home.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE,null, ex);
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
            Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE,null, ex);
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
    private void diagnosisCaller(ActionEvent event)  throws IOException{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Diagnosi.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE,null, ex);
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
            Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE,null, ex);
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
        // cleanup code here...
        System.out.println("\nSAVING SESSION");
        Utility util = new Utility(patient);
        util.writeLastSession();
    }

    public static void setInfo(){

    }

    public void setAnamnesiController(AnamnesiController anam){
        this.anamnesiController = anam;
    }

    @FXML
    void salva(ActionEvent event) {
        if(textFieldEta.getText().isEmpty())
            textFieldEta.setText("");
        anamnesiController.getInfo(comboBoxTipoParto.getSelectionModel().getSelectedItem(),
                comboBoxAlcolici.getSelectionModel().getSelectedItem(),
                comboBoxAlimentazione.getSelectionModel().getSelectedItem(),
                comboBoxAttivita.getSelectionModel().getSelectedItem(),
                comboBoxCaffeina.getSelectionModel().getSelectedItem(),
                comboBoxEsecuzioneParto.getSelectionModel().getSelectedItem(),
                comboBoxFumatore.getSelectionModel().getSelectedItem(),
                comboBoxRiposo.getSelectionModel().getSelectedItem(),
                comboBoxScuola.getSelectionModel().getSelectedItem(),
                comboBoxSviluppo.getSelectionModel().getSelectedItem(),
                comboBoxDroga.getSelectionModel().getSelectedItem(),
                comboBoxStress.getSelectionModel().getSelectedItem(),
                textFieldEta.getText(),
                textFieldAllergia.getText(),
                textFam.getText(),
                textPross.getText(),
                textRem.getText()
                );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxTipoParto.setItems(FXCollections.observableArrayList("pretermine", "termine", "post-termine"));
        comboBoxAlcolici.setItems(FXCollections.observableArrayList("nessuno", "irrilevante", "uso eccessivo"));
        comboBoxAlimentazione.setItems(FXCollections.observableArrayList("sana", "errata"));
        comboBoxAttivita.setItems(FXCollections.observableArrayList("assente", "poca attività", "molta attività"));
        comboBoxCaffeina.setItems(FXCollections.observableArrayList("nessuno", "irrilevante", "uso eccessivo"));
        comboBoxEsecuzioneParto.setItems(FXCollections.observableArrayList("naturale eutocico", "naturale distocito", "operatorio cesareo"));
        comboBoxFumatore.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxRiposo.setItems(FXCollections.observableArrayList("ideale", "non ideale"));
        comboBoxScuola.setItems(FXCollections.observableArrayList("analfabeta", "elementare", "medie-inferiori", "diploma", "laurea"));
        comboBoxSviluppo.setItems(FXCollections.observableArrayList("normale", "patologico"));
        comboBoxDroga.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxStress.setItems(FXCollections.observableArrayList("poco", "molto"));
        setInfo();
    }
}
