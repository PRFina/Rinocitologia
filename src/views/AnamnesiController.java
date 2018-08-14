package views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import rinocitologia.*;
import utility.*;

public class AnamnesiController implements Initializable {

    @FXML
    private TextArea textAnamFam, textAnamFis, textAnamPatRemota, textAnamPatProssima;

    private Patient patient;

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

    public void getInfo(){
        /*
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
        */
    }


    /*
    public void getInfo(){
        String anamFis = "";
        anamFis = "Tipo di parto: "+patient.getAnamnesi().getTipoParto()+
                "\nTipo di esecuzione del parto: "+patient.getAnamnesi().getEsecuzioneParto()+
                "\nTipo di sviluppo vegetativo e relazionale: "+patient.getAnamnesi().getSviluppo()+
                "\nLivello scolastico: "+patient.getAnamnesi().getScuola()+
                "\nEta': "+patient.getAnamnesi().getEta()+
                "\nLivello attività fisica: "+patient.getAnamnesi().getAttivita()+
                "\nAbitudini alimentari: "+patient.getAnamnesi().getAlimentazione()+
                "\nRitmo sonno/sveglia: "+patient.getAnamnesi().getRiposo()+
                "\nStress fisico/psicologico accumulato: "+patient.getAnamnesi().getStress()+
                "\nFumatore: "+patient.getAnamnesi().getFumatore()+
                "\nUso di alcolici: "+patient.getAnamnesi().getAlcool()+
                "\nUso di droghe: "+patient.getAnamnesi().getDroga()+
                "\nAssunzione di caffeina: "+patient.getAnamnesi().getCaffeina()+
                "\nAllergia: "+patient.getAnamnesi().getAllergia();

        textAnamFam.setText(patient.getAnamnesi().getAnamFamiliare());
        textAnamFis.setText(anamFis);
        textAnamPatProssima.setText(patient.getAnamnesi().getAnamProssima());
        textAnamPatRemota.setText(patient.getAnamnesi().getAnamRemota());
    }
    */

/*
        domandeAnamFisiologica.add("Che tipo di parto ha avuto? (pretermine, termine, post-termine)");
        domandeAnamFisiologica.add("Che tipo di parto ha avuto? (naturale eutocico, naturale distocito, operatorio cesareo)");
        domandeAnamFisiologica.add("Che tipo di allattamento ha avuto?(materno, artificiale, mercenario)");
        domandeAnamFisiologica.add("Che tipo di sviluppo vegetativo e relazionale ha avuto?(Normale, patologico)");
        domandeAnamFisiologica.add("A quale livello scolastico fa riferimento?(analfabeta, elementare, medie-inferiori, diploma, laurea)");
        domandeAnamFisiologica.add("A quale livello di attività fisica fa riferimento?(bassa, alta)");
        domandeAnamFisiologica.add("Quali sono le sue abitudini alimentari?(sano, non sano)");
        domandeAnamFisiologica.add("Qual'è il ritmo sonno/sveglia?(ideale, non ideale)");
        domandeAnamFisiologica.add("Quanto stress fisico/psicologico ha accumulato?(poco, molto)");
        domandeAnamFisiologica.add("Fuma? Se si da quanto tempo?(si, no)");
        domandeAnamFisiologica.add("Beve molti alcolici?(si, no)");
        domandeAnamFisiologica.add("Fa uso di droghe?(si, no)");
        domandeAnamFisiologica.add("Assume molta caffeina?");
        domandeAnamFisiologica.add("Quante gravidanze ha avuto?");
        //Chiedere l'età in cui si è avuto, l'inizio e la fine della gravidanza, l'esito, il sesso del bambino
        domandeAnamFisiologica.add("Qual'è l'età menarca? (della prima mestruazione)(un numero da 8 a 18)");
        domandeAnamFisiologica.add("Il ciclo mestruale è regolare?(si, no)");
        domandeAnamFisiologica.add("La menopaura è fisiologica?(si, no)");
        domandeAnamFisiologica.add("Che tipo di professione svolge?");
        domandeAnamFisiologica.add("Qual'è la condizione dell'alvo?(regolare, stitico, diarroico, alterno)");
        domandeAnamFisiologica.add("Qual'è la condizione della minzione?(regolare, patologica)");
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
        if (patient.getAnamnesi() == null) {
            Anamnesi anam = new Anamnesi();
            patient.setAnamnesi(anam);
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
    private void diagnosisCaller(ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Diagnosi.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AnamnesiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DiagnosiController controller = Loader.getController();
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
    }


}