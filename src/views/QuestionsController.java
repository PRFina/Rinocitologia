package views;

import com.itextpdf.text.DocumentException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import rinocitologia.Anamnesi;
import rinocitologia.Patient;
import rinocitologia.Resistenza;
import utility.DialogHelper;
import utility.Utility;

import java.io.FileNotFoundException;
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
    private CheckBox checkBoxPositivo, checkBoxNegativo, checkBoxPerenne, cupressacee, nocciolo, ontano,  pioppo,  frassino, betulla, salice,  carpino, platano,  quercia,  orniello, pinacee,  urticacee, graminacee,  poligonacee,  castagno, assenzio, piantaggine;

    @FXML
    TableView table;

    TableColumn titolo = new TableColumn(" ");
    TableColumn base = new TableColumn("BASE");
    TableColumn decong = new TableColumn("DECONG");
    TableColumn valori = new TableColumn("Valori normali");

    private Patient patient;

    private Anamnesi currentAnamnesi;

    public void setCurrentAnamnesi(Anamnesi currentAnamnesi){this.currentAnamnesi = currentAnamnesi;}

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
        comboBoxAllergiaGen.setValue(currentAnamnesi.getAllergiaGen());
        comboBoxTipoAllergiaGen.setItems(FXCollections.observableArrayList("alimenti", "inalanti"));
        comboBoxTipoAllergiaGen.setValue(currentAnamnesi.getTipoAllergiaGen());
        comboBoxAllergiaFra.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxAllergiaFra.setValue(currentAnamnesi.getAllergiaFra());
        comboBoxTipoAllergiaFra.setItems(FXCollections.observableArrayList("alimenti", "inalanti"));
        comboBoxTipoAllergiaFra.setValue(currentAnamnesi.getTipoAllergiaFra());
        comboBoxPoliposiGen.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxPoliposiGen.setValue(currentAnamnesi.getPoliposiGen());
        comboBoxAsmaFra.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxPoliposiFra.setValue(currentAnamnesi.getPoliposiFra());
        comboBoxAsmaGen.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxAsmaGen.setValue(currentAnamnesi.getAsmaGen());
        comboBoxPoliposiFra.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxAsmaFra.setValue(currentAnamnesi.getAsmaFra());
        textAreaAppuntiFam.setText(currentAnamnesi.getAppuntiFam());

        comboBoxOstruzione.setItems(FXCollections.observableArrayList("nessuna", "sinistra", "destra", "bilaterale"));
        comboBoxOstruzione.setValue(currentAnamnesi.getOstruzione());
        comboBoxRinorrea.setItems(FXCollections.observableArrayList("nessuna", "sierosa", "mucosa", "purulenta", "ematica"));
        comboBoxRinorrea.setValue(currentAnamnesi.getRinorrea());
        comboBoxEspansioneRinorrea.setItems(FXCollections.observableArrayList("sinistra", "destra", "bilaterale"));
        comboBoxEspansioneRinorrea.setValue(currentAnamnesi.getEspansioneRinorrea());
        comboBoxPruritoNasale.setItems(FXCollections.observableArrayList("si", "no"));
        comboBoxPruritoNasale.setValue(currentAnamnesi.getPruritoNasale());
        comboBoxStarnuto.setItems(FXCollections.observableArrayList("nessuna", "sporadica", "a salve"));
        comboBoxStarnuto.setValue(currentAnamnesi.getStarnuto());
        comboBoxOlfatto.setItems(FXCollections.observableArrayList("nessuno", "iposmia", "anosmia", "cacosmia"));
        comboBoxOlfatto.setValue(currentAnamnesi.getOlfatto());
        comboBoxOvattamento.setItems(FXCollections.observableArrayList("nessuno", "sinistro", "destro", "bilaterale"));
        comboBoxOvattamento.setValue(currentAnamnesi.getOvattamento());
        comboBoxIpoacusia.setItems(FXCollections.observableArrayList("nessuno", "sinistro", "destro", "bilaterale"));
        comboBoxIpoacusia.setValue(currentAnamnesi.getIpoacusia());
        comboBoxAcufeni.setItems(FXCollections.observableArrayList("nessuno", "sinistro", "destro", "bilaterale"));
        comboBoxAcufeni.setValue(currentAnamnesi.getAcufeni());
        comboBoxVertigini.setItems(FXCollections.observableArrayList("nessuna", "soggettiva", "oggettiva"));
        comboBoxVertigini.setValue(currentAnamnesi.getVertigini());

        checkBoxLacrimazione.setSelected(currentAnamnesi.getLacrimazione());
        checkBoxFotofobia.setSelected(currentAnamnesi.getFotofobia());
        checkBoxPrurito.setSelected(currentAnamnesi.getPrurito());
        checkBoxBruciore.setSelected(currentAnamnesi.getBruciore());

        textAreaAppuntiPat.setText(currentAnamnesi.getAppuntiPat());

        comboBoxPirNasale.setItems(FXCollections.observableArrayList("normoformata", "gibbo", "scoiosi", "deformazioni varie"));
        comboBoxPirNasale.setValue(currentAnamnesi.getPirNasale());
        comboBoxValNasale.setItems(FXCollections.observableArrayList("normofunzionante", "insufficienza sinistra", "insufficienza destra", "insufficienza bilaterale"));
        comboBoxValNasale.setValue(currentAnamnesi.getValNasale());
        comboBoxSetto.setItems(FXCollections.observableArrayList("in asse", "deviato a sinistra", "deviato a destra", "esse italica"));
        comboBoxSetto.setValue(currentAnamnesi.getSetto());
        comboBoxTurbinati.setItems(FXCollections.observableArrayList("normotrofici", "ipertrofici", "iperemici", "ematosi"));
        comboBoxTurbinati.setValue(currentAnamnesi.getTurbinati());
        comboBoxPolSx.setItems(FXCollections.observableArrayList("assente", "1", "2", "3", "4"));
        comboBoxPolSx.setValue(currentAnamnesi.getPolSx());
        comboBoxPolDx.setItems(FXCollections.observableArrayList("assente", "1", "2", "3", "4"));
        comboBoxPolDx.setValue(currentAnamnesi.getPolDx());
        comboBoxEssudato.setItems(FXCollections.observableArrayList("assente", "sieroso", "mucoso", "purulento", "ematico"));
        comboBoxEssudato.setValue(currentAnamnesi.getEssudato());
        comboBoxIpertrofia.setItems(FXCollections.observableArrayList("assente", "1", "2", "3", "4"));
        comboBoxIpertrofia.setValue(currentAnamnesi.getIpertrofia());

        textAreaAppuntiAlterazioniLIO.setText(currentAnamnesi.getAppuntiAlterazioniLIO());
        textAreaAppuntiEsameOtoscopico.setText(currentAnamnesi.getAppuntiEsameOtoscopico());
        textAreaAppuntiEsameRinom.setText(currentAnamnesi.getAppuntiEsameRinom());

        ObservableList<Resistenza> data;

        table.getColumns().addAll(titolo, base, decong, valori);

        data = FXCollections.observableArrayList(
                new Resistenza("Resistenze Sx", currentAnamnesi.getBaseSx(), "", "< 0,50"),
                new Resistenza("Resistenze Dx", "", "", "< 0,50"),
                new Resistenza("Resistenze Sx+Dx", "", "", "< 0,25")
        );

        titolo.setCellValueFactory(
                new PropertyValueFactory<Resistenza,String>("titolo")
        );
        base.setCellValueFactory(
                new PropertyValueFactory<Resistenza,String>("base")
        );
        decong.setCellValueFactory(
                new PropertyValueFactory<Resistenza,String>("decong")
        );

        valori.setCellValueFactory(
                new PropertyValueFactory<Resistenza,String>("valori")
        );
        table.setItems(data);
        comboBoxTipoAllergiaGen.setDisable(true);
        comboBoxTipoAllergiaFra.setDisable(true);
        comboBoxEspansioneRinorrea.setDisable(true);

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
        if(comboBoxRinorrea.getSelectionModel().getSelectedItem() == "nessuna" || comboBoxRinorrea.getSelectionModel().getSelectedItem() == ""){
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
            checkBoxPerenne.setSelected(false);
            checkBoxPerenne.setDisable(true);
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
    }

    @FXML
    void testPositivo(ActionEvent event) {
        if(checkBoxPositivo.isSelected()){
            checkBoxNegativo.setSelected(false);
            checkBoxPerenne.setSelected(false);
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
    }

    /**
     * Writes informations in anamnesi object.
     * @param event
     */
    @FXML
    void salva(ActionEvent event) {
        Anamnesi anamnesi = new Anamnesi();
        TextField baseSx = (TextField) base.getCellObservableValue(0).getValue();
        TextField baseDx = (TextField) base.getCellObservableValue(1).getValue();
        TextField decongSx = (TextField) decong.getCellObservableValue(0).getValue();
        TextField decongDx = (TextField) decong.getCellObservableValue(1).getValue();
        TextField baseSxDx = (TextField) base.getCellObservableValue(2).getValue();
        TextField decongSxDx = (TextField) decong.getCellObservableValue(2).getValue();
        anamnesi.setInfo(comboBoxAllergiaGen.getSelectionModel().getSelectedItem(),
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
                checkboxes(),
                baseSx.getText(),
                baseDx.getText(),
                decongSx.getText(),
                decongDx.getText(),
                baseSxDx.getText(),
                decongSxDx.getText()
            );

        patient.addAnamnesi(anamnesi);

        Utility utility = new Utility(patient);
        utility.writeJson();
        System.out.println(anamnesi.toString());
        try {
            utility.writePdfReport(anamnesi);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

            FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Anamnesi.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE,null, ex);
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

    /**
     * Fills an array with informations get from selected checkboxes
     * @return
     */
    public ArrayList<String> checkboxes(){
        ArrayList<String> checkbox = new ArrayList<String>();
        if(checkBoxPerenne.isSelected()){
            checkbox.add(checkBoxPerenne.getText());
        }
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
        if(patient.getAnamnesiList() == null){
            Anamnesi anam = new Anamnesi();
            patient.addAnamnesi(anam);
        }
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
            Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE,null, ex);
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