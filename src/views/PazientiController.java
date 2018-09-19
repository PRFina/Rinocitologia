package views;


import com.itextpdf.text.DocumentException;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import rinocitologia.Anamnesi;
import rinocitologia.Patient;
import utility.DialogHelper;
import utility.Sequence;
import utility.SqliteHelper;
import utility.Utility;

import javax.swing.*;

public class PazientiController implements Initializable {

    @FXML
    private TextField input;
    @FXML
    private JFXTreeTableView<Paziente> treeView;

    private Patient patient;

    @FXML
    private Label patientTxt;

    @FXML
    public void setPatient(Patient patient) {this.patient = patient;
        String label = "Paziente: " + patient.getFirstName() + " " + patient.getSurname();
        patientTxt.setText(label);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXTreeTableColumn<Paziente, String> name = new JFXTreeTableColumn<>("Nome");
        name.setPrefWidth(150);
        name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Paziente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Paziente, String> param) {
                return param.getValue().getValue().name;
            }
        });

        JFXTreeTableColumn<Paziente, String> surname = new JFXTreeTableColumn<>("Cognome");
        surname.setPrefWidth(150);
        surname.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Paziente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Paziente, String> param) {
                return param.getValue().getValue().surname;
            }
        });

        JFXTreeTableColumn<Paziente, String> codicefiscale = new JFXTreeTableColumn<>("Codice Fiscale");
        codicefiscale.setPrefWidth(150);
        codicefiscale.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Paziente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Paziente, String> param) {
                return param.getValue().getValue().codicefiscale;
            }
        });

        JFXTreeTableColumn<Paziente, String> sex = new JFXTreeTableColumn<>("Sesso");
        sex.setPrefWidth(50);
        sex.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Paziente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Paziente, String> param) {
                return param.getValue().getValue().sex;
            }
        });

        JFXTreeTableColumn<Paziente, String> year = new JFXTreeTableColumn<>("Anno");
        year.setPrefWidth(50);
        year.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Paziente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Paziente, String> param) {
                return param.getValue().getValue().year;
            }
        });


        JFXTreeTableColumn<Paziente, String> month = new JFXTreeTableColumn<>("Mese");
        month.setPrefWidth(50);
        month.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Paziente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Paziente, String> param) {
                return param.getValue().getValue().month;
            }
        });

        JFXTreeTableColumn<Paziente, String> day = new JFXTreeTableColumn<>("Giorno");
        day.setPrefWidth(50);
        day.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Paziente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Paziente, String> param) {
                return param.getValue().getValue().day;
            }
        });

        SqliteHelper helper = new SqliteHelper( System.getProperty("user.home") + File.separator + "data" + File.separator + "test.db");
        List<String[]> listOfPazienti = helper.selectAllCfNotNull();
        ObservableList<Paziente> pazienti = FXCollections.observableArrayList();
        for(String[] paziente: listOfPazienti){
            pazienti.add(new Paziente(paziente[0], paziente[1], paziente[2], paziente[3], paziente[4], paziente[5], paziente[6]));
        }
        //pazienti.add(new Paziente("Computer Department", "23", "CD 1", "M", "1996", "18", "01"));


        final TreeItem<Paziente> root = new RecursiveTreeItem<Paziente>(pazienti, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(name,surname,codicefiscale,sex,year,month, day);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

        input.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeView.setPredicate(new Predicate<TreeItem<Paziente>>() {
                    @Override
                    public boolean test(TreeItem<Paziente> pazienteTreeItem) {
                        Boolean flag = pazienteTreeItem.getValue().surname.getValue().contains(newValue)||pazienteTreeItem.getValue().name.getValue().contains(newValue)||pazienteTreeItem.getValue().year.getValue().contains(newValue)||pazienteTreeItem.getValue().codicefiscale.getValue().contains(newValue);
                        return flag;
                    }
                });
            }
        });

        //treeView.getSelectionModel().selectedItemProperty().addListener();
        Object object =  treeView.getSelectionModel().selectedItemProperty().get();
        int index = treeView.getSelectionModel().selectedIndexProperty().get();
        treeView.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()==2){
                System.out.println(treeView.getSelectionModel().getSelectedItem().getValue().codicefiscale.get());
                String cf = treeView.getSelectionModel().getSelectedItem().getValue().codicefiscale.get();
                if(DialogHelper.showConfirmationDialog("Confermare caricamento", "Confermare il caricamento per il paziente " + cf, "Confermare?")) {

                    String target = patient.getPathData() + File.separator + cf;
                    Path path = Paths.get(target);
                    if (Files.exists(path)) {
                        DialogHelper.showAlert(Alert.AlertType.INFORMATION, "Successo", "Successo.", "Paziente caricato con successo.");
                        patient = Utility.readJson(cf);
                        setPatient(patient);
                        System.out.println(patient.toString());

                        //CALL INSERT CELLS
                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("Home.fxml"));
                        try {
                            Loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        HomeController controller = Loader.getController();
                        controller.setPatient(patient);
                        controller.setInfo();


                        //Inizio Carica View
                        Parent p = Loader.getRoot();
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(p));
                        stage.show();

                    } else {
                        DialogHelper.showAlert(Alert.AlertType.ERROR, "Errore", "Codice Fiscale errato.", "Inserisci un codice fiscale corretto per poter caricare i dati di un paziente.");
                    }
                }
            }
        });
    }

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
    private void insertCells(ActionEvent event)  throws IOException {
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

    @FXML
    private void filter(ActionEvent event) {
    }

    class Paziente extends RecursiveTreeObject<Paziente> {

        StringProperty name;
        StringProperty surname;
        StringProperty codicefiscale;
        StringProperty sex;
        StringProperty year;
        StringProperty month;
        StringProperty day;


        public Paziente(String name, String surname, String codicefiscale, String sex, String year, String month, String day) {
            this.name = new SimpleStringProperty(name);
            this.surname = new SimpleStringProperty(surname);
            this.codicefiscale = new SimpleStringProperty(codicefiscale);
            this.sex = new SimpleStringProperty(sex);
            this.year = new SimpleStringProperty(year);
            this.month = new SimpleStringProperty(month);
            this.day = new SimpleStringProperty(day);

        }

    }
}
