package org.uwl.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import static org.uwl.cs.Main.currentCustomer;
import static org.uwl.cs.model.Constant.*;
import static org.uwl.cs.model.Database.connect;
import static org.uwl.cs.model.Transaction.*;
import static org.uwl.cs.model.Utility.*;

public class Controller implements Initializable {
    public static ArrayList<Label[]> labelList = new ArrayList<>();
    public static ArrayList<String[]> transactionHistoryList = new ArrayList<>();
    public String modifiedAccNum = "";
    @FXML
    private BorderPane appWindow;

    @FXML
    private Circle profileCircle1;

    @FXML
    private Circle midCircleRed;

    @FXML
    private Circle midCircleGreen;

    @FXML
    private Button transferButton;

    @FXML
    private Button withdrawButton;

    @FXML
    private SVGPath depositButton;

    @FXML
    private Button profileButton2;

    @FXML
    private Circle profileCircle2;

    @FXML
    private Button profileButton1;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label accountLabel;

    @FXML
    private DialogPane transferDialog;

    @FXML
    private Button transferAcceptButton;

    @FXML
    private Button transferNoButton;

    @FXML
    private TextField transferAccNameTf;

    @FXML
    private TextField transferAccNoTf;

    @FXML
    private TextField transferAmountTf;

    @FXML
    private Label transferErrorLabel;

    @FXML
    private DialogPane depositDialogue;

    @FXML
    private Button depositAcceptButton;

    @FXML
    private Button depositNobButton;

    @FXML
    private TextField depositTf;

    @FXML
    private Label depositErrorLabel;

    @FXML
    private DialogPane interestDialog;

    @FXML
    private Button interestButtonOk;

    @FXML
    private Button depositNobButton1;

    @FXML
    private Label monthlyInterestLabel;

    @FXML
    private Label annualInterestLabel;

    @FXML
    private DialogPane withdrawDialog;

    @FXML
    private Button withdrawAcceptButton;

    @FXML
    private Button withdrawDeclineBUtton;

    @FXML
    private TextField withdrawTf;

    @FXML
    private Label withdrawErrorLabel;

    @FXML
    private DialogPane loanDialog;

    @FXML
    private Button loanAcceptButton;

    @FXML
    private Button loanNoButton;

    @FXML
    private TextField loanAmountTf;

    @FXML
    private TextField loanYearsTf;

    @FXML
    private Label loanLabel;

    @FXML
    private Label loanAmountLabel;

    @FXML
    private Label loanErrorLabel;

    @FXML
    private Label monthlyPaymentLabel;

    @FXML
    private DialogPane transactionDialog;

    @FXML
    private Label transactionDate;

    @FXML
    private Label transactionTime;

    @FXML
    private Label transactionType;

    @FXML
    private Label transactionAmount;

    @FXML
    private Separator transactionSeparator;

    @FXML
    private Label transactionDate1;

    @FXML
    private Label transactionTime1;

    @FXML
    private Label transactionType1;

    @FXML
    private Label transactionAmount1;

    @FXML
    private Separator transactionSeparator1;

    @FXML
    private Label transactionDate2;

    @FXML
    private Label transactionTime2;

    @FXML
    private Label transactionType2;

    @FXML
    private Label transactionAmount2;

    @FXML
    private Separator transactionSeparator2;

    @FXML
    private Label transactionDate3;

    @FXML
    private Label transactionTime3;

    @FXML
    private Label transactionType3;

    @FXML
    private Label transactionAmount3;

    @FXML
    private Separator transactionSeparator3;

    @FXML
    private Label transactionDate4;

    @FXML
    private Label transactionTime4;

    @FXML
    private Label transactionType4;

    @FXML
    private Label transactionAmount4;

    @FXML
    private Separator transactionSeparator4;

    @FXML
    private Label transactionDate5;

    @FXML
    private Label transactionTime5;

    @FXML
    private Label transactionType5;

    @FXML
    private Label transactionAmount5;

    @FXML
    private Separator transactionSeparator5;

    @FXML
    private Label transactionDate6;

    @FXML
    private Label transactionTime6;

    @FXML
    private Label transactionType6;

    @FXML
    private Label transactionAmount6;

    @FXML
    private Separator transactionSeparator6;

    @FXML
    private Label transactionDate7;

    @FXML
    private Label transactionTime7;

    @FXML
    private Label transactionType7;

    @FXML
    private Label transactionAmount7;

    @FXML
    private Separator transactionSeparator7;

    @FXML
    private Label transactionDate8;

    @FXML
    private Label transactionTime8;

    @FXML
    private Label transactionType8;

    @FXML
    private Label transactionAmount8;

    @FXML
    private Separator transactionSeparator8;

    @FXML
    private Label transactionDate9;

    @FXML
    private Label transactionTime9;

    @FXML
    private Label transactionType9;

    @FXML
    private Label transactionAmount9;

    @FXML
    private Separator transactionSeparator9;

    @FXML
    private Label transactionDate10;

    @FXML
    private Label transactionTime10;

    @FXML
    private Label transactionType10;

    @FXML
    private Label transactionAmount10;

    @FXML
    private Separator transactionSeparator10;

    @FXML
    private Button transactionOkButton;





