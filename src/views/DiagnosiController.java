package views;

import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import rinocitologia.*;
import utility.DialogHelper;
import utility.Utility;

public class DiagnosiController implements Initializable {

    @FXML
    private TextArea textAreaTerapia, textAreaDiagnosi;


    private ArrayList<Diagnosi> diagnosi = new ArrayList<>();

    private Patient patient;
    private Diagnosis diagnosis;

    @FXML
    private ListView<String> listView;

    @FXML
    private SplitPane splitPane;

    @FXML
    private AnchorPane splitPaneDx, splitPaneSx;

    @FXML
    Label patientTxt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    @FXML
    public void setPatient(Patient patient) {this.patient = patient;
        String label = "Paziente: " + patient.getFirstName() + " " + patient.getSurname();
        patientTxt.setText(label);
    }

    @FXML
    void salva(ActionEvent event) {
        patient.setDiagnosi(diagnosi);
        System.out.println(patient.getDiagnosi());
        patient.setTerapia(textAreaTerapia.getText());
        patient.setDiagnosiUfficiale(textAreaDiagnosi.getText());

        if(patient.getDiagnosiUfficiale()!=null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK, ButtonType.CANCEL);
            alert.setTitle("Aggiunta diagnosi personalizzata");
            alert.setHeaderText("Quali informazioni sono state determinanti per diagnosticare la patologia inserita?");
            alert.getDialogPane().setPrefSize(480, 320);
            ScrollPane scrollpane = new ScrollPane();

            ArrayList<CheckBox> checkboxes = new ArrayList<>();
            AnchorPane anchorpane = new AnchorPane();
            int posizione = 30;
            for(ArrayList<String> i : diagnosis.getFattiAsseriti()){
                CheckBox c = new CheckBox(i.get(0));
                c.setLayoutY(posizione);
                checkboxes.add(c);
                anchorpane.getChildren().add(checkboxes.get(checkboxes.size()-1));
                posizione = posizione + 30;
            }
            scrollpane.setContent(anchorpane);
            scrollpane.setPannable(true);
            alert.getDialogPane().setContent(scrollpane);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                alert.close();
                String parteSinistra = "";
                String informazioni = "";
                for(CheckBox c : checkboxes){
                    if(c.isSelected()){
                        for(ArrayList<String> i : diagnosis.getFattiAsseriti()){
                            if(c.getText()==i.get(0)) {
                                informazioni = informazioni +" \\\""+c.getText()+"\\\"";
                                parteSinistra = parteSinistra + " " + i.get(1);
                            }
                        }
                    }
                }
                if(parteSinistra.length() > 3){
                    diagnosis.writeFileDiagnosi(parteSinistra, informazioni);
                }else
                    DialogHelper.showAlert(Alert.AlertType.INFORMATION, "Apprendimento automatico", "Assenza informazioni inerenti i sintomi","La diagnosi attuale sarà salvata per il paziente, ma non sarà memorizzata per i pazienti futuri.");
            }
            if(result.get() == ButtonType.CANCEL)
                alert.close();
        }

        Utility utility = new Utility(patient);
        utility.writeJson();
        try {
            utility.writePdfReport(ReportType.completo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

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

    /*
     *
     * START SIDEBAR COMMANDS
     *
     */

    public void setDati(){
        splitPaneSx.maxWidthProperty().bind(splitPane.widthProperty().multiply(0.43538767395626243));
        splitPaneDx.maxWidthProperty().bind(splitPane.widthProperty().multiply(0.43538767395626243));
        textAreaTerapia.setText(patient.getTerapia());
        textAreaDiagnosi.setText(patient.getDiagnosiUfficiale());
        try {
            diagnosis = new Diagnosis(patient);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        diagnosi = patient.getDiagnosi();
        for(Diagnosi d : diagnosi)
            listView.getItems().add(d.getNome());
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void handleItemClicks(javafx.scene.input.MouseEvent mouseEvent) {
        splitPaneDx.getChildren().clear();
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        for(Diagnosi d : diagnosi){
            if(d.getNome()==selectedItem) {
                String informazioni = "";
                for(String info : d.getInformazioni()){
                    informazioni = informazioni+info+"\n";
                }
                System.out.println(informazioni);
                splitPaneDx.getChildren().add(new Label(informazioni));
            }
        }
    }

    @FXML
    private void insertCells(ActionEvent event)  throws IOException{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Home.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(DiagnosiController.class.getName()).log(Level.SEVERE,null, ex);
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
            Logger.getLogger(DiagnosiController.class.getName()).log(Level.SEVERE,null, ex);
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
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Diagnosi.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(DiagnosiController.class.getName()).log(Level.SEVERE,null, ex);
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
            Logger.getLogger(DiagnosiController.class.getName()).log(Level.SEVERE,null, ex);
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
            Logger.getLogger(DiagnosiController.class.getName()).log(Level.SEVERE,null, ex);
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
        // cleanup code here...
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
