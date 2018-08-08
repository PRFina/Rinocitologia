package filemngr;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.Nullable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.*;

import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class FileView extends HBox {

    private static final String ACTION_SELECT = "select";
    private static final String ACTION_COPY = "copy";
    private static final String ACTION_MOVE = "move";
    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_OPEN = "open";

    static final DataFormat FILE_LIST = new DataFormat("FileList");


    private ListView mLeftPane;
    private ListView mRightPane;

    private ImageView iv2;

    //private TextEditor mTextEditor;

    public FileView(String initialPath,  ImageDisplay imageView) {
        File[] roots = File.listRoots();
        //String leftPanePath = roots[0].getPath();
        String leftPanePath = initialPath + File.separator + "inputs";
        // String rightPanePath = roots.length > 1 ? roots[1].getPath() : leftPanePath;
        String rightPanePath = leftPanePath;

        mLeftPane = new ListView(leftPanePath, imageView);
        mRightPane = new ListView(rightPanePath, imageView);

        mLeftPane.setFileView(this);
        mRightPane.setFileView(this);

        // Add mouse event handlers for the source
        mLeftPane.setOnDragDetected(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                writelog("Event on Source: drag detected");
                dragDetected(event, mLeftPane);
            }
        });

        mLeftPane.setOnDragOver(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Source: drag over");
                dragOver(event, mLeftPane);
            }
        });

        mLeftPane.setOnDragDropped(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Source: drag dropped");
                dragDropped(event, mLeftPane);
            }
        });

        mLeftPane.setOnDragDone(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Source: drag done");
                dragDone(event, mLeftPane);
            }
        });

        // Add mouse event handlers for the target
        mRightPane.setOnDragDetected(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                writelog("Event on Target: drag detected");
                dragDetected(event, mRightPane);
            }
        });

        mRightPane.setOnDragOver(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Target: drag over");
                dragOver(event, mRightPane);
            }
        });

        mRightPane.setOnDragDropped(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Target: drag dropped");
                dragDropped(event, mRightPane);
            }
        });

        mRightPane.setOnDragDone(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                writelog("Event on Target: drag done");
                dragDone(event, mRightPane);
            }
        });
























        mLeftPane.getTextField().setOnAction(e -> onTextEntered(mLeftPane.getTextField()));
        mRightPane.getTextField().setOnAction(e -> onTextEntered(mRightPane.getTextField()));






        VBox leftView = new VBox(mLeftPane.getTextField(), mLeftPane);
        VBox rightView = new VBox(mRightPane.getTextField(), mRightPane);
        mLeftPane.setFocusTraversable(true);

        VBox.setVgrow(mLeftPane, Priority.ALWAYS);
        VBox.setVgrow(mRightPane, Priority.ALWAYS);
        HBox.setHgrow(leftView, Priority.ALWAYS);
        HBox.setHgrow(rightView, Priority.ALWAYS);

        getChildren().addAll(leftView, rightView);
    }

    public ImageView getIv2() {
        return iv2;
    }

    public void setIv2(ImageView iv2) {
        this.iv2 = iv2;
    }

    public void copy() {
        if (mLeftPane.isFocused()) {
            List<Path> source = mLeftPane.getSelection();
            Path target = mRightPane.getDirectory();
            FileHelper.copy(source, target);
        } else if (mRightPane.isFocused()) {
            List<Path> source = mRightPane.getSelection();
            Path target = mLeftPane.getDirectory();
            FileHelper.copy(source, target);
        }
    }
    private void writelog(String text)
    {
        System.out.println(text + "\n");
    }

    public boolean move() {

        List<Path> source = null;
        Path target = null;

        if (mLeftPane.isFocused()) {
            source = mLeftPane.getSelection();
            target = mRightPane.getDirectory();
        } else if (mRightPane.isFocused()) {
            source = mRightPane.getSelection();
            target = mLeftPane.getDirectory();
        }
        return FileHelper.move(source, target);
    }

    public void delete() {
        ListView focusedPane = getFocusedPane();
        if (focusedPane != null) FileHelper.delete(focusedPane.getSelection());
    }

    public void rename() {
        ListView focusedPane = getFocusedPane();
        if (focusedPane != null) {
            List<Path> selection = focusedPane.getSelection();
            if (selection.size() == 1) FileHelper.rename(selection.get(0));
        }
    }

    public void createDirectory() {
        ListView focusedPane = getFocusedPane();
        if (focusedPane != null) FileHelper.createDirectory(focusedPane.getDirectory());
    }

    public void createFile() {
        ListView focusedPane = getFocusedPane();
        if (focusedPane != null) FileHelper.createFile(focusedPane.getDirectory());
    }

    public void focusTextField() {
        ListView focusedPane = getFocusedPane();
        if (focusedPane != null) focusedPane.getTextField().requestFocus();
    }