    // ****** TextField & Label Methods ******
    public static void errorToLabel(TextField textfield, Label label, String string) {
        textfield.setStyle("-fx-border-color: red;");
        textfield.requestFocus();
        textfield.clear();
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
            textField.setStyle("-fx-focus-color: transparent;");
        }
    }

    public static Boolean isEmpty(TextField textField) {
        return textField.getText().equals(EMPTY_STRING);
    }
    // ***** Transaction Dialog methods *****
    public void getTransactionDialog(ActionEvent actionEvent) throws IOException {
        transactionDialog.setVisible(true);
        // There is some strange behaviour in the scene of the dialog, you can see the hard edges which set to be round
        // however when you close the dialog and reopen it they appear. Officially this is a limitation of JavaFX unless
        // you hardcode a solution. It works at present therefore I will keep it.
        transactionDialog.getScene().setFill(Color.TRANSPARENT);
        transactionDialog.getScene().setFill(Color.DARKSALMON);
        int transactionIndex = transactionHistoryList.size() - 1;
        for (int i = 0; i < labelList.size() && transactionIndex >= 0; i++) {
            for (int j = 0; j < 4; j++) {
                labelList.get(i)[j].setText(transactionHistoryList.get(transactionIndex)[j]);
              // System.out.println(transactionHistoryList.get(transactionIndex)[j]);
            }
            transactionIndex--;

        }
        updateScreenInformation();
    }

    public void closeTransactionDialog(ActionEvent actionEvent) throws IOException {
        transactionDialog.setVisible(false);
        resetLabelsAndTextFields();
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
            errorToLabel(transferAccNoTf, transferErrorLabel, "8 digits required");

        else if (isEmpty(transferAmountTf))
            errorToLabel(transferAmountTf, transferErrorLabel, "Transfer Amount required");
        else if (!isDigit(transferAmountTf)) errorToLabel(transferAmountTf, transferErrorLabel, "Enter an amount");
        else {
            try {
                if (withdraw(transferAmountTf.getText())) {
                    updateScreenInformation();
                    transactionHistoryList.add(new String[]{ getDate(),getTime(),"Money Transfer", transferAmountTf.getText()});
for (int i = 0; i < transactionHistoryList.size(); i++) {
    System.out.println("Widthdraw" + transactionHistoryList.get(i)[3]);
}
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
        transferAccNameTf.clear();
        transferAccNoTf.clear();
        transferAmountTf.clear();
        depositTf.clear();
        withdrawTf.clear();
        loanAmountTf.clear();
        loanYearsTf.clear();
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

    public void exitApplication(ActionEvent event) throws Exception {
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
            System.err.println("Error occurred while opening the Login.");
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
        for (int i = 0; i < 11; i++) {
            labelList.add(new Label[4]);
        }
        labelList.get(0)[0] = (transactionDate);
        labelList.get(0)[1] = (transactionTime);
        labelList.get(0)[2] = (transactionType);
        labelList.get(0)[3] = (transactionAmount);

        labelList.get(1)[0] = (transactionDate1);
        labelList.get(1)[1] = (transactionTime1);
        labelList.get(1)[2] = (transactionType1);
        labelList.get(1)[3] = (transactionAmount1);

        labelList.get(2)[0] = (transactionDate2);
        labelList.get(2)[1] = (transactionTime2);
        labelList.get(2)[2] = (transactionType2);
        labelList.get(2)[3] = (transactionAmount2);

        labelList.get(3)[0] = (transactionDate3);
        labelList.get(3)[1] = (transactionTime3);
        labelList.get(3)[2] = (transactionType3);
        labelList.get(3)[3] = (transactionAmount3);

        labelList.get(4)[0] = (transactionDate4);
        labelList.get(4)[1] = (transactionTime4);
        labelList.get(4)[2] = (transactionType4);
        labelList.get(4)[3] = (transactionAmount4);

        labelList.get(5)[0] = (transactionDate5);
        labelList.get(5)[1] = (transactionTime5);
        labelList.get(5)[2] = (transactionType5);
        labelList.get(5)[3] = (transactionAmount5);

        labelList.get(6)[0] = (transactionDate6);
        labelList.get(6)[1] = (transactionTime6);
        labelList.get(6)[2] = (transactionType6);
        labelList.get(6)[3] = (transactionAmount6);

        labelList.get(7)[0] = (transactionDate7);
        labelList.get(7)[1] = (transactionTime7);
        labelList.get(7)[2] = (transactionType7);
        labelList.get(7)[3] = (transactionAmount7);

        labelList.get(8)[0] = (transactionDate8);
        labelList.get(8)[1] = (transactionTime8);
        labelList.get(8)[2] = (transactionType8);
        labelList.get(8)[3] = (transactionAmount8);

        labelList.get(9)[0] = (transactionDate9);
        labelList.get(9)[1] = (transactionTime9);
        labelList.get(9)[2] = (transactionType9);
        labelList.get(9)[3] = (transactionAmount9);

        labelList.get(10)[0] = (transactionDate10);
        labelList.get(10)[1] = (transactionTime10);
        labelList.get(10)[2] = (transactionType10);
        labelList.get(10)[3] = (transactionAmount10);
    }
}
