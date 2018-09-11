package views;

import java.awt.*;
import java.awt.Dialog;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.itextpdf.text.DocumentException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
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
import rinocitologia.Anamnesi;
import rinocitologia.Cell;
import rinocitologia.Patient;
import utility.FileHelper;
import utility.Utility;
import utility.DialogHelper;

import javax.swing.*;

public class RevisioneController implements Initializable {

    private Patient patient;

    private FileHelper fileHelper = new FileHelper();

    @FXML
    TilePane epitelialiTile, mucipareTile, neutrofiliTile, eosinofiliTile, mastcelluleTile, linfocitiTile, biofilmTile,  altroTile;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    @FXML
    Label patientTxt;



    //-------------------------
    //     VAR PER CLIENT
    String dirName;
    Socket clientSocket;
    InputStream inFromServer;
    OutputStream outToServer;
    BufferedInputStream bis;
    PrintWriter pw;
    String name, file, path;
    String hostAddr;
    int portNumber;
    int c;
    int size = 9022386;
    //__________________________
    Boolean connected = false;

    @FXML
    public void setPatient(Patient patient) {this.patient = patient;
        String label = "Paziente: " + patient.getFirstName() + " " + patient.getSurname();
        patientTxt.setText(label);
    }

    /**
     * Set tiles to let the medic to review classifier's work.
     * Each tile displays cells belonging to a class (such as Epiteliali, mucipare, etc).
     * The medic can move wrong classified cells by right-clicking on one of them and selecting the correct class in the context menu displayed.
     * The medic can also display the cell with the default Preview application installed in his computer by double clicking an image.
     */
    public void setTiles(){

//        System.out.println(patient.toString());

        /*
        //SETTO SERVER
        hostAddr = "localhost";
        portNumber = 3333;

        try {
            clientSocket = new Socket(hostAddr, portNumber);
            inFromServer = clientSocket.getInputStream();
            pw = new PrintWriter(clientSocket.getOutputStream(), true);
            outToServer = clientSocket.getOutputStream();
            ObjectInputStream oin = new ObjectInputStream(inFromServer);

            String s = (String) oin.readObject();
            System.out.println(s);
            connected = true;
            */
            /*
            len = Integer.parseInt((String) oin.readObject());
            System.out.println(len);

            String[] temp_names = new String[len];

            for (int i = 0; i < len; i++) {
                String filename = (String) oin.readObject();
                System.out.println(filename);
                names[i] = filename;
                temp_names[i] = filename;
            }
            */
            /*
        } catch (Exception exc) {
            System.out.println("Exception: " + exc.getMessage());
        }
            */
            establishConnection();
        setEpitelialiTile(epitelialiTile);
        setMucipareTile(mucipareTile);
        setNeutrofiliTile(neutrofiliTile);
        setEosinofiliTile(eosinofiliTile);
        setMastcelluleTile(mastcelluleTile);
        setLinfocitiTile(linfocitiTile);
        setBiofilmTile(biofilmTile);
        setAltroTile(altroTile);
    }

