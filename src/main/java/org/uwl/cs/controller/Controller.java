package org.uwl.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.uwl.cs.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.uwl.cs.Main.currentCustomer;
import static org.uwl.cs.Transaction.withdraw;
import static org.uwl.cs.Util.*;
import static org.uwl.cs.Util.WINDOW_ICON;
import static org.uwl.cs.Utils.getTime;

public class Controller implements Initializable {

    public DialogPane transferDialog;
    public TextField transferAccNameTf;
    public TextField transferAccNoTf;
    public TextField transferAmountTf;
    public Label transferError;
    public DialogPane withdrawDialog;
    public  Label timeLabel;
    public  Label nameLabel;
    public  Label accountLabel;
    public  Label balanceLabel;

    // // ***** Transfer Dialog methods *****
    public void getTransferDialog(ActionEvent actionEvent) throws IOException {
        transferDialog.setVisible(true);
        // There is some strange behaviour in the scene of the dialog, you can see the hard edges which set to be round
        // however when you close the dialog and reopen it they appear. Officially this is a limitation of JavaFX unless
        // you hardcode a solution. It works at present therefore I will keep it.
        transferDialog.getScene().setFill(Color.TRANSPARENT);
        transferDialog.getScene().setFill(Color.DARKSALMON);
    }
    public void transferAccept(ActionEvent actionEvent) throws IOException {
        transferDialog.getScene().setFill(Color.TRANSPARENT);
        clearLabel(transferError);
        resetTextFieldColor(transferAccNameTf, transferAccNoTf, transferAmountTf);
        if (isEmpty(transferAccNameTf)) errorToLabel(transferAccNameTf, transferError, "Account Name required");
        else if (!isLetter(transferAccNameTf)) errorToLabel(transferAccNameTf, transferError, "Invalid Account Name");

        else if (isEmpty(transferAccNoTf)) errorToLabel(transferAccNoTf, transferError, "Account Number required");
        else if (!isDigit(transferAccNoTf)) errorToLabel(transferAccNoTf, transferError, "Invalid Account No");
        else if (!validateAccountNo(transferAccNoTf)) errorToLabel(transferAccNoTf, transferError, "Invalid length");

        else if (isEmpty(transferAmountTf)) errorToLabel(transferAmountTf, transferError, "Transfer Amount required");
        else if (!isDigit(transferAmountTf)) errorToLabel(transferAmountTf, transferError, "Enter an amount");
        else {
            try {
                if (withdraw(transferAmountTf.getText())) {
                    updateScreenInformation();
                    transferDialog.setVisible(false);
                    resetLabelsAndTextFields();
                }
                else {
                    errorToLabel(transferAmountTf,transferError,"Insufficient funds");
                }
                System.out.println("The new balance is : " + currentCustomer.getBalance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void transferDecline(ActionEvent actionEvent) throws IOException {
        //transferDialog.getScene().setFill(Color.TRANSPARENT);
        transferDialog.setVisible(false);
    


    }
    // ***** Deposit Dialog Methods  *****
    public void getDepositDialog(ActionEvent actionEvent) throws IOException {
        transferDialog.setVisible(true);
        // There is some strange behaviour in the scene of the dialog, you can see the hard edges which set to be round
        // however when you close the dialog and reopen it they appear. Officially this is a limitation of JavaFX unless
        // you hardcode a solution. It works at present therefore I will keep it.
        transferDialog.getScene().setFill(Color.TRANSPARENT);
        transferDialog.getScene().setFill(Color.DARKSALMON);
    }
    public void transferAccept(ActionEvent actionEvent) throws IOException {
        transferDialog.getScene().setFill(Color.TRANSPARENT);
        clearLabel(transferError);
        resetTextFieldColor(transferAccNameTf, transferAccNoTf, transferAmountTf);
        if (isEmpty(transferAccNameTf)) errorToLabel(transferAccNameTf, transferError, "Account Name required");
        else if (!isLetter(transferAccNameTf)) errorToLabel(transferAccNameTf, transferError, "Invalid Account Name");

        else if (isEmpty(transferAccNoTf)) errorToLabel(transferAccNoTf, transferError, "Account Number required");
        else if (!isDigit(transferAccNoTf)) errorToLabel(transferAccNoTf, transferError, "Invalid Account No");
        else if (!validateAccountNo(transferAccNoTf)) errorToLabel(transferAccNoTf, transferError, "Invalid length");

        else if (isEmpty(transferAmountTf)) errorToLabel(transferAmountTf, transferError, "Transfer Amount required");
        else if (!isDigit(transferAmountTf)) errorToLabel(transferAmountTf, transferError, "Enter an amount");
        else {
            try {
                if (withdraw(transferAmountTf.getText())) {
                    updateScreenInformation();
                    transferDialog.setVisible(false);
                    resetLabelsAndTextFields();
                }
                else {
                    errorToLabel(transferAmountTf,transferError,"Insufficient funds");
                }
                System.out.println("The new balance is : " + currentCustomer.getBalance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    // ****** TextField Methods ******
    public static Boolean isEmpty(TextField textField) {
        return textField.getText().equals(EMPTY_STRING);
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
        return textField.getText().length() >= 8;
    }
    // clears the label
    public static void clearLabel(Label label) {
        label.setText(EMPTY_STRING);
    }
    // clears Labels and TextFields, used when pressing NO/YES on dialog to be ready for use when they open again
    public  void resetLabelsAndTextFields() {
        transferAccNameTf.setText(EMPTY_STRING);
        transferAccNoTf.setText(EMPTY_STRING);
        transferAmountTf.setText(EMPTY_STRING);
    }
    public void clearAllDialogLabels() {}
    public static void resetTextFieldColor(TextField ... textFields) {
        for (TextField textField : textFields) {
            textField.setStyle("-fx-border-color: #2b5c50;");
        }
    }
    public void updateScreenInformation() {
        nameLabel.setText(currentCustomer.getFirstName() + EMPTY_STRING + currentCustomer.getLastName());
        accountLabel.setText(currentCustomer.getAccountNumber() + EMPTY_STRING); // converts to string
        balanceLabel.setText(currentCustomer.getBalance() + EMPTY_STRING); // converts to string
        timeLabel.setText(LAST_UPDATE + getTime());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
