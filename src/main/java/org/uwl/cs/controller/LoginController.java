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

import static org.uwl.cs.Main.currentCustomer;
import static org.uwl.cs.controller.Controller.*;
import static org.uwl.cs.model.Constant.*;
import static org.uwl.cs.model.Database.*;

public class LoginController implements Initializable {

    public BorderPane appWindow;
    public Pane loginPane;
    public TextField emailTf;
    public PasswordField passwordTf;
    public Label loginErrorLabel;
    public Pane registrationPane;
    public TextField registrationNameTf;
    public TextField registrationLastNameTf;
    public TextField registrationEmailTf1;
    public TextField registrationEmailTf2;
    public TextField registrationMobileTf;
    public PasswordField registrationPasswordTf1;
    public PasswordField registrationPasswordTf2;
    public Button registrationButton;
    public Button cancelButton;
    public Label registrationErrorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void getLogin() {
        registrationPane.setVisible(false);
        registrationPane.setDisable(true);
        loginPane.setDisable(false);
    }

    public void getRegistration() {
        registrationPane.setVisible(true);
        registrationPane.setDisable(false);
        loginPane.setDisable(true);
    }

    public void resetLoginLabelsAndFields(ActionEvent event) {
        emailTf.clear();
        passwordTf.clear();
    }

    public void loginToApp(ActionEvent event) throws Exception {
        clearLabel(loginErrorLabel);
        resetTextFieldColor(emailTf, passwordTf);
        if (isEmpty(emailTf)) errorToLabel(emailTf, loginErrorLabel, "Email is required");
        else if (isEmpty(passwordTf)) errorToLabel(passwordTf, loginErrorLabel, "Password is required");
        else if (login(emailTf.getText(), passwordTf.getText())) {
            try {
                currentCustomer = getCustomerByEmail(emailTf.getText());
                Parent mainMenu = FXMLLoader.load(getClass().getResource(MAINMENU));
                Scene mainMenuScene = new Scene(mainMenu);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                mainMenuScene.getStylesheets().add(CSS);
                window.setScene(mainMenuScene);
                window.show();
            } catch (Exception e) {
                System.out.println("Error occurred while opening the mainmenu.fxml.");
                e.printStackTrace();
            }
        } else {
            passwordTf.clear();
            errorToLabel(emailTf, loginErrorLabel, "Invalid email or password");
            System.out.println("Authentication failed - bad credentials");
        }
    }

    public void register(ActionEvent event) throws Exception {
        clearLabel(registrationErrorLabel);
        resetTextFieldColor(registrationNameTf, registrationLastNameTf, registrationEmailTf1,
                registrationEmailTf2,
                registrationMobileTf, registrationPasswordTf1, registrationPasswordTf2);
        try {
            // name check
            if (isEmpty(registrationNameTf))
                errorToLabel(registrationNameTf, registrationErrorLabel, "Name required");
            else if (!isLetter(registrationNameTf))
                errorToLabel(registrationNameTf, registrationErrorLabel, "Invalid name");
            else if (isEmpty(registrationLastNameTf))
                errorToLabel(registrationLastNameTf, registrationErrorLabel, "Last name required");
            else if (!isLetter(registrationLastNameTf))
                errorToLabel(registrationLastNameTf, registrationErrorLabel, "Invalid last name");
                // email check
            else if (isEmpty(registrationEmailTf1))
                errorToLabel(registrationEmailTf1, registrationErrorLabel, "Email required");
            else if (!validateEmail(registrationEmailTf1.getText())) {
                errorToLabel(registrationEmailTf1, registrationErrorLabel, "Invalid email");
                registrationEmailTf2.setText(EMPTY_STRING);
            }
            else if (isEmpty(registrationEmailTf2))
                errorToLabel(registrationEmailTf2, registrationErrorLabel, "Confirm email");
            else if (!registrationEmailTf1.getText().equals(registrationEmailTf2.getText())) {
                errorToLabel(registrationEmailTf2, registrationErrorLabel, "Email does not match");
            }
            // mobile check
            else if (isEmpty(registrationMobileTf))
                errorToLabel(registrationMobileTf, registrationErrorLabel, "Mobile number required");
            else if (!validatePhoneNumber(registrationMobileTf.getText()))
                errorToLabel(registrationMobileTf, registrationErrorLabel, "Invalid mobile number");
                // password check
            else if (isEmpty(registrationPasswordTf1))
                errorToLabel(registrationPasswordTf1, registrationErrorLabel, "Password required");
            else if (registrationPasswordTf1.getText().length() < 8)
                errorToLabel(registrationPasswordTf1, registrationErrorLabel, "Weak password");
            else if (!validatePassword(registrationPasswordTf1.getText())) {
                errorToLabel(registrationPasswordTf1, registrationErrorLabel, "Invalid password");
                registrationPasswordTf2.clear();

            } else if (isEmpty(registrationPasswordTf2))
                errorToLabel(registrationPasswordTf2, registrationErrorLabel, "Confirm password");
            else if (!registrationPasswordTf1.getText().equals(registrationPasswordTf2.getText()))
                errorToLabel(registrationPasswordTf2, registrationErrorLabel, "Password does not match");
                // account exists
            else if (existsEmail(registrationEmailTf1.getText())) {
                errorToLabel(registrationEmailTf1, registrationErrorLabel, "Email already registered");
                registrationEmailTf2.clear();
            }
            // user creation
            else if (getOrCreateUser(registrationNameTf.getText(),
                    registrationLastNameTf.getText(),
                    registrationEmailTf1.getText(),
                    registrationPasswordTf1.getText(),
                    registrationMobileTf.getText())) {
                getLogin();
                addRegistrationBonus(registrationEmailTf1.getText(), 100);

            }
            else registrationErrorLabel.setText("Unexpected error");
        } catch (Exception e) {
            System.err.println("Error occurred on registration.");
            e.printStackTrace();
        }
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
