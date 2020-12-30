package org.uwl.cs.controller;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.uwl.cs.Main.primaryStage;
import static org.uwl.cs.Util.LOGIN;

public class LoginController implements Initializable {

    public Hyperlink forgotPasswordHyperlink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void getBrowser(ActionEvent actionEvent) throws IOException, URISyntaxException {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.uwl.ac.uk/current-students/new-students/changing-your-password"));
            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }

}
