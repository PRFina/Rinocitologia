package views;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import filemngr.DialogHelper;
import filemngr.ListView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import rinocitologia.Patient;
import utility.Utility;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class RevisioneController implements Initializable {

    private Patient patient;

    @FXML
    TilePane epitelialiTile, mucipareTile, neutrofiliTile, eosinofiliTile, mastcelluleTile, linfocitiTile, altroTile;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void setPatient(Patient patient) {this.patient = patient;}

    public void setTiles(){
        setEpitelialiTile(epitelialiTile);
        setMucipareTile(mucipareTile);
        setNeutrofiliTile(neutrofiliTile);
        setEosinofiliTile(eosinofiliTile);
        setMastcelluleTile(mastcelluleTile);
        setLinfocitiTile(linfocitiTile);
        setAltroTile(altroTile);
//        System.out.println(patient.toString());
    }

    public void setEpitelialiTile(TilePane tile) {

        tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setHgap(15);
        //Patient patient = new Patient();
        String path = patient.getPath() + File.separator + "inputs" + File.separator + "Epiteliali";

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (final File file : listOfFiles) {
            ImageView imageView;
            imageView = createImageView(file);
            ContextMenu contextMenu = new ContextMenu();

            MenuItem titleItem = new MenuItem("Move to:");
            titleItem.setDisable(true);

            MenuItem item1 = new MenuItem("Mucipare");
            item1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item1.getText());
                    //imageView.setVisible(false);
                }
            });
            MenuItem item2 = new MenuItem("Neutrofili");
            item2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item2.getText());
                }
            });

            MenuItem item3 = new MenuItem("Eosinofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item3.getText());
                }
            });

            MenuItem item4 = new MenuItem("Mastcellule");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item4.getText());
                }
            });

            MenuItem item5 = new MenuItem("Linfociti");
            item5.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item5.getText());
                }
            });

            MenuItem item6 = new MenuItem("Altro");
            item6.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item6.getText());
                }
            });

            contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6);

            // When user right-click on Circle
            imageView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                @Override
                public void handle(ContextMenuEvent event) {

                    contextMenu.show(imageView, event.getScreenX(), event.getScreenY());
                }
            });
            tile.getChildren().addAll(imageView);
        }
    }

    public void setNeutrofiliTile(TilePane tile) {

        tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setHgap(15);
        //Patient patient = new Patient();
        String path = patient.getPath() + File.separator + "inputs" + File.separator + "Neutrofili";

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (final File file : listOfFiles) {
            ImageView imageView;
            imageView = createImageView(file);
            ContextMenu contextMenu = new ContextMenu();

            MenuItem titleItem = new MenuItem("Move to:");
            titleItem.setDisable(true);


            MenuItem item2 = new MenuItem("Mucipare");
            item2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item2.getText());
                }
            });
            MenuItem item1 = new MenuItem("Epiteliali");
            item1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item1.getText());
                }
            });

            MenuItem item3 = new MenuItem("Eosinofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item3.getText());
                }
            });

            MenuItem item4 = new MenuItem("Mastcellule");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item4.getText());
                }
            });

            MenuItem item5 = new MenuItem("Linfociti");
            item5.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item5.getText());
                }
            });

            MenuItem item6 = new MenuItem("Altro");
            item6.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item6.getText());
                }
            });

            contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6);

            // When user right-click on Circle
            imageView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                @Override
                public void handle(ContextMenuEvent event) {

                    contextMenu.show(imageView, event.getScreenX(), event.getScreenY());
                }
            });
            tile.getChildren().addAll(imageView);
        }
    }

    public void setMucipareTile(TilePane tile) {

        tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setHgap(15);
        //Patient patient = new Patient();
        String path = patient.getPath() + File.separator + "inputs" + File.separator + "Mucipare";

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (final File file : listOfFiles) {
            ImageView imageView;
            imageView = createImageView(file);
            ContextMenu contextMenu = new ContextMenu();
            MenuItem titleItem = new MenuItem("Move to:");
            titleItem.setDisable(true);

            MenuItem item1 = new MenuItem("Epiteliali");
            item1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item1.getText());
                }
            });
            MenuItem item2 = new MenuItem("Neutrofili");
            item2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item2.getText());
                }
            });

            MenuItem item3 = new MenuItem("Eosinofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item3.getText());
                }
            });

            MenuItem item4 = new MenuItem("Mastcellule");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item4.getText());
                }
            });

            MenuItem item5 = new MenuItem("Linfociti");
            item5.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item5.getText());
                }
            });

            MenuItem item6 = new MenuItem("Altro");
            item6.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item6.getText());
                }
            });

            contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6);

            // When user right-click on Circle
            imageView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                @Override
                public void handle(ContextMenuEvent event) {

                    contextMenu.show(imageView, event.getScreenX(), event.getScreenY());
                }
            });
            tile.getChildren().addAll(imageView);
        }
    }

    public void setEosinofiliTile(TilePane tile) {

        tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setHgap(15);
        //Patient patient = new Patient();
        String path = patient.getPath() + File.separator + "inputs" + File.separator + "eosinofili";

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (final File file : listOfFiles) {
            ImageView imageView;
            imageView = createImageView(file);
            ContextMenu contextMenu = new ContextMenu();

            MenuItem titleItem = new MenuItem("Move to:");
            titleItem.setDisable(true);

            MenuItem item1 = new MenuItem("Epiteliali");
            item1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item1.getText());
                }
            });
            MenuItem item2 = new MenuItem("Mucipare");
            item2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item2.getText());
                }
            });

            MenuItem item3 = new MenuItem("Neutrofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item3.getText());
                }
            });

            MenuItem item4 = new MenuItem("Mastcellule");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item4.getText());
                }
            });

            MenuItem item5 = new MenuItem("Linfociti");
            item5.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item5.getText());
                }
            });

            MenuItem item6 = new MenuItem("Altro");
            item6.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item6.getText());
                }
            });

            contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6);

            // When user right-click on Circle
            imageView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                @Override
                public void handle(ContextMenuEvent event) {

                    contextMenu.show(imageView, event.getScreenX(), event.getScreenY());
                }
            });
            tile.getChildren().addAll(imageView);
        }
    }

    public void setMastcelluleTile(TilePane tile) {

        tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setHgap(15);
        //Patient patient = new Patient();
        String path = patient.getPath() + File.separator + "inputs" + File.separator + "mastcellule";

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (final File file : listOfFiles) {
            ImageView imageView;
            imageView = createImageView(file);
            ContextMenu contextMenu = new ContextMenu();

            MenuItem titleItem = new MenuItem("Move to:");
            titleItem.setDisable(true);

            MenuItem item1 = new MenuItem("Epiteliali");
            item1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item1.getText());
                }
            });
            MenuItem item2 = new MenuItem("Mucipare");
            item2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item2.getText());
                }
            });

            MenuItem item3 = new MenuItem("Neutrofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item3.getText());
                }
            });

            MenuItem item4 = new MenuItem("Eosinofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item4.getText());
                }
            });

            MenuItem item5 = new MenuItem("Linfociti");
            item5.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item5.getText());
                }
            });

            MenuItem item6 = new MenuItem("Altro");
            item6.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item6.getText());
                }
            });

            contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6);


            // When user right-click on Circle
            imageView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                @Override
                public void handle(ContextMenuEvent event) {

                    contextMenu.show(imageView, event.getScreenX(), event.getScreenY());
                }
            });
            tile.getChildren().addAll(imageView);
        }
    }

    public void setLinfocitiTile(TilePane tile) {

        tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setHgap(15);
        //Patient patient = new Patient();
        String path = patient.getPath() + File.separator + "inputs" + File.separator + "linfociti";

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (final File file : listOfFiles) {
            ImageView imageView;
            imageView = createImageView(file);
            ContextMenu contextMenu = new ContextMenu();

            MenuItem titleItem = new MenuItem("Move to:");
            titleItem.setDisable(true);

            MenuItem item1 = new MenuItem("Epiteliali");
            item1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item1.getText());
                }
            });
            MenuItem item2 = new MenuItem("Mucipare");
            item2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item2.getText());
                }
            });

            MenuItem item3 = new MenuItem("Neutrofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item3.getText());
                }
            });

            MenuItem item4 = new MenuItem("Eosinofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item4.getText());
                }
            });

            MenuItem item5 = new MenuItem("Mastcellule");
            item5.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item5.getText());
                }
            });

            MenuItem item6 = new MenuItem("Altro");
            item6.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item6.getText());
                }
            });

            contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6);

            // When user right-click on Circle
            imageView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                @Override
                public void handle(ContextMenuEvent event) {

                    contextMenu.show(imageView, event.getScreenX(), event.getScreenY());
                }
            });
            tile.getChildren().addAll(imageView);
        }
    }

    public void setAltroTile(TilePane tile) {

        tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setHgap(15);
        //Patient patient = new Patient();
        String path = patient.getPath() + File.separator + "inputs" + File.separator + "others";

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (final File file : listOfFiles) {
            ImageView imageView;
            imageView = createImageView(file);
            ContextMenu contextMenu = new ContextMenu();
            MenuItem titleItem = new MenuItem("Move to:");
            titleItem.setDisable(true);

            MenuItem item1 = new MenuItem("Epiteliali");
            item1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item1.getText());
                }
            });
            MenuItem item2 = new MenuItem("Mucipare");
            item2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item2.getText());
                }
            });

            MenuItem item3 = new MenuItem("Neutrofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item3.getText());
                }
            });

            MenuItem item4 = new MenuItem("Eosinofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item4.getText());
                }
            });

            MenuItem item5 = new MenuItem("Mastcellule");
            item5.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item5.getText());
                }
            });

            MenuItem item6 = new MenuItem("Linfociti");
            item6.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item6.getText());
                }
            });

            contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6);


            // When user right-click on Circle
            imageView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                @Override
                public void handle(ContextMenuEvent event) {

                    contextMenu.show(imageView, event.getScreenX(), event.getScreenY());
                }
            });
            tile.getChildren().addAll(imageView);
        }
    }

    private void copyTo(final File imageFile, String destination){
        FileInputStream file = null;
        try {
            file = new FileInputStream(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(destination == "Epiteliali") {
            Path path = Paths.get(patient.getPath() + File.separator + "epiteliali");
            try {
                Files.copy(file, path, REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Copiato " + imageFile.getName() + " in " + patient.getPath() + File.separator + "epiteliali");
        }
        if(destination == "Mucipare") {
            Path path = Paths.get(patient.getPath() + File.separator + "Mucipare");
            try {
                Files.copy(file, path, REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Copiato " + imageFile.getName() + " in " + patient.getPath() + File.separator + "Mucipare");
        }
    }

    private ImageView createImageView(final File imageFile) {
        // DEFAULT_THUMBNAIL_WIDTH is a constant you need to define
        // The last two arguments are: preserveRatio, and use smooth (slower)
        // resizing

        ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile), 50, 0, true,
                    true);
            imageView = new ImageView(image);
            imageView.setFitWidth(50);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                        if(mouseEvent.getClickCount() == 2){
                            /*
                            try {
                                BorderPane borderPane = new BorderPane();
                                ImageView imageView = new ImageView();
                                Image image = new Image(new FileInputStream(imageFile));
                                imageView.setImage(image);
                                imageView.setStyle("-fx-background-color: BLACK");
                                imageView.setPreserveRatio(true);
                                imageView.setSmooth(true);
                                imageView.setCache(true);
                                borderPane.setCenter(imageView);
                                borderPane.setStyle("-fx-background-color: BLACK");
                                Stage newStage = new Stage();
                                newStage.setWidth(300);
                                newStage.setHeight(300);
                                newStage.setTitle(imageFile.getName());
                                Scene scene = new Scene(borderPane,Color.BLACK);
                                newStage.setScene(scene);
                                newStage.show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            */
                            try {
                                Desktop.getDesktop().open(imageFile);
                            } catch (Exception e) {
                                DialogHelper.showException(e);
                            }

                        }
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }


    /*
     *
     * START SIDEBAR COMMANDS
     *
     */

    @FXML
    private void anamnesiCaller(ActionEvent event)  throws IOException{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Anamnesi.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(DiagnosiController.class.getName()).log(Level.SEVERE,null, ex);
        }
        AnamnesiController controller = Loader.getController();
        controller.setPatient(patient);
//        anamnesi.print();
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
            Logger.getLogger(DiagnosiController.class.getName()).log(Level.SEVERE,null, ex);
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
            Logger.getLogger(DiagnosiController.class.getName()).log(Level.SEVERE,null, ex);
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
            Logger.getLogger(DiagnosiController.class.getName()).log(Level.SEVERE,null, ex);
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
        stage.setScene(new Scene(p));
        stage.show();
    }

    public void shutdown() {
        // cleanup code here...
        System.out.println("SAVING SESSION");
        Utility util = new Utility(patient);
        util.writeLastSession();
    }

}
