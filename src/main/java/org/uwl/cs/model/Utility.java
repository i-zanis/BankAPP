package org.uwl.cs.model;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.uwl.cs.Main.primaryStage;

/**
 * A class that contains utility methods used elsewhere.
 */
public class Utility {

    /**
     * Opens a file chooser that allows picking of one image-type file.
     */
    public static File selectProfileIcon() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterImages = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().addAll(extFilterImages);
        fileChooser.setTitle("Select an image");
        return fileChooser.showOpenDialog(primaryStage);
    }

    /**
     * Gets the current time in the specified format.
     * @return current time
     */
    public static String getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.now().format(dtf);
    }

    /**
     * Gets the current date in the specified format.
     * @return current date
     */
    public static String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        return LocalDate.now().format(dtf);
    }

    /**
     * Fade in animation for children of StackPane.
     * @param node type object
     */
    public static void fadeIn(Node node) {
        DoubleProperty opacity = node.opacityProperty();
        Timeline fadeIn = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                new KeyFrame(new Duration(900), new KeyValue(opacity, 1.0))
        );
        fadeIn.play();
    }


}
