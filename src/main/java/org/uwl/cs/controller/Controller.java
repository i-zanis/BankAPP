package org.uwl.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.uwl.cs.model.Constant.*;
import static org.uwl.cs.model.Database.connect;
import static org.uwl.cs.Main.currentCustomer;
import static org.uwl.cs.model.Transaction.*;
import static org.uwl.cs.model.Utility.getTime;
import static org.uwl.cs.model.Utility.selectProfileIcon;

public class Controller implements Initializable {
    public BorderPane appWindow;
    public DialogPane transferDialog;
    public TextField transferAccNoTf;
    public TextField transferAmountTf;
    public TextField transferAccNameTf;
    public Label transferErrorLabel;
    public DialogPane withdrawDialog;
    public Label timeLabel;
    public Label nameLabel;
    public Label accountLabel;
    public Label balanceLabel;
    public DialogPane depositDialogue;
    public TextField depositTf;
    public Label depositErrorLabel;
    public DialogPane interestDialog;
    public Label monthlyInterestLabel;
    public Label annualInterestLabel;
    public TextField withdrawTf;
    public Label withdrawErrorLabel;
    public DialogPane loanDialog;
    public TextField loanAmountTf;
    public TextField loanYearsTf;
    public Label loanAmountLabel;
    public Label loanLabel;
    public Label loanErrorLabel;
    public Label monthlyPaymentLabel;
    public Circle midCircleRed;
    public Circle midCircleGreen;
    public Button transferButton;
    public Circle profileCircle1;
    public Circle profileCircle2;
    public Button profileButton1;
    public Button profileButton2;
    public String modifiedAccNum = "";

    public static void highlightRed(TextField textfield) {
        textfield.setStyle("-fx-border-color: red;");
        textfield.requestFocus();
        textfield.setText(EMPTY_STRING);
    }

    public static void errorToLabel(TextField textfield, Label label, String string) {
        textfield.setStyle("-fx-border-color: red;");
        textfield.requestFocus();
        textfield.setText(EMPTY_STRING);
        label.setText(string);
    }

    public static Boolean isLetter(TextField textField) {
        return textField.getText().matches("[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z])$");
    }

    public static Boolean isDigit(TextField textField) {
        return textField.getText().matches("^[-+]?[0-9]*\\.?[0-9]+$");
    }

    public static Boolean validateAccountNo(TextField textField) {
        return textField.getText().length() >= 8;
    }

    // clears the label
    public static void clearLabel(Label label) {
        label.setText(EMPTY_STRING);
    }