/*
    public void openHtml() {
        ListView focusedPane = getFocusedPane();
        if (focusedPane == null) return;
        List<Path> selection = focusedPane.getSelection();
        if (selection.size() != 1) return;
        File file = selection.get(0).toFile();
        //mTextEditor.open(file);
    }
*/
/*
    public void countWords() {
        Path path = getSelectedPath();
        if (path != null && path.toString().endsWith(".txt")) {
            Path resultPath = path.getParent().resolve("[Word Count] " + path.getFileName());
            try (PrintWriter printWriter = new PrintWriter(resultPath.toFile())) {
                Arrays.stream(new String(Files.readAllBytes(path), StandardCharsets.UTF_8).toLowerCase().split("\\W+"))
                        .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()))
                        .entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                        .forEach(printWriter::println);
                Desktop.getDesktop().open(resultPath.toFile());
            } catch (IOException e) {
                DialogHelper.showException(e);
            }
        }
    }
*/
    private ListView getFocusedPane() {
        if (mLeftPane.isFocused() || mLeftPane.getTextField().isFocused()) {
            return mLeftPane;
        } else if (mRightPane.isFocused() || mRightPane.getTextField().isFocused()) {
            return mRightPane;
        } else {
            return null;
        }
    }

    private ListView getFocusedPane(TextField textField) {
        if (textField == mLeftPane.getTextField()) {
            return mLeftPane;
        } else {
            return mRightPane;
        }
    }

    @Nullable
    private Path getSelectedPath() {
        ListView focusedPane = getFocusedPane();
        if (focusedPane == null) return null;
        List<Path> selection = focusedPane.getSelection();
        if (selection.size() != 1) return null;
        return selection.get(0);
    }














    private void dragDetected (MouseEvent event, ListView listView)
    {
        for (Path path:listView.getSelection()) {
            File selectedFile = new File(path.toString());
            if (selectedFile.isDirectory()) {
                listView.getSelectionModel().clearSelection(); //getSelectedItems().remove(listView.getSelectionModel().getSelectedItems().indexOf(path));
            }
        }

        // Make sure at least one item is selected
        // int selectedCount = listView.getSelectionModel().getSelectedIndices().size();
        int selectedCount = listView.getSelectionModel().getSelectedItems().size();

        if (selectedCount == 0)
        {
            event.consume();
            return;
        }

        // Initiate a drag-and-drop gesture
        Dragboard dragboard = listView.startDragAndDrop(TransferMode.COPY_OR_MOVE);

        // Put the the selected items to the dragboard
        ArrayList selectedItems = this.getSelected(listView);

        ClipboardContent content = new ClipboardContent();
        // content.put(FRUIT_LIST, selectedItems);
        content.put(FILE_LIST, selectedItems);

        dragboard.setContent(content);
        event.consume();
    }

    private void dragOver(DragEvent event, ListView listView)
    {
        // If drag board has an ITEM_LIST and it is not being dragged
        // over itself, we accept the MOVE transfer mode
        Dragboard dragboard = event.getDragboard();

        if (event.getGestureSource() != listView) {
            if (dragboard.hasContent(FILE_LIST)) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

            }
        }
        event.consume();
    }

    @SuppressWarnings("unchecked")
    private void dragDropped(DragEvent event, ListView listView)
    {
        boolean dragCompleted = false;

        // Transfer the data to the target
        Dragboard dragboard = event.getDragboard();

        if(dragboard.hasContent(FILE_LIST))
        {
            if (move()) {
                ArrayList list = (ArrayList)dragboard.getContent(FILE_LIST);
                listView.getItems().addAll(list);
                // Data transfer is successful
                dragCompleted = true;
            }
        }

        // Data transfer is not successful
        event.setDropCompleted(dragCompleted);
        event.consume();

    }

    private void dragDone(DragEvent event, ListView listView)
    {
        // Check how data was transfered to the target
        // If it was moved, clear the selected items
        TransferMode tm = event.getTransferMode();

        if (tm == TransferMode.MOVE)
        {
            removeSelectedFruits(listView);
        }

        event.consume();
    }

    private void removeSelectedFruits(ListView listView)
    {
        // Get all selected Fruits in a separate list to avoid the shared list issue
        List selectedList = new ArrayList<>();

        for(Path path : listView.getSelection())
        {
            selectedList.add(path);
        }

        // Clear the selection
        listView.getSelectionModel().clearSelection();
        // Remove items from the selected list
        listView.getItems().removeAll(selectedList);
    }





























    private ArrayList getSelected(ListView listView)
    {
        // Return the list of selected Fruit in an ArratyList, so it is
        // serializable and can be stored in a Dragboard.
        ArrayList list = new ArrayList<>(listView.getSelectionModel().getSelectedItems());

        return list;
    }

    private void onTextEntered(TextField textField) {
        ListView focusedPane = getFocusedPane(textField);
        String command = textField.getText().trim();
        File file = new File(command);
        if (file.exists()) {
            focusedPane.openFile(file);
            focusedPane.requestFocus();
        } else if (command.startsWith(ACTION_SELECT)) {
            String regex = command.substring(ACTION_SELECT.length()).trim();
            focusedPane.select(regex);
            focusedPane.requestFocus();
        } else if (command.startsWith(ACTION_COPY)) {
            String regex = command.substring(ACTION_COPY.length()).trim();
            focusedPane.select(regex);
            focusedPane.requestFocus();
            copy();
        } else if (command.startsWith(ACTION_MOVE)) {
            String regex = command.substring(ACTION_MOVE.length()).trim();
            focusedPane.select(regex);
            focusedPane.requestFocus();
            move();
        } else if (command.startsWith(ACTION_DELETE)) {
            String regex = command.substring(ACTION_DELETE.length()).trim();
            focusedPane.select(regex);
            focusedPane.requestFocus();
            delete();
        } else if (command.startsWith(ACTION_OPEN)) {
            String regex = command.substring(ACTION_OPEN.length()).trim();
            focusedPane.select(regex);
            focusedPane.requestFocus();
            for (Path path : focusedPane.getSelection()) {
                try {
                    Desktop.getDesktop().open(path.toFile());
                } catch (Exception e) {
                    DialogHelper.showException(e);
                }
            }
        }
        textField.setText(focusedPane.getDirectory().toString());
    }
}
