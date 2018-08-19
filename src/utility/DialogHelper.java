package utility;


import views.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import static javafx.scene.control.Alert.AlertType;



public class DialogHelper {

    /**
     * Static method to generate an Alert.
     * alertType allows to choose the type of Alert that is going to be displayed.
     * @param alertType
     * @param title
     * @param header
     * @param content
     */
    public static void showAlert(AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("safari.png")));

        alert.showAndWait();
    }

    /**
     * Static method to generate an Alert.
     * alertType allows to choose the type of Alert that is going to be displayed.
     * Mostly used to print StackTrace in case of exceptions.
     * @param alertType
     * @param title
     * @param header
     * @param content
     * @param expandableContent
     */
    public static void showExpandableAlert(AlertType alertType, String title, String header, String content,
                                           String expandableContent) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        TextArea textArea = new TextArea(expandableContent);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        VBox.setVgrow(textArea, Priority.ALWAYS);
        alert.getDialogPane().setExpandableContent(new VBox(textArea));
        alert.getDialogPane().setExpanded(true);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("safari.png")));

        alert.showAndWait();
    }

    /**
     * Static method to generate a confirmation Dialog.
     * @param title
     * @param header
     * @param content
     * @return
     */
    public static boolean showConfirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("safari.png")));

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * Static method to generate an expandable confirmation Dialog.
     * @param title
     * @param header
     * @param content
     * @param expandableContent
     * @return
     */
    public static boolean showExpandableConfirmationDialog(String title, String header, String content,
                                                           String expandableContent) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        TextArea textArea = new TextArea(expandableContent);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        VBox.setVgrow(textArea, Priority.ALWAYS);
        alert.getDialogPane().setExpandableContent(new VBox(textArea));
        alert.getDialogPane().setExpanded(true);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("safari.png")));

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * Static method to generate a text input Dialog.
     * @param title
     * @param header
     * @param content
     * @param hint
     * @return
     */
    @Nullable
    public static String showTextInputDialog(String title, String header, String content, String hint) {
        TextInputDialog dialog = new TextInputDialog(hint);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("safari.png")));

        Optional<String> result = dialog.showAndWait();
        return result.isPresent() ? result.get() : null;
    }

    /**
     * Static method to generate a Dialog to display Exceptions.
     * @param e
     */
    public static void showException(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String exceptionText = printWriter.toString();

        showExpandableAlert(AlertType.ERROR, "Cells Explorer", "Something went wrong!", e.toString(), exceptionText);
    }
}
