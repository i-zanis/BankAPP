package org.uwl.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.uwl.cs.model.Constant.*;
import static org.uwl.cs.model.Database.getCustomerByEmail;
import static org.uwl.cs.model.Database.login;
import static org.uwl.cs.Main.currentCustomer;
import static org.uwl.cs.controller.Controller.*;

public class LoginController implements Initializable {

    public BorderPane appWindow;
    public Pane loginPane;
    public Pane registrationPane;
    public TextField nameRegistrationField;
    public TextField surnameRegistrationField;
    public TextField emailRegistrationField1;
    public TextField emailRegistrationField2;
    public TextField mobileRegistrationField;
    public PasswordField passwordFieldRegistration1;
    public PasswordField passwordFieldRegistration2;
    public Button registrationButton;
    public TextField emailTf;
    public PasswordField passwordTf;
    public Label loginErrorLabel;
    public Label registrationErrorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void cancelRegistration(ActionEvent event) {
        registrationPane.setVisible(false);
    }

    public void getRegistration(ActionEvent event) {
        registrationPane.setVisible(true);
    }

    public void resetLoginLabelsAndFields(ActionEvent event) {
        emailTf.setText(EMPTY_STRING);
        passwordTf.setText(EMPTY_STRING);
    }

    public void loginToApp(ActionEvent event) throws Exception {
        clearLabel(loginErrorLabel);
        resetTextFieldColor(emailTf, passwordTf);
        if (isEmpty(emailTf)) errorToLabel(emailTf, loginErrorLabel, "Email is required");
        else if (isEmpty(passwordTf)) errorToLabel(passwordTf, loginErrorLabel, "Password is required");
        else if (login(emailTf.getText(), passwordTf.getText())) {
            try {
                currentCustomer = getCustomerByEmail(emailTf.getText());
                Parent mainmenu = FXMLLoader.load(getClass().getResource(MAINMENU));
                Scene mainMenuScene = new Scene(mainmenu);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                mainMenuScene.getStylesheets().add(CSS);
                window.setScene(mainMenuScene);
                window.show();
            } catch (Exception e) {
                System.out.println("Error occurred while opening the mainmenu.fxml.");
                e.printStackTrace();
            }
        } else {
            passwordTf.setText(EMPTY_STRING);
            errorToLabel(emailTf, loginErrorLabel, "Invalid email or password");
            System.out.println("Authentication failed - bad credentials");
        }

    }

    public void register(ActionEvent event) {
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
