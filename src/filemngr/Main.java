package filemngr;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//https://www.jetbrains.com/help/idea/annotating-source-code.html

import rinocitologia.*;
import utility.*;




public class Main{
    private static final KeyCombination SHORTCUT_COPY = new KeyCodeCombination(KeyCode.F5);
    private static final KeyCombination SHORTCUT_MOVE = new KeyCodeCombination(KeyCode.F6);
    private static final KeyCombination SHORTCUT_DELETE = new KeyCodeCombination(KeyCode.DELETE);
    private static final KeyCombination SHORTCUT_NEW_FILE = new KeyCodeCombination(KeyCode.N,
            KeyCombination.SHORTCUT_DOWN);
    private static final KeyCombination SHORTCUT_NEW_DIRECTORY = new KeyCodeCombination(KeyCode.N,
            KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN);
    private static final KeyCombination SHORTCUT_RENAME = new KeyCodeCombination(KeyCode.F6, KeyCombination.SHIFT_DOWN);
    private static final KeyCombination SHORTCUT_FOCUS_TEXT_FIELD = new KeyCodeCombination(KeyCode.D, KeyCombination.SHIFT_DOWN);

    private FileView mFileView;
    private ImageDisplay mImageView;


    private Prove prove;


    public void start(Stage primaryStage, Prove prove) throws Exception {

        this.prove = prove;

        VBox root = new VBox();

        mFileView = new FileView(prove.getPatient().getPath(), mImageView);


        VBox.setVgrow(mFileView, Priority.ALWAYS);

        root.getChildren().addAll(getMenuBar(), mFileView, getToolBar());

        Scene scene = new Scene(root, 840, 600);

        scene.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if (SHORTCUT_DELETE.match(e)) {
                mFileView.delete();
            } else if (SHORTCUT_NEW_FILE.match(e)) {
                mFileView.createFile();
            } else if (SHORTCUT_NEW_DIRECTORY.match(e)) {
                mFileView.createDirectory();
            } else if (SHORTCUT_RENAME.match(e)) {
                mFileView.rename();
            } else if (SHORTCUT_COPY.match(e)) {
                mFileView.copy();
            } else if (SHORTCUT_MOVE.match(e)) {
                mFileView.move();
            } else if (SHORTCUT_FOCUS_TEXT_FIELD.match(e)) {
                mFileView.focusTextField();
            }
        });

        primaryStage.setTitle("Cells Explorer");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("safari.png")));
        primaryStage.show();
    }

    public static void main(String[] args) {
        //launch(args);
        System.exit(0);
    }

    private MenuBar getMenuBar() {
        Menu fileMenu = new Menu("File");

        // Create file menu
        MenuItem newFile = new MenuItem("New File");
        newFile.setOnAction(e -> mFileView.createFile());
        newFile.setAccelerator(SHORTCUT_NEW_FILE);

        MenuItem newFolder = new MenuItem("New Folder     ");
        newFolder.setOnAction(e -> mFileView.createDirectory());
        newFolder.setAccelerator(SHORTCUT_NEW_DIRECTORY);

        MenuItem renameItem = new MenuItem("Rename");
        renameItem.setOnAction(e -> mFileView.rename());
        renameItem.setAccelerator(SHORTCUT_RENAME);

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> mFileView.delete());
        deleteItem.setAccelerator(SHORTCUT_DELETE);

        fileMenu.getItems().addAll(newFile, newFolder, renameItem, deleteItem);

        //Create helpMenu
        Menu helpMenu = new Menu("Help");
        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(e -> DialogHelper.showAlert(Alert.AlertType.INFORMATION, "About", null,
                "Cells Explorer\n\n" + "Copyright © 2018 by Francesco Lacriola\nUniversità degli studi di Bari\nhttps://github.com/Rensykes\nAdditional credits:\nhttps://github.com/konvio")
        );
        helpMenu.getItems().addAll(aboutMenuItem);

        return new MenuBar(fileMenu, helpMenu);
    }

    private ToolBar getToolBar() {
        Label labelCopy = new Label("F5 Copy");
        labelCopy.setOnMouseClicked(e -> mFileView.copy());

        Label labelMove = new Label("F6 Move");
        labelMove.setOnMouseClicked(e -> mFileView.move());

        Label labelPreview = new Label("Enter to prewiev");
        labelMove.setOnMouseClicked(e -> mFileView.move());

        return new ToolBar(labelCopy, new Separator(), labelMove, new Separator(), labelPreview);

    }

}
