package filemngr;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageDisplay extends VBox {
    private ImageView iv2;

    public ImageDisplay(){
        /*
        FileInputStream inputstream = null;
        try {
            inputstream = new FileInputStream("/Users/chiccolacriola/data/Pinco_Pallino/inputs/ciliate/37078068_2018050255176635_6736622569887105024_n.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        javafx.scene.image.Image image = new Image(inputstream,  300, 300, false, false);
    */

        iv2 = new ImageView();
        iv2.setFitWidth(100);
        iv2.setFitHeight(100);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);

        //iv2.setImage(image);


        setVgrow(iv2, Priority.ALWAYS);

        getChildren().addAll(iv2);

    }

    public ImageView getIv2() {return iv2;}
}
