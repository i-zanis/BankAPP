package org.uwl.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.uwl.cs.model.Customer;
import org.uwl.cs.model.Database;

import static org.uwl.cs.model.Constant.*;


public class Main extends Application {

    public static Stage primaryStage;
    public static Parent root;
    public static Customer currentCustomer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // sets the initial FXML file to be loaded
        root = FXMLLoader.load(getClass().getResource(LOGIN));
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

        // make the account start with 200 pounds

        //removes the window title bar from at the top
        //primaryStage.initStyle(StageStyle.UNDECORATED);

        // add UWL color in settings that also brings the logo in the middle "UWL theme"
        // you can do this by adding a different css method


    }
}