package org.uwl.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;

import static org.uwl.cs.Database.getCustomer;
import static org.uwl.cs.Util.*;

/** This has been a very long endeavour. The greatest programming task I have ever taken to this day. Last year I swore
 * that next year(this) I would do the same exercise with a database. Unfortunately I was not very competent with mySQL
 * therefore I had to open up Introduction to Java and Algorithms by Daniel Liang to brush up and touch their premium
 * online chapters. On top of this I decided to use a new technology "Gradle" for Dependency control and find naming
 * conventions and hierarchy for folders(eg. module names, MVC format).
 *
 * I would like to thank the following websites for their free services.
 * Window/Task bar icon from DesignMantic.com
 * Icons/Images from FlatIcon.com
 * Also aspiring future Computer Engineer AG (Agnijus Botyrius) for conducting software testing for the application.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    public static Customer currentCustomer;
    @Override
    public void start(Stage primaryStage) throws Exception {
        // sets the initial FXML file to be loaded
        Parent root = FXMLLoader.load(getClass().getResource(MAINMENU));
        Scene logInScene = new Scene(root);
        // Clear any previous CSS to avoid inconsistencies
        logInScene.getStylesheets().clear();
        // Add CSS from separate Style Sheet
        logInScene.getStylesheets().add(CSS);
        // Sets the title of the window
        primaryStage.setTitle("UWL BANK");
        // Sets the icon of the window in the Taskbar and top window bar
        primaryStage.getIcons().add(new Image(WINDOW_ICON));
        // Sets the Scene to the Stage

        primaryStage.setScene(logInScene);
        // Shows the Stage
        primaryStage.show();
        // makes the window not resizable
        primaryStage.setResizable(false);
        // selectProfPhoto(primaryStage);

        Database.connect();
        currentCustomer = getCustomer(2);
        //Database.updateAccount(2, 5.0);
       // System.out.println(cust1.getBalance());
        //removes the window title bar from at the top
        //primaryStage.initStyle(StageStyle.UNDECORATED);
    }


    /**
     * Opens up a new window to find an image of choice
     *
     * @param stage The stage of the Scene
     * @return
     */
    public void selectProfPhoto(Stage stage) throws Exception {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterImages = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().addAll(extFilterImages);
        fileChooser.setTitle("Select a profile photo");
        File selectedImage = fileChooser.showOpenDialog(stage);
        Image image = new Image(new FileInputStream(selectedImage));
        stage.getIcons().clear();
        stage.getIcons().add(image);
    }

    }