    public static void resetTextFieldColor(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setStyle("-fx-border-color: #2b5c50;");
        }
    }

    // ****** TextField & Label Methods ******
    public static Boolean isEmpty(TextField textField) {
        return textField.getText().equals(EMPTY_STRING);
    }

    // ***** Loan Dialog methods *****
    public void getLoanDialog(ActionEvent actionEvent) throws IOException {
        loanDialog.setVisible(true);
        // There is some strange behaviour in the scene of the dialog, you can see the hard edges which set to be round
        // however when you close the dialog and reopen it they appear. Officially this is a limitation of JavaFX unless
        // you hardcode a solution. It works at present therefore I will keep it.
        loanDialog.getScene().setFill(Color.TRANSPARENT);
        loanDialog.getScene().setFill(Color.DARKSALMON);
    }

    public void loanAccept(ActionEvent actionEvent) throws IOException {
        loanDialog.getScene().setFill(Color.TRANSPARENT);
        clearLabel(loanErrorLabel);
        resetTextFieldColor(loanAmountTf, loanYearsTf);
        if (isEmpty(loanAmountTf)) errorToLabel(loanAmountTf, loanErrorLabel, "Loan amount required");
        else if (!isDigit(loanAmountTf)) errorToLabel(loanAmountTf, loanErrorLabel, "Invalid number");

        else if (isEmpty(loanYearsTf)) errorToLabel(loanYearsTf, loanErrorLabel, "Years required");
        else if (!isDigit(loanYearsTf)) errorToLabel(loanYearsTf, loanErrorLabel, "Invalid number");
        else {
            try {
                loanAmountLabel.setText(getMonthlyLoanRepayment(loanAmountTf.getText(), loanYearsTf.getText()));
                updateScreenInformation();
                monthlyPaymentLabel.setVisible(true);
                updateScreenInformation();
                resetLabelsAndTextFields();
                System.out.println("The new balance is : " + currentCustomer.getBalance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loanDecline(ActionEvent actionEvent) throws IOException {
        //loanDialog.getScene().setFill(Color.TRANSPARENT);
        loanDialog.setVisible(false);
        monthlyPaymentLabel.setVisible(false);
        resetLabelsAndTextFields();
        // this has to be placed here because the Accept doesn't close the dialog after press therefore the amount has
        // to be reset when you press decline and be empty on the next getLoanDialog()
        loanAmountLabel.setText(EMPTY_STRING);
    }

    // ***** Withdraw Dialog methods *****
    public void getWithdrawDialog(ActionEvent actionEvent) throws IOException {
        withdrawDialog.setVisible(true);
        // There is some strange behaviour in the scene of the dialog, you can see the hard edges which set to be round
        // however when you close the dialog and reopen it they appear. Officially this is a limitation of JavaFX unless
        // you hardcode a solution. It works at present therefore I will keep it.
        withdrawDialog.getScene().setFill(Color.TRANSPARENT);
        withdrawDialog.getScene().setFill(Color.DARKSALMON);

    }

    public void withdrawAccept(ActionEvent actionEvent) throws IOException {
        withdrawDialog.getScene().setFill(Color.TRANSPARENT);
        clearLabel(withdrawErrorLabel);
        resetTextFieldColor(withdrawTf);
        if (isEmpty(withdrawTf)) errorToLabel(withdrawTf, withdrawErrorLabel, "Amount required");
        else if (!isDigit(withdrawTf)) errorToLabel(withdrawTf, withdrawErrorLabel, "Invalid number");
        else {
            try {
                if (withdraw(withdrawTf.getText())) {
                    updateScreenInformation();
                    withdrawDialog.setVisible(false);
                    resetLabelsAndTextFields();
                } else {
                    errorToLabel(withdrawTf, withdrawErrorLabel, "Insufficient funds");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void withdrawDecline(ActionEvent actionEvent) throws IOException {
        //transferDialog.getScene().setFill(Color.TRANSPARENT);
        withdrawDialog.setVisible(false);
        resetLabelsAndTextFields();
    }

    // ***** Interest Dialog methods *****
    public void getInterestDialog(ActionEvent actionEvent) throws IOException {
        interestDialog.setVisible(true);
        // There is some strange behaviour in the scene of the dialog, you can see the hard edges which set to be round
        // however when you close the dialog and reopen it they appear. Officially this is a limitation of JavaFX unless
        // you hardcode a solution. It works at present therefore I will keep it.
        interestDialog.getScene().setFill(Color.TRANSPARENT);
        interestDialog.getScene().setFill(Color.DARKSALMON);
        monthlyInterestLabel.setText(getMonthlyInterest());
        annualInterestLabel.setText(getAnnualInterest());
        updateScreenInformation();
    }

    public void closeInterestDialog(ActionEvent actionEvent) throws IOException {
        interestDialog.setVisible(false);
        resetLabelsAndTextFields();
    }

    // ***** Transfer Dialog methods *****
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
        clearLabel(transferErrorLabel);
        resetTextFieldColor(transferAccNameTf, transferAccNoTf, transferAmountTf);
        if (isEmpty(transferAccNameTf)) errorToLabel(transferAccNameTf, transferErrorLabel, "Account Name required");
        else if (!isLetter(transferAccNameTf))
            errorToLabel(transferAccNameTf, transferErrorLabel, "Invalid Account Name");

        else if (isEmpty(transferAccNoTf)) errorToLabel(transferAccNoTf, transferErrorLabel, "Account Number required");
        else if (!isDigit(transferAccNoTf)) errorToLabel(transferAccNoTf, transferErrorLabel, "Invalid Account No");
        else if (!validateAccountNo(transferAccNoTf))
            errorToLabel(transferAccNoTf, transferErrorLabel, "Number too short");

        else if (isEmpty(transferAmountTf))
            errorToLabel(transferAmountTf, transferErrorLabel, "Transfer Amount required");
        else if (!isDigit(transferAmountTf)) errorToLabel(transferAmountTf, transferErrorLabel, "Enter an amount");
        else {
            try {
                if (withdraw(transferAmountTf.getText())) {
                    updateScreenInformation();
                    transferDialog.setVisible(false);
                    resetLabelsAndTextFields();
                } else {
                    errorToLabel(transferAmountTf, transferErrorLabel, "Insufficient funds");
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
        resetLabelsAndTextFields();
    }

    // ***** Deposit Dialog Methods  *****
    public void getDepositDialog(ActionEvent actionEvent) throws IOException {
        depositDialogue.setVisible(true);
        // There is some strange behaviour in the scene of the dialog, you can see the hard edges which set to be round
        // however when you close the dialog and reopen it they appear. Officially this is a limitation of JavaFX unless
        // you hardcode a solution. It works at present therefore I will keep it.
        depositDialogue.getScene().setFill(Color.TRANSPARENT);
        depositDialogue.getScene().setFill(Color.DARKSALMON);

    }

    public void depositAccept(ActionEvent actionEvent) throws IOException {
        depositDialogue.getScene().setFill(Color.TRANSPARENT);
        clearLabel(depositErrorLabel);
        resetTextFieldColor(depositTf);
        if (isEmpty(depositTf)) errorToLabel(depositTf, depositErrorLabel, "Deposit amount required");
        else if (!isDigit(depositTf)) errorToLabel(depositTf, depositErrorLabel, "Invalid number");
        else {
            try {
                if (deposit(depositTf.getText())) {
                    updateScreenInformation();
                    depositDialogue.setVisible(false);
                    resetLabelsAndTextFields();
                    System.out.println("The new balance is : " + currentCustomer.getBalance());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void depositDecline(ActionEvent actionEvent) throws IOException {
        //transferDialog.getScene().setFill(Color.TRANSPARENT);
        depositDialogue.setVisible(false);
        resetLabelsAndTextFields();

    }

    // clears Labels and TextFields, used when pressing NO/YES on dialog to be ready for use when they open again
    public void resetLabelsAndTextFields() {
        transferAccNameTf.setText(EMPTY_STRING);
        transferAccNoTf.setText(EMPTY_STRING);
        transferAmountTf.setText(EMPTY_STRING);
        depositTf.setText(EMPTY_STRING);
        withdrawTf.setText(EMPTY_STRING);
        loanAmountTf.setText(EMPTY_STRING);
        loanYearsTf.setText(EMPTY_STRING);
        // need to add newly implemented textfields
        resetTextFieldColor(transferAccNameTf, transferAccNoTf, transferAmountTf, depositTf, withdrawTf, loanAmountTf, loanAmountTf);
        clearAllDialogLabels();
    }

    public void clearAllDialogLabels() {
        transferErrorLabel.setText(EMPTY_STRING);
        withdrawErrorLabel.setText(EMPTY_STRING);
        depositErrorLabel.setText(EMPTY_STRING);
        loanErrorLabel.setText(EMPTY_STRING);

    }

    public void updateScreenInformation() {
        nameLabel.setText(currentCustomer.getFirstName() + SPACE + currentCustomer.getLastName());
        accountLabel.setText(adjustAccountNumber()); // converts to string
        balanceLabel.setText(getUpdatedBalance());
        timeLabel.setText(LAST_UPDATE + getTime());
        updateMidCircleColor();
    }

    public String adjustAccountNumber() {
        if (modifiedAccNum.isEmpty())
            // EMPTY_STRING to avoid addition of the first digits
            modifiedAccNum = currentCustomer.getAccountNumber().charAt(0) + EMPTY_STRING
                    + currentCustomer.getAccountNumber().charAt(1) + EMPTY_STRING +
                    currentCustomer.getAccountNumber().charAt(2) + HYPHEN + currentCustomer.getAccountNumber().charAt(3) +
                    currentCustomer.getAccountNumber().charAt(4) + currentCustomer.getAccountNumber().charAt(5) + HYPHEN
                    + currentCustomer.getAccountNumber().charAt(6) + currentCustomer.getAccountNumber().charAt(7);
        return modifiedAccNum;
    }

    public void updateMidCircleColor() {
        if (currentCustomer.getBalance() > 1000.0) {
            midCircleGreen.setVisible(true);
            midCircleRed.setVisible(false);
        } else if (currentCustomer.getBalance() <= 100.0) {
            midCircleGreen.setVisible(false);
            midCircleRed.setVisible(true);
        } else {
            midCircleGreen.setVisible(false);
            midCircleRed.setVisible(false);
        }
    }

    public  void exitApplication(ActionEvent event) throws Exception {
        connect().close();
        System.out.println("You have been disconnected from the database.");
        try {
            Parent root = FXMLLoader.load(Controller.class.getResource(LOGIN));
            Scene logInScene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            logInScene.getStylesheets().add(CSS);
            window.setScene(logInScene);
            window.show();
        } catch (Exception e) {
            System.out.println("Error occurred while opening the Login.");
            e.printStackTrace();
        }
    }


    public void changeProfilePic() {
        profileButton1.setVisible(false);
        profileButton2.setVisible(true);
        Image image = new Image(selectProfileIcon().toURI().toString());
        profileCircle2.setFill(new ImagePattern(image));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateScreenInformation();
    }
}
