package views;

import javafx.application.Platform;
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
import rinocitologia.Anamnesi;
import rinocitologia.Patient;
import utility.DialogHelper;
import utility.Utility;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionsController  implements Initializable {

    @FXML
    private CheckBox cupressacee, nocciolo, ontano,  pioppo,  frassino, betulla, salice,  carpino, platano,  quercia,  orniello;

    @FXML
    private CheckBox pinacee,  urticacee, graminacee,  poligonacee,  castagno, assenzio, piantaggine;

    @FXML
    private TextArea textFam, textPross, textRem;

    @FXML
    private ComboBox<String> comboBoxTipoParto, comboBoxEsecuzioneParto, comboBoxSviluppo, comboBoxScuola, comboBoxAttivita;

    @FXML
    private ComboBox<String> comboBoxAlimentazione, comboBoxRiposo, comboBoxStress, comboBoxDroga, comboBoxFumatore;

    @FXML
    private ComboBox<String> comboBoxAlcolici, comboBoxCaffeina;

    @FXML
    private TextField textFieldAllergia;

    @FXML
    private TextField textFieldEta;

    private Patient patient;

    @FXML
    private Label patientTxt;

    @FXML
    public void setPatient(Patient patient) {
        this.patient = patient;
        String label = "Paziente: " + patient.getFirstName() + " " + patient.getSurname();
        patientTxt.setText(label);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}


    public void setInfo(){
        comboBoxTipoParto.setItems(FXCollections.observableArrayList("pretermine", "termine", "post-termine"));
        comboBoxTipoParto.setValue(patient.getAnamnesi().getTipoParto());
        comboBoxAlcolici.setItems(FXCollections.observableArrayList("nessuno", "irrilevante", "uso eccessivo"));
        comboBoxAlcolici.setValue(patient.getAnamnesi().getAlcool());
        comboBoxAlimentazione.setItems(FXCollections.observableArrayList("sana", "errata"));
        comboBoxAlimentazione.setValue(patient.getAnamnesi().getAlimentazione());
        comboBoxAttivita.setItems(FXCollections.observableArrayList("assente", "poca attività", "molta attività"));
        comboBoxAttivita.setValue(patient.getAnamnesi().getAttivita());
        comboBoxCaffeina.setItems(FXCollections.observableArrayList("nessuno", "irrilevante", "uso eccessivo"));
        comboBoxCaffeina.setValue(patient.getAnamnesi().getCaffeina());
        comboBoxEsecuzioneParto.setItems(FXCollections.observableArrayList("naturale eutocico", "naturale distocito", "operatorio cesareo"));
        comboBoxEsecuzioneParto.setValue(patient.getAnamnesi().getEsecuzioneParto());
        comboBoxFumatore.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxFumatore.setValue(patient.getAnamnesi().getFumatore());
        comboBoxRiposo.setItems(FXCollections.observableArrayList("ideale", "non ideale"));
        comboBoxRiposo.setValue(patient.getAnamnesi().getRiposo());
        comboBoxScuola.setItems(FXCollections.observableArrayList("analfabeta", "elementare", "medie-inferiori", "diploma", "laurea"));
        comboBoxScuola.setValue(patient.getAnamnesi().getScuola());
        comboBoxSviluppo.setItems(FXCollections.observableArrayList("normale", "patologico"));
        comboBoxSviluppo.setValue(patient.getAnamnesi().getSviluppo());
        comboBoxDroga.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxDroga.setValue(patient.getAnamnesi().getDroga());
        comboBoxStress.setItems(FXCollections.observableArrayList("poco", "molto"));
        comboBoxStress.setValue(patient.getAnamnesi().getStress());
        textFieldEta.setText(patient.getAnamnesi().getEta());
        textFam.setText(patient.getAnamnesi().getAnamRemota());
        textRem.setText(patient.getAnamnesi().getAnamFamiliare());
        textPross.setText(patient.getAnamnesi().getAnamProssima());
        //Inserire checkbox per le allergie del paziente corrente
    }

    @FXML
    void salva(ActionEvent event) {
        if((textFieldEta.getText().matches("[0-9]+")) && (textFieldEta.getText().length() > 0)) {
            patient.getAnamnesi().setInfo(comboBoxTipoParto.getSelectionModel().getSelectedItem(),
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
                    chekboxes(),
                    textFam.getText(),
                    textPross.getText(),
                    textRem.getText()
            );
            Utility utility = new Utility(patient);
            utility.writeJson();

            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("Anamnesi.fxml"));
            try {
                Loader.load();
            } catch (IOException ex){
                Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE,null, ex);
            }
            AnamnesiController controller = Loader.getController();
            controller.setPatient(patient);
            controller.getInfo();

            //Inizio Carica View
            Parent p = Loader.getRoot();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setOnHidden(e -> {
                controller.shutdown();
                Platform.exit();
            });
            stage.setScene(new Scene(p));
            stage.show();
        }else{
            DialogHelper.showAlert(Alert.AlertType.ERROR, "Errore", "Errore nell'inserimento dell'età", "Il campo inerente all'eta' deve essere riempito con un numero maggiore di 0.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
        }
    }

    public ArrayList<String> chekboxes(){
        ArrayList<String> checkbox = new ArrayList<String>();
        if(graminacee.isSelected()){
            checkbox.add(graminacee.getText());
        }
        if(cupressacee.isSelected()){
            checkbox.add(cupressacee.getText());
        }
        if(nocciolo.isSelected()){
            checkbox.add(nocciolo.getText());
        }
        if(ontano.isSelected()){
            checkbox.add(ontano.getText());
        }
        if(pioppo.isSelected()){
            checkbox.add(pioppo.getText());
        }
        if(frassino.isSelected()){
            checkbox.add(frassino.getText());
        }
        if(betulla.isSelected()){
            checkbox.add(betulla.getText());
        }
        if(salice.isSelected()){
            checkbox.add(salice.getText());
        }
        if(carpino.isSelected()){
            checkbox.add(carpino.getText());
        }
        if(platano.isSelected()){
            checkbox.add(platano.getText());
        }
        if(quercia.isSelected()){
            checkbox.add(quercia.getText());
        }
        if(orniello.isSelected()){
            checkbox.add(orniello.getText());
        }
        if(pinacee.isSelected()){
            checkbox.add(pinacee.getText());
        }
        if(urticacee.isSelected()){
            checkbox.add(urticacee.getText());
        }
        if(graminacee.isSelected()){
            checkbox.add(graminacee.getText());
        }
        if(poligonacee.isSelected()){
            checkbox.add(poligonacee.getText());
        }
        if(castagno.isSelected()){
            checkbox.add(castagno.getText());
        }
        if(assenzio.isSelected()){
            checkbox.add(assenzio.getText());
        }
        if(piantaggine.isSelected()){
            checkbox.add(piantaggine.getText());
        }

        for(String el:checkbox){
            System.out.println(el);
        }

        return checkbox;
    }

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
        if(patient.getAnamnesi() == null){
            Anamnesi anam = new Anamnesi();
            patient.setAnamnesi(anam);
        }
        controller.setPatient(patient);
        controller.getInfo();

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
            Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE,null, ex);
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
        // cleanup code here...
        System.out.println("\nSAVING SESSION");
        Utility util = new Utility(patient);
        util.writeLastSession();
    }



}
