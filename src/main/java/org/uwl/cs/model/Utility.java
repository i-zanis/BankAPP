package org.uwl.cs.model;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.uwl.cs.Main.primaryStage;

public class Utility {


    public static File selectProfileIcon() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterImages = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().addAll(extFilterImages);
        // fileChooser.setInitialDirectory(getInitialDirectoy());
        fileChooser.setTitle("Select an image");

        File selectedImage = fileChooser.showOpenDialog(primaryStage);
        return selectedImage;
    }


    public static String getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println(LocalTime.now().format(dtf));
        return LocalTime.now().format(dtf);
    }

    public static void fadeIn(Node node) {
        DoubleProperty opacity = node.opacityProperty();
        Timeline fadeIn = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                new KeyFrame(new Duration(900), new KeyValue(opacity, 1.0))
        );
        fadeIn.play();
    }

}
