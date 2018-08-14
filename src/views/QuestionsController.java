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
import utility.Utility;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionsController  implements Initializable {

    @FXML
    private Label patientTxt;

    @FXML
    private Label title;

    //Variabili delle componenti della parte di Anamnesi familiare
    @FXML
    private ComboBox<String> comboBoxAllergiaGen, comboBoxTipoAllergiaGen, comboBoxAllergiaFra, comboBoxTipoAllergiaFra, comboBoxPoliposiGen, comboBoxAsmaFra, comboBoxAsmaGen, comboBoxPoliposiFra;

    @FXML
    private TextArea textAreaAppuntiFam;

    //Variabili delle componenti della parte di Anamnesi patologica prossima
    @FXML
    private ComboBox<String> comboBoxOstruzione, comboBoxRinorrea, comboBoxEspansioneRinorrea, comboBoxPruritoNasale, comboBoxStarnuto, comboBoxOlfatto, comboBoxOvattamento, comboBoxIpoacusia, comboBoxAcufeni, comboBoxVertigini;

    @FXML
    private CheckBox checkBoxLacrimazione, checkBoxFotofobia, checkBoxPrurito, checkBoxBruciore;

    @FXML
    private TextArea textAreaAppuntiPat;

    //Variabili delle componenti della parte di Esame del medico
    @FXML
    private ComboBox<String> comboBoxPirNasale, comboBoxValNasale, comboBoxSetto, comboBoxTurbinati, comboBoxPolSx, comboBoxPolDx, comboBoxEssudato, comboBoxIpertrofia;

    @FXML
    private TextArea textAreaAppuntiAlterazioniLIO, textAreaAppuntiEsameOtoscopico, textAreaAppuntiEsameRinom;

    //Variabili delle componenti della parte del Prick-Test
    @FXML
    private CheckBox checkBoxPositivo, checkBoxNegativo, cupressacee, nocciolo, ontano,  pioppo,  frassino, betulla, salice,  carpino, platano,  quercia,  orniello, pinacee,  urticacee, graminacee,  poligonacee,  castagno, assenzio, piantaggine;


    private Patient patient;

    @FXML
    public void setPatient(Patient patient) {
        this.patient = patient;
        String label = "Paziente: " + patient.getFirstName() + " " + patient.getSurname();
        patientTxt.setText(label);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}


    public void setInfo(){
        comboBoxAllergiaGen.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxAllergiaGen.setValue(patient.getAnamnesi().getAllergiaGen());
        comboBoxTipoAllergiaGen.setItems(FXCollections.observableArrayList("alimenti", "inalanti"));
        comboBoxTipoAllergiaGen.setValue(patient.getAnamnesi().getTipoAllergiaGen());
        comboBoxAllergiaFra.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxAllergiaFra.setValue(patient.getAnamnesi().getAllergiaFra());
        comboBoxTipoAllergiaFra.setItems(FXCollections.observableArrayList("alimenti", "inalanti"));
        comboBoxTipoAllergiaFra.setValue(patient.getAnamnesi().getTipoAllergiaFra());
        comboBoxPoliposiGen.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxPoliposiGen.setValue(patient.getAnamnesi().getPoliposiGen());
        comboBoxAsmaFra.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxPoliposiFra.setValue(patient.getAnamnesi().getPoliposiFra());
        comboBoxAsmaGen.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxAsmaGen.setValue(patient.getAnamnesi().getAsmaGen());
        comboBoxPoliposiFra.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxAsmaFra.setValue(patient.getAnamnesi().getAsmaFra());
        textAreaAppuntiFam.setText(patient.getAnamnesi().getAppuntiFam());

        comboBoxOstruzione.setItems(FXCollections.observableArrayList("nessuna", "sinistra", "destra", "bilaterale"));
        comboBoxOstruzione.setValue(patient.getAnamnesi().getOstruzione());
        comboBoxRinorrea.setItems(FXCollections.observableArrayList("nessuna", "sierosa", "mucosa", "purulenta", "ematica"));
        comboBoxRinorrea.setValue(patient.getAnamnesi().getRinorrea());
        comboBoxEspansioneRinorrea.setItems(FXCollections.observableArrayList("sinistra", "destra", "bilaterale"));
        comboBoxEspansioneRinorrea.setValue(patient.getAnamnesi().getEspansioneRinorrea());
        comboBoxPruritoNasale.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxPruritoNasale.setValue(patient.getAnamnesi().getPruritoNasale());
        comboBoxStarnuto.setItems(FXCollections.observableArrayList("nessuna", "sporadica", "a salve"));
        comboBoxStarnuto.setValue(patient.getAnamnesi().getStarnuto());
        comboBoxOlfatto.setItems(FXCollections.observableArrayList("nessuno", "iposmia", "anosmia", "cacosmia"));
        comboBoxOlfatto.setValue(patient.getAnamnesi().getOlfatto());
        comboBoxOvattamento.setItems(FXCollections.observableArrayList("nessuno", "sinistro", "destro", "bilaterale"));
        comboBoxOvattamento.setValue(patient.getAnamnesi().getOvattamento());
        comboBoxIpoacusia.setItems(FXCollections.observableArrayList("nessuno", "sinistro", "destro", "bilaterale"));
        comboBoxIpoacusia.setValue(patient.getAnamnesi().getIpoacusia());
        comboBoxAcufeni.setItems(FXCollections.observableArrayList("nessuno", "sinistro", "destro", "bilaterale"));
        comboBoxAcufeni.setValue(patient.getAnamnesi().getAcufeni());
        comboBoxVertigini.setItems(FXCollections.observableArrayList("nessuna", "soggettiva", "oggettiva"));
        comboBoxVertigini.setValue(patient.getAnamnesi().getVertigini());

        checkBoxLacrimazione.setSelected(patient.getAnamnesi().getLacrimazione());
        checkBoxFotofobia.setSelected(patient.getAnamnesi().getFotofobia());
        checkBoxPrurito.setSelected(patient.getAnamnesi().getPrurito());
        checkBoxBruciore.setSelected(patient.getAnamnesi().getBruciore());

        textAreaAppuntiPat.setText(patient.getAnamnesi().getAppuntiPat());

        comboBoxPirNasale.setItems(FXCollections.observableArrayList("normoformata", "gibbo", "scoiosi", "deformazioni varie"));
        comboBoxPirNasale.setValue(patient.getAnamnesi().getPirNasale());
        comboBoxValNasale.setItems(FXCollections.observableArrayList("normofunzionante", "insufficienza sinistra", "insufficienza destra", "insufficienza bilaterale"));
        comboBoxValNasale.setValue(patient.getAnamnesi().getValNasale());
        comboBoxSetto.setItems(FXCollections.observableArrayList("in asse", "deviato a sinistra", "deviato a destra", "esse italica"));
        comboBoxSetto.setValue(patient.getAnamnesi().getSetto());
        comboBoxTurbinati.setItems(FXCollections.observableArrayList("normotrofici", "ipertrofici", "iperemici", "ematosi"));
        comboBoxTurbinati.setValue(patient.getAnamnesi().getTurbinati());
        comboBoxPolSx.setItems(FXCollections.observableArrayList("assente", "1", "2", "3", "4"));
        comboBoxPolSx.setValue(patient.getAnamnesi().getPolSx());
        comboBoxPolDx.setItems(FXCollections.observableArrayList("assente", "1", "2", "3", "4"));
        comboBoxPolDx.setValue(patient.getAnamnesi().getPolDx());
        comboBoxEssudato.setItems(FXCollections.observableArrayList("assente", "sieroso", "mucoso", "purulento", "ematico"));
        comboBoxEssudato.setValue(patient.getAnamnesi().getEssudato());
        comboBoxIpertrofia.setItems(FXCollections.observableArrayList("assente", "1", "2", "3", "4"));
        comboBoxIpertrofia.setValue(patient.getAnamnesi().getIpertrofia());

        textAreaAppuntiAlterazioniLIO.setText(patient.getAnamnesi().getAppuntiAlterazioniLIO());
        textAreaAppuntiEsameOtoscopico.setText(patient.getAnamnesi().getAppuntiEsameOtoscopico());
        textAreaAppuntiEsameRinom.setText(patient.getAnamnesi().getAppuntiEsameRinom());

    }

    @FXML
    void tipoAllergiaGen(ActionEvent event) {
        if(comboBoxAllergiaGen.getSelectionModel().getSelectedItem() == "no"){
            comboBoxTipoAllergiaGen.setValue("");
            comboBoxTipoAllergiaGen.setDisable(true);
        }
        else
            comboBoxTipoAllergiaGen.setDisable(false);
    }

    @FXML
    void tipoAllergiaFra(ActionEvent event) {
        if(comboBoxAllergiaFra.getSelectionModel().getSelectedItem() == "no"){
            comboBoxTipoAllergiaFra.setValue("");
            comboBoxTipoAllergiaFra.setDisable(true);
        }
        else
            comboBoxTipoAllergiaFra.setDisable(false);
    }

    @FXML
    void espansioneRinorrea(ActionEvent event) {
        if(comboBoxRinorrea.getSelectionModel().getSelectedItem() == "nessuna"){
            comboBoxEspansioneRinorrea.setValue("");
            comboBoxEspansioneRinorrea.setDisable(true);
        }
        else
            comboBoxEspansioneRinorrea.setDisable(false);
    }

    @FXML
    void testNegativo(ActionEvent event) {
        if(checkBoxNegativo.isSelected()){
            checkBoxPositivo.setSelected(false);
            cupressacee.setSelected(false);
            cupressacee.setDisable(true);
            nocciolo.setSelected(false);
            nocciolo.setDisable(true);
            ontano.setSelected(false);
            ontano.setDisable(true);
            pioppo.setSelected(false);
            pioppo.setDisable(true);
            frassino.setSelected(false);
            frassino.setDisable(true);
            betulla.setSelected(false);
            betulla.setDisable(true);
            salice.setSelected(false);
            salice.setDisable(true);
            carpino.setSelected(false);
            carpino.setDisable(true);
            platano.setSelected(false);
            platano.setDisable(true);
            quercia.setSelected(false);
            quercia.setDisable(true);
            orniello.setSelected(false);
            orniello.setDisable(true);
            pinacee.setSelected(false);
            pinacee.setDisable(true);
            urticacee.setSelected(false);
            urticacee.setDisable(true);
            graminacee.setSelected(false);
            graminacee.setDisable(true);
            poligonacee.setSelected(false);
            poligonacee.setDisable(true);
            castagno.setSelected(false);
            castagno.setDisable(true);
            assenzio.setSelected(false);
            assenzio.setDisable(true);
            piantaggine.setSelected(false);
            piantaggine.setDisable(true);
        }
        else
            checkBoxPositivo.setSelected(true);
    }

    @FXML
    void testPositivo(ActionEvent event) {
        if(checkBoxPositivo.isSelected()){
            checkBoxNegativo.setSelected(false);
            cupressacee.setSelected(false);
            cupressacee.setDisable(false);
            nocciolo.setSelected(false);
            nocciolo.setDisable(false);
            ontano.setSelected(false);
            ontano.setDisable(false);
            pioppo.setSelected(false);
            pioppo.setDisable(false);
            frassino.setSelected(false);
            frassino.setDisable(false);
            betulla.setSelected(false);
            betulla.setDisable(false);
            salice.setSelected(false);
            salice.setDisable(false);
            carpino.setSelected(false);
            carpino.setDisable(false);
            platano.setSelected(false);
            platano.setDisable(false);
            quercia.setSelected(false);
            quercia.setDisable(false);
            orniello.setSelected(false);
            orniello.setDisable(false);
            pinacee.setSelected(false);
            pinacee.setDisable(false);
            urticacee.setSelected(false);
            urticacee.setDisable(false);
            graminacee.setSelected(false);
            graminacee.setDisable(false);
            poligonacee.setSelected(false);
            poligonacee.setDisable(false);
            castagno.setSelected(false);
            castagno.setDisable(false);
            assenzio.setSelected(false);
            assenzio.setDisable(false);
            piantaggine.setSelected(false);
            piantaggine.setDisable(false);
        }
        else
            checkBoxNegativo.setSelected(true);
    }


    @FXML
    void salva(ActionEvent event) {
        patient.getAnamnesi().setInfo(comboBoxAllergiaGen.getSelectionModel().getSelectedItem(),
                comboBoxTipoAllergiaGen.getSelectionModel().getSelectedItem(),
                comboBoxAllergiaFra.getSelectionModel().getSelectedItem(),
                comboBoxTipoAllergiaFra.getSelectionModel().getSelectedItem(),
                comboBoxPoliposiGen.getSelectionModel().getSelectedItem(),
                comboBoxAsmaFra.getSelectionModel().getSelectedItem(),
                comboBoxAsmaGen.getSelectionModel().getSelectedItem(),
                comboBoxPoliposiFra.getSelectionModel().getSelectedItem(),
                textAreaAppuntiFam.getText(),
                comboBoxOstruzione.getSelectionModel().getSelectedItem(),
                comboBoxRinorrea.getSelectionModel().getSelectedItem(),
                comboBoxEspansioneRinorrea.getSelectionModel().getSelectedItem(),
                comboBoxPruritoNasale.getSelectionModel().getSelectedItem(),
                comboBoxStarnuto.getSelectionModel().getSelectedItem(),
                comboBoxOlfatto.getSelectionModel().getSelectedItem(),
                comboBoxOvattamento.getSelectionModel().getSelectedItem(),
                comboBoxIpoacusia.getSelectionModel().getSelectedItem(),
                comboBoxAcufeni.getSelectionModel().getSelectedItem(),
                comboBoxVertigini.getSelectionModel().getSelectedItem(),
                checkBoxLacrimazione.isSelected(),
                checkBoxFotofobia.isSelected(),
                checkBoxPrurito.isSelected(),
                checkBoxBruciore.isSelected(),
                textAreaAppuntiPat.getText(),
                comboBoxPirNasale.getSelectionModel().getSelectedItem(),
                comboBoxValNasale.getSelectionModel().getSelectedItem(),
                comboBoxSetto.getSelectionModel().getSelectedItem(),
                comboBoxTurbinati.getSelectionModel().getSelectedItem(),
                comboBoxPolSx.getSelectionModel().getSelectedItem(),
                comboBoxPolDx.getSelectionModel().getSelectedItem(),
                comboBoxEssudato.getSelectionModel().getSelectedItem(),
                comboBoxIpertrofia.getSelectionModel().getSelectedItem(),
                textAreaAppuntiAlterazioniLIO.getText(),
                textAreaAppuntiEsameOtoscopico.getText(),
                textAreaAppuntiEsameRinom.getText(),
                checkboxes()
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
    }

    public ArrayList<String> checkboxes(){
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
