package org.uwl.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static org.uwl.cs.FilePaths.LOGIN;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        // sets the initial FXML file to be loaded
        Parent root = FXMLLoader.load(getClass().getResource(LOGIN));
        // Create a scene and set the initial(root) FXML
        Scene logInScene = new Scene(root);
        // Clear any previous CSS to avoid inconsistencies
        logInScene.getStylesheets().clear();
        // Add CSS from separate Style Sheet
       // logInScene.getStylesheets().add(styleCSS);
        // Sets the title of the window
        primaryStage.setTitle("Bank of West London");
        // Sets the icon of the window in the Taskbar and top window bar
        //primaryStage.getIcons().add(new Image(dragonPic));
        // Sets the Scene to the Stage
        primaryStage.setScene(logInScene);
        // Shows the Stage
        primaryStage.show();
        // makes the window not resizable
        primaryStage.setResizable(false);
    }
}

