package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;


public class FileHelper {
    public static void copy(Path path, Path target) {
        List<Path> uncopiable = new ArrayList<>();
        try {
            File sourceFile = path.toFile();
            if (sourceFile.isDirectory()) {
                org.apache.commons.io.FileUtils.copyDirectoryToDirectory(sourceFile, target.toFile());
            } else {
                org.apache.commons.io.FileUtils.copyFileToDirectory(sourceFile, target.toFile());
            }
        } catch (Exception e) {
            uncopiable.add(path);
        }

        if (uncopiable.size() > 0) {
            String sourceDirectory = uncopiable.get(0).getParent().toString();
            String content = "";
            for (Path pathUncopiable : uncopiable) {
                content += pathUncopiable.toString() + System.lineSeparator();
            }
            String message = "Some files were not copied properly";
            //DialogHelper.showAlert(Alert.AlertType.INFORMATION, sourceDirectory, message, content);
        }
    }

    public static boolean move(Path path, Path targetDirectory) {
        boolean esito = false;
        List<Path> unmovable = new ArrayList<>();
        try {
            FileUtils.moveToDirectory(path.toFile(), targetDirectory.toFile(), false);
            esito = true;
        } catch (Exception e) {
            unmovable.add(path);
        }

        if (unmovable.size() > 0) {
            String sourceDirectory = unmovable.get(0).getParent().toString();
            String content = "";
            for (Path pathUnmovable : unmovable) {
                content += pathUnmovable.toString() + System.lineSeparator();
            }
            String message = "Some files were not moved properly";
            //DialogHelper.showAlert(Alert.AlertType.INFORMATION, sourceDirectory, message, content);
        }
        return esito;
    }

    public static void delete(Path path) {
        //String sourceDirectory = source.get(0).getParent().toString();

        String filesToDelete = "";
        boolean isConfirmed = true;

        if (isConfirmed) {
            List<Path> undeleted = new ArrayList<>();

            try {
                if (path.toFile().isDirectory()) {
                    FileUtils.deleteDirectory(path.toFile());
                } else {
                    FileUtils.forceDelete(path.toFile());
                }
            } catch (Exception e) {
                undeleted.add(path);
            }

            if (undeleted.size() > 0) {
                String content = "";
                for (Path pathUndeleted : undeleted) content += pathUndeleted.toString() + System.lineSeparator();
                String message = "Some files were not deleted";
                //DialogHelper.showAlert(Alert.AlertType.INFORMATION, sourceDirectory, message, content);
            }
        }
    }

}
