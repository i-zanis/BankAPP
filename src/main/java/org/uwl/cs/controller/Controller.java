package org.uwl.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static org.uwl.cs.Main.cust1;
import static org.uwl.cs.Transaction.withdraw;
import static org.uwl.cs.Util.*;
import static org.uwl.cs.Util.WINDOW_ICON;

public class Controller {

    public DialogPane transferDialog;
    public TextField transferAccNameTf;
    public TextField transferAccNoTf;
    public TextField transferAmountTf;
    public Label transferError;

    public void press(ActionEvent actionEvent) throws IOException {
        transferDialog.setVisible(true);
        // removes the hard edges of the Dialog(scene's rectangular corners)
        transferDialog.getScene().setFill(Color.TRANSPARENT);
    }
    public void transferAccept(ActionEvent actionEvent) throws IOException {
        // transferAccNameTf.getText();
        // transferAccNo.getText();
        // clears old errors from the label
        transferDialog.getScene().setFill(Color.TRANSPARENT);
        clearLabel(transferError);
        resetTextFieldColor(transferAccNameTf,transferAccNoTf,transferAmountTf);
        if (isEmpty(transferAccNameTf)) errorToLabel(transferAccNameTf,transferError,"Account Name required");
        else if (!isLetter(transferAccNameTf)) errorToLabel(transferAccNameTf,transferError,"Incorrect Account Name");

        else if (isEmpty(transferAccNoTf)) errorToLabel(transferAccNoTf,transferError,"Account Number required");
        else if (!isDigit(transferAccNoTf)) errorToLabel(transferAccNoTf,transferError,"Incorrect Account No");
        else if (!validateAccountNo(transferAccNoTf)) errorToLabel(transferAccNoTf,transferError,"Invalid length");

        else if (isEmpty(transferAmountTf)) errorToLabel(transferAmountTf,transferError,"Transfer Amount required");
        else if (!isDigit(transferAmountTf)) errorToLabel(transferAmountTf,transferError,"Enter a number");

        try {
           // withdraw(cust1, transferAmountTf.getText());
           // transferDialog.setVisible(false);
            System.out.println("The new balance is : " + cust1.getBalance());
        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }
    public void transferDecline(ActionEvent actionEvent) throws IOException {
        transferDialog.getScene().setFill(Color.TRANSPARENT);
       transferDialog.setVisible(false);
    


    }

    // ****** TextField Methods ******
    public static Boolean isEmpty(TextField textField) {
        return textField.getText().equals(EMPTY);
    }
    public static void highlightRed(TextField textfield) {
        textfield.setStyle("-fx-border-color: red;");
        textfield.requestFocus();
    }
    public static void errorToLabel(TextField textfield, Label label, String string) {
        textfield.setStyle("-fx-border-color: red;");
        textfield.requestFocus();
        label.setText(string);
    }
    public static Boolean isLetter(TextField textField) {
        return textField.getText().matches("[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z])$");
    }
    public static Boolean isDigit(TextField textField) {
        return textField.getText().matches("[0-9]+");
    }
    public static Boolean validateAccountNo(TextField textField) {
        return textField.getText().length() > 8;
    }
    // clears the label
    public static void clearLabel(Label label) {
        label.setText(EMPTY);
    }
    public static void resetTextFieldColor(TextField ... textFields) {
        for (TextField textField : textFields) {
            textField.setStyle("-fx-border-color: #2b5c50;");
        }
    }
}
