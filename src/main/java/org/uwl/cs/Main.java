package org.uwl.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import org.uwl.cs.Database.*;

import static org.uwl.cs.FilePaths.*;

/** This has been a very long endeavour. The greatest programming task I have ever taken to this day. Last year I swore
 * that next year(this) I would do the same exercise with a database. Unfortunately I was not very competent with mySQL
 * therefore I had to open up Introduction to Java and Algorithms by Daniel Liang to brush up and touch their premium
 * online chapters. On top of this I decided to use a new technology "Gradle" for Dependency control and find naming
 * conventions and hierarchy for folders(eg. module names, MVC format).
 *
 * Window/Task bar icon is a free sample from DesignMantic.com
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
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

        Database.connect();
        Database.updateAccount(2,1.0);
    }

}

