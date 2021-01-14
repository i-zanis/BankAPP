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


/**
 * Basic Bank Application Demo with Graphical User Interface.
 * It supports basic transaction functionalities transfer from account to account, withdraw, deposit, yearly/monthly
 * interest, loan calculator and transaction summary.
 * The circle in the middle changes color based on the current balance.
 * <p>
 * Made in Java 14.0.2.
 * For running the application; type run or press run on the Gradle menu.
 * <p>
 * If the Program does not work please download "javafx-sdk-11.0" and add all the jar-files in
 * Javafx-sdk-11.0.2\lib\ to the global library.
 * <p>
 * VM options --module-path %java path% --add-modules javafx.controls,javafx.fxml
 * If there is an error with the root file FXML please place the above VM options as well.
 * Screenshot provided in the media Folder.
 */

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

        // connects to the Database
        Database.connect();

    }
}