    public void setEpitelialiTile(TilePane tile) {
        //tile.setPrefTileWidth(90);
        //tile.setPrefTileHeight(90);

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
                    setMucipareTile(mucipareTile);
                    imageView.setVisible(false);
                    copyTo(file, item1.getText());
                }
            });
            MenuItem item2 = new MenuItem("Neutrofili");
            item2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item2.getText());
                    copyTo(file, item2.getText());
                    imageView.setVisible(false);
                    setNeutrofiliTile(neutrofiliTile);

                }

            });

            MenuItem item3 = new MenuItem("Eosinofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item3.getText());
                    copyTo(file, item3.getText());
                    imageView.setVisible(false);
                    setEosinofiliTile(eosinofiliTile);

                }
            });

            MenuItem item4 = new MenuItem("Mastcellule");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item4.getText());
                    copyTo(file, item4.getText());
                    imageView.setVisible(false);
                    setMastcelluleTile(mastcelluleTile);
                }
            });

            MenuItem item5 = new MenuItem("Linfociti");
            item5.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item5.getText());
                    copyTo(file, item5.getText());
                    imageView.setVisible(false);
                    setLinfocitiTile(linfocitiTile);
                }
            });

            MenuItem item6 = new MenuItem("Altro");
            item6.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item6.getText());
                    copyTo(file, item6.getText());
                    imageView.setVisible(false);
                    setAltroTile(altroTile);
                }
            });

            if (clientSocket != null) {
                MenuItem serverTitleItem = new MenuItem("Pass to:");
                serverTitleItem.setDisable(true);


                MenuItem serverMove = new MenuItem("Server");
                serverMove.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(item2.getText());
                        uploadServer(file, "epiteliali", patient.getSurname(), patient.getFirstName());
                        try {
                            if(clientSocket != null) {
            clientSocket.close();
        }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        establishConnection();

                    }
                });

                contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6, serverTitleItem, serverMove);

            } else {
                contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6);
            }
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
                    copyTo(file, item2.getText());
                    imageView.setVisible(false);
                    setMucipareTile(mucipareTile);

                }
            });
            MenuItem item1 = new MenuItem("Epiteliali");
            item1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item1.getText());
                    copyTo(file, item1.getText());
                    imageView.setVisible(false);
                    setEpitelialiTile(epitelialiTile);

                }
            });

            MenuItem item3 = new MenuItem("Eosinofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item3.getText());
                    copyTo(file, item3.getText());
                    imageView.setVisible(false);
                    setEosinofiliTile(eosinofiliTile);

                }
            });

            MenuItem item4 = new MenuItem("Mastcellule");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item4.getText());
                    copyTo(file, item4.getText());
                    imageView.setVisible(false);
                    setMastcelluleTile(mastcelluleTile);

                }
            });

            MenuItem item5 = new MenuItem("Linfociti");
            item5.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item5.getText());
                    copyTo(file, item5.getText());
                    imageView.setVisible(false);
                    setLinfocitiTile(linfocitiTile);

                }
            });

            MenuItem item6 = new MenuItem("Altro");
            item6.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item6.getText());
                    copyTo(file, item6.getText());
                    imageView.setVisible(false);
                    setAltroTile(altroTile);

                }
            });

            if (clientSocket != null) {
                MenuItem serverTitleItem = new MenuItem("Pass to:");
                serverTitleItem.setDisable(true);


                MenuItem serverMove = new MenuItem("Server");
                serverMove.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(item2.getText());
                        uploadServer(file, "neutrofili", patient.getSurname(), patient.getFirstName());
                        try {
                            if(clientSocket != null) {
            clientSocket.close();
        }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        establishConnection();

                    }
                });

                contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6, serverTitleItem, serverMove);

            } else {
                contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6);
            }
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
                    copyTo(file, item1.getText());
                    imageView.setVisible(false);
                    setEpitelialiTile(epitelialiTile);

                }
            });
            MenuItem item2 = new MenuItem("Neutrofili");
            item2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item2.getText());
                    copyTo(file, item2.getText());
                    imageView.setVisible(false);
                    setNeutrofiliTile(neutrofiliTile);
                }
            });

            MenuItem item3 = new MenuItem("Eosinofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item3.getText());
                    copyTo(file, item3.getText());
                    imageView.setVisible(false);
                    setEosinofiliTile(eosinofiliTile);

                }
            });

            MenuItem item4 = new MenuItem("Mastcellule");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item4.getText());
                    copyTo(file, item4.getText());
                    imageView.setVisible(false);
                    setMastcelluleTile(mastcelluleTile);

                }
            });

            MenuItem item5 = new MenuItem("Linfociti");
            item5.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item5.getText());
                    copyTo(file, item5.getText());
                    imageView.setVisible(false);
                    setLinfocitiTile(linfocitiTile);

                }
            });

            MenuItem item6 = new MenuItem("Altro");
            item6.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item6.getText());
                    copyTo(file, item6.getText());
                    imageView.setVisible(false);
                    setAltroTile(altroTile);

                }
            });

            if (clientSocket != null) {
                MenuItem serverTitleItem = new MenuItem("Pass to:");
                serverTitleItem.setDisable(true);


                MenuItem serverMove = new MenuItem("Server");
                serverMove.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(item2.getText());
                        uploadServer(file, "mucipare", patient.getSurname(), patient.getFirstName());
                        try {
                            if(clientSocket != null) {
            clientSocket.close();
        }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        establishConnection();

                    }
                });

                contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6, serverTitleItem, serverMove);

            } else {
                contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6);
            }
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
                    copyTo(file, item1.getText());
                    imageView.setVisible(false);
                    setEpitelialiTile(epitelialiTile);

                }
            });
            MenuItem item2 = new MenuItem("Mucipare");
            item2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item2.getText());
                    copyTo(file, item2.getText());
                    imageView.setVisible(false);
                    setMucipareTile(mucipareTile);

                }
            });

            MenuItem item3 = new MenuItem("Neutrofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item3.getText());
                    copyTo(file, item3.getText());
                    imageView.setVisible(false);
                    setNeutrofiliTile(neutrofiliTile);
                }
            });

            MenuItem item4 = new MenuItem("Mastcellule");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item4.getText());
                    copyTo(file, item4.getText());
                    imageView.setVisible(false);
                    setMastcelluleTile(mastcelluleTile);

                }
            });

            MenuItem item5 = new MenuItem("Linfociti");
            item5.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item5.getText());
                    copyTo(file, item5.getText());
                    imageView.setVisible(false);
                    setLinfocitiTile(linfocitiTile);

                }
            });

            MenuItem item6 = new MenuItem("Altro");
            item6.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item6.getText());
                    copyTo(file, item6.getText());
                    imageView.setVisible(false);
                    setAltroTile(altroTile);

                }
            });

            if (clientSocket != null) {
                MenuItem serverTitleItem = new MenuItem("Pass to:");
                serverTitleItem.setDisable(true);


                MenuItem serverMove = new MenuItem("Server");
                serverMove.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(item2.getText());
                        uploadServer(file, "eosinofili", patient.getSurname(), patient.getFirstName());
                        try {
                            if(clientSocket != null) {
            clientSocket.close();
        }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        establishConnection();

                    }
                });

                contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6, serverTitleItem, serverMove);

            } else {
                contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6);
            }
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
                    copyTo(file, item1.getText());
                    imageView.setVisible(false);
                    setEpitelialiTile(epitelialiTile);

                }
            });
            MenuItem item2 = new MenuItem("Mucipare");
            item2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item2.getText());
                    copyTo(file, item2.getText());
                    imageView.setVisible(false);
                    setMucipareTile(mucipareTile);

                }
            });

            MenuItem item3 = new MenuItem("Neutrofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item3.getText());
                    copyTo(file, item3.getText());
                    imageView.setVisible(false);
                    setNeutrofiliTile(neutrofiliTile);
                }
            });

            MenuItem item4 = new MenuItem("Eosinofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item4.getText());
                    copyTo(file, item4.getText());
                    imageView.setVisible(false);
                    setEosinofiliTile(eosinofiliTile);

                }
            });

            MenuItem item5 = new MenuItem("Linfociti");
            item5.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item5.getText());
                    copyTo(file, item5.getText());
                    imageView.setVisible(false);
                    setLinfocitiTile(linfocitiTile);

                }
            });

            MenuItem item6 = new MenuItem("Altro");
            item6.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item6.getText());
                    copyTo(file, item6.getText());
                    imageView.setVisible(false);
                    setAltroTile(altroTile);

                }
            });

            if (clientSocket != null) {
                MenuItem serverTitleItem = new MenuItem("Pass to:");
                serverTitleItem.setDisable(true);


                MenuItem serverMove = new MenuItem("Server");
                serverMove.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(item2.getText());
                        uploadServer(file, "mastcellule", patient.getSurname(), patient.getFirstName());
                        try {
                            if(clientSocket != null) {
            clientSocket.close();
        }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        establishConnection();

                    }
                });

                contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6, serverTitleItem, serverMove);

            } else {
                contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6);
            }

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
                    copyTo(file, item1.getText());
                    imageView.setVisible(false);
                    setEpitelialiTile(epitelialiTile);

                }
            });
            MenuItem item2 = new MenuItem("Mucipare");
            item2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item2.getText());
                    copyTo(file, item2.getText());
                    imageView.setVisible(false);
                    setMucipareTile(mucipareTile);

                }
            });

            MenuItem item3 = new MenuItem("Neutrofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item3.getText());
                    copyTo(file, item3.getText());
                    imageView.setVisible(false);
                    setNeutrofiliTile(neutrofiliTile);
                }
            });

            MenuItem item4 = new MenuItem("Eosinofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item4.getText());
                    copyTo(file, item4.getText());
                    imageView.setVisible(false);
                    setEosinofiliTile(eosinofiliTile);

                }
            });

            MenuItem item5 = new MenuItem("Mastcellule");
            item5.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item5.getText());
                    copyTo(file, item5.getText());
                    imageView.setVisible(false);
                    setMastcelluleTile(mastcelluleTile);

                }
            });

            MenuItem item6 = new MenuItem("Altro");
            item6.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item6.getText());
                    copyTo(file, item6.getText());
                    imageView.setVisible(false);
                    setAltroTile(altroTile);

                }
            });
            if (clientSocket != null) {
                MenuItem serverTitleItem = new MenuItem("Pass to:");
                serverTitleItem.setDisable(true);


                MenuItem serverMove = new MenuItem("Server");
                serverMove.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(item2.getText());
                        uploadServer(file, "linfociti", patient.getSurname(), patient.getFirstName());
                        try {
                            if(clientSocket != null) {
            clientSocket.close();
        }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        establishConnection();

                    }
                });

                contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6, serverTitleItem, serverMove);

            } else {
                contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6);
            }

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


    public void setBiofilmTile(TilePane tile) {

        tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setHgap(15);
        //Patient patient = new Patient();
        String path = patient.getPath() + File.separator + "inputs" + File.separator + "biofilmsi";

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (final File file : listOfFiles) {
            ImageView imageView;
            imageView = createImageView(file);
            ContextMenu contextMenu = new ContextMenu();
            MenuItem titleItem = new MenuItem("Remove:");
            titleItem.setDisable(true);

            MenuItem item1 = new MenuItem("Yes");
            item1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item1.getText());
                    copyTo(file, item1.getText());
                    imageView.setVisible(false);
                    //setEpitelialiTile(epitelialiTile);

                }
            });


            if (clientSocket != null) {
                MenuItem serverTitleItem = new MenuItem("Pass to:");
                serverTitleItem.setDisable(true);


                MenuItem serverMove = new MenuItem("Server");
                serverMove.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        //System.out.println(item2.getText());
                        uploadServer(file, "biofilm", patient.getSurname(), patient.getFirstName());
                        try {
                            if(clientSocket != null) {
                                clientSocket.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        establishConnection();

                    }
                });

                contextMenu.getItems().addAll(titleItem, item1, serverTitleItem, serverMove);

            } else {
                contextMenu.getItems().addAll(titleItem, item1);
            }

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
                    copyTo(file, item1.getText());
                    imageView.setVisible(false);
                    setEpitelialiTile(epitelialiTile);

                }
            });
            MenuItem item2 = new MenuItem("Mucipare");
            item2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item2.getText());
                    copyTo(file, item2.getText());
                    imageView.setVisible(false);
                    setMucipareTile(mucipareTile);

                }
            });

            MenuItem item3 = new MenuItem("Neutrofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item3.getText());
                    copyTo(file, item3.getText());
                    imageView.setVisible(false);
                    setNeutrofiliTile(neutrofiliTile);
                }
            });

            MenuItem item4 = new MenuItem("Eosinofili");
            item3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item4.getText());
                    copyTo(file, item4.getText());
                    imageView.setVisible(false);
                    setEosinofiliTile(eosinofiliTile);

                }
            });

            MenuItem item5 = new MenuItem("Mastcellule");
            item5.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item5.getText());
                    copyTo(file, item5.getText());
                    imageView.setVisible(false);
                    setMastcelluleTile(mastcelluleTile);

                }
            });

            MenuItem item6 = new MenuItem("Linfociti");
            item6.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println(item6.getText());
                    copyTo(file, item6.getText());
                    imageView.setVisible(false);
                    setLinfocitiTile(linfocitiTile);

                }
            });

            if (clientSocket != null) {
                MenuItem serverTitleItem = new MenuItem("Pass to:");
                serverTitleItem.setDisable(true);


                MenuItem serverMove = new MenuItem("Server");
                serverMove.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(item2.getText());
                        uploadServer(file, "others", patient.getSurname(), patient.getFirstName());
                        try {
                            if(clientSocket != null) {
            clientSocket.close();
        }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        establishConnection();

                    }
                });

                contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6, serverTitleItem, serverMove);

            } else {
                contextMenu.getItems().addAll(titleItem, item1, item2, item3, item4, item5, item6);
            }

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

    private void establishConnection(){
        //SETTO SERVER
        hostAddr = "localhost";
        portNumber = 3333;

        try {
            clientSocket = new Socket(hostAddr, portNumber);
            inFromServer = clientSocket.getInputStream();
            pw = new PrintWriter(clientSocket.getOutputStream(), true);
            outToServer = clientSocket.getOutputStream();
            ObjectInputStream oin = new ObjectInputStream(inFromServer);

            String s = (String) oin.readObject();
            System.out.println(s);
            connected = true;
            /*
            len = Integer.parseInt((String) oin.readObject());
            System.out.println(len);

            String[] temp_names = new String[len];

            for (int i = 0; i < len; i++) {
                String filename = (String) oin.readObject();
                System.out.println(filename);
                names[i] = filename;
                temp_names[i] = filename;
            }
            */
        } catch (Exception exc) {
            System.out.println("Exception: " + exc.getMessage());
        }
    }



    private void uploadServer(File image, String typology, String surname, String name){
        try {
            path = image.getAbsolutePath();
            //outToServer = clientSocket.getOutputStream();
            FileInputStream file = null;
            BufferedInputStream bis = null;

            boolean fileExists = true;


            try {
                file = new FileInputStream(path);
                bis = new BufferedInputStream(file);
            } catch (FileNotFoundException excep) {
                fileExists = false;
                System.out.println("FileNotFoundException:" + excep.getMessage());

            }

            if (fileExists) {
                // send file name to server
                String[] filepath = path.split(File.separator);
                String filename = typology + File.separator + surname + "_" + name + "_" + filepath[filepath.length-1];

                //pw.println(path);
                pw.println(filename);

                System.out.println("Upload begins");


                // send file data to server
                sendBytes(bis, outToServer);
                System.out.println("Completed");

                /*
                boolean exists = false;
                for(int i = 0; i < len; i++){
                    if(names[i].equals(name)){
                        exists = true;
                        break;
                    }
                }

                if(!exists){
                    names[len] = name;
                    len++;
                }

                String[] temp_names = new String[len];
                for(int i = 0; i < len; i++){
                    temp_names[i] = names[i];
                }

                // sort the array of strings that's going to get displayed in the scrollpane
                Arrays.sort(temp_names);
                */

                // close all file buffers
                bis.close();
                file.close();
                DialogHelper.showAlert(Alert.AlertType.INFORMATION, "Trasferimento completato", "Il trasferimento è stato completato", "Continuare");
                //outToServer.close();
                //outToServer.flush();
            }
        }
        catch (Exception exc) {
            System.out.println("Exception: " + exc.getMessage());
            DialogHelper.showAlert(Alert.AlertType.ERROR, "ERRORE", "Errore", "Non è stato possibile completare il trasferimento");

        }
    }

    //private static void sendBytes(BufferedInputStream in , OutputStream out) throws Exception {
    private void sendBytes(BufferedInputStream in , OutputStream out) throws Exception {
        int size = 9022386;
        byte[] data = new byte[size];
        int bytes = 0;
        int c = in.read(data, 0, data.length);
        out.write(data, 0, c);
        out.flush();
    }

    /**
     * Utility method to copy files (image right-clicked from a tile) to a new (and correct destination).
     * @param imageFile
     * @param destination
     */
    private void copyTo(final File imageFile, String destination){
        Path path = imageFile.toPath();
        if(destination == "Epiteliali") {
            Path target = Paths.get(patient.getPath() + File.separator + "inputs" + File.separator + "epiteliali");
            fileHelper.copy(path, target);
            System.out.println("Copiato " + imageFile.getName() + " in " + patient.getPath() + File.separator + "inputs" + File.separator + "epiteliali");
        }
        if(destination == "Mucipare") {
            Path target = Paths.get(patient.getPath() + File.separator + "inputs" + File.separator + "Mucipare");
            fileHelper.copy(path, target);
            System.out.println("Copiato " + imageFile.getName() + " in " + patient.getPath() + File.separator +"inputs" + File.separator + "mucipare");
        }
        if(destination == "Neutrofili") {
            Path target = Paths.get(patient.getPath() + File.separator + "inputs" + File.separator +"inputs" + File.separator + "Neutrofili");
            fileHelper.copy(path, target);
            System.out.println("Copiato " + imageFile.getName() + " in " + patient.getPath() + File.separator +"inputs" + File.separator + "neutrofili");
        }
        if(destination == "Eosinofili") {
            Path target = Paths.get(patient.getPath() + File.separator + "inputs" + File.separator + "Eosinofili");
            fileHelper.copy(path, target);
            System.out.println("Copiato " + imageFile.getName() + " in " + patient.getPath() + File.separator +"inputs" + File.separator + "eosinofili");
        }
        if(destination == "Mastcellule") {
            Path target = Paths.get(patient.getPath() + File.separator + "inputs" + File.separator + "Mastcellule");
            fileHelper.copy(path, target);
            System.out.println("Copiato " + imageFile.getName() + " in " + patient.getPath() + File.separator +"inputs" + File.separator + "mastcellule");
        }
        if(destination == "Linfociti") {
            Path target = Paths.get(patient.getPath() + File.separator + "inputs" + File.separator + "Linfociti");
            fileHelper.copy(path, target);
            System.out.println("Copiato " + imageFile.getName() + " in " + patient.getPath() + File.separator +"inputs" + File.separator + "linfociti");
        }
        if(destination == "Altro") {
            Path target = Paths.get(patient.getPath() + File.separator + "inputs" + File.separator + "Others");
            fileHelper.copy(path, target);
            System.out.println("Copiato " + imageFile.getName() + " in " + patient.getPath() + File.separator +"inputs" + File.separator + "others");
        }
        if(destination == "Yes") {
            Path target = Paths.get(patient.getPath() + File.separator + "inputs" + File.separator + "biofilmno");
            fileHelper.copy(path, target);
            System.out.println("Copiato " + imageFile.getName() + " in " + patient.getPath() + File.separator +"inputs" + File.separator + "biofilmno");
        }
    }

    /**
     * Creates an image view to be displayed in a tile.
     * If right-clicked , the image is opened in default image preview application on medic's computer.
     * @param imageFile
     * @return
     */
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

    @FXML
    private void countCells (ActionEvent event) {
        patient.addAllElements();
        int sum = 0;
        StringBuilder cells = new StringBuilder("Cells List:\n");
        for (Map.Entry<String, Cell> entry : patient.getDictionary().entrySet()) {
            cells.append("Name: " + entry.getKey() + " - Count: " + entry.getValue().getcellCount() + " - Grade: " + entry.getValue().getgrade() + ";\n");
            sum = sum + entry.getValue().getcellCount();
        }
        DialogHelper.showExpandableAlert(Alert.AlertType.INFORMATION, "Risultato citologia", "Risultato citologia", "Totale cellule: " + sum, cells.toString());

    }


    /*
     *
     * START SIDEBAR COMMANDS
     *
     */

    @FXML
    private void anamnesiCaller(ActionEvent event)  throws IOException{
       
            if(clientSocket != null) {
            clientSocket.close();
        }
        
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
    private void insertCells(ActionEvent event)  throws IOException{
        if(clientSocket != null) {
            clientSocket.close();
        }

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
        if(clientSocket != null) {
            clientSocket.close();
        }

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
    private void diagnosisCaller(ActionEvent event)  throws IOException{
        if(clientSocket != null) {
            clientSocket.close();
        }

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
        if(clientSocket != null) {
            clientSocket.close();
        }

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
        if(clientSocket != null) {
            clientSocket.close();
        }

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Load.fxml"));
        try {
            Loader.load();
        } catch (IOException ex){
            Logger.getLogger(RevisioneController.class.getName()).log(Level.SEVERE,null, ex);
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
        System.out.println("SAVING SESSION");
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
