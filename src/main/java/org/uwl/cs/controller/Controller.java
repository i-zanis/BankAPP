package org.uwl.cs.controller;

import javafx.event.ActionEvent;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.uwl.cs.Main.currentCustomer;
import static org.uwl.cs.model.Constant.*;
import static org.uwl.cs.model.Database.connect;
import static org.uwl.cs.model.Database.existsCustomer;
import static org.uwl.cs.model.Transaction.*;
import static org.uwl.cs.model.Utility.*;

public class Controller implements Initializable {
    // List for labels in the transactionsDialog
    public static ArrayList<Label[]> labelList = new ArrayList<>();
    // Storage list for previous transactions
    public static ArrayList<String[]> transactionHistoryList = new ArrayList<>();
    // This is where the account number gets stored to be shown with hyphens
    public String modifiedAccNum = "";

    public BorderPane appWindow;


    public Circle profileCircle1;


    public Circle midCircleRed;


    public Circle midCircleGreen;


    public Button transferButton;


    public Button withdrawButton;


    public SVGPath depositButton;


    public Button profileButton2;


    public Circle profileCircle2;


    public Button profileButton1;


    public Label balanceLabel;


    public Label timeLabel;


    public Label nameLabel;


    public Label accountLabel;


    public DialogPane transferDialog;


    public Button transferAcceptButton;


    public Button transferNoButton;


    public TextField transferAccNameTf;


    public TextField transferAccNoTf;


    public TextField transferAmountTf;


    public Label transferErrorLabel;


    public DialogPane depositDialogue;


    public Button depositAcceptButton;


    public Button depositNobButton;


    public TextField depositTf;


    public Label depositErrorLabel;


    public DialogPane interestDialog;


    public Button interestButtonOk;


    public Button depositNobButton1;


    public Label monthlyInterestLabel;


    public Label annualInterestLabel;


    public DialogPane withdrawDialog;


    public Button withdrawAcceptButton;


    public Button withdrawDeclineBUtton;


    public TextField withdrawTf;


    public Label withdrawErrorLabel;


    public DialogPane loanDialog;


    public Button loanAcceptButton;


    public Button loanNoButton;


    public TextField loanAmountTf;


    public TextField loanYearsTf;


    public Label loanLabel;


    public Label loanAmountLabel;


    public Label loanErrorLabel;


    public Label monthlyPaymentLabel;


    public DialogPane transactionDialog;


    public Label transactionDate;


    public Label transactionTime;


    public Label transactionType;


    public Label transactionAmount;


    public Separator transactionSeparator;


    public Label transactionDate1;


    public Label transactionTime1;


    public Label transactionType1;


    public Label transactionAmount1;


    public Separator transactionSeparator1;


    public Label transactionDate2;


    public Label transactionTime2;


    public Label transactionType2;


    public Label transactionAmount2;


    public Separator transactionSeparator2;


    public Label transactionDate3;


    public Label transactionTime3;


    public Label transactionType3;


    public Label transactionAmount3;


    public Separator transactionSeparator3;


    public Label transactionDate4;


    public Label transactionTime4;


    public Label transactionType4;


    public Label transactionAmount4;


    public Separator transactionSeparator4;


    public Label transactionDate5;


    public Label transactionTime5;


    public Label transactionType5;


    public Label transactionAmount5;


    public Separator transactionSeparator5;


    public Label transactionDate6;


    public Label transactionTime6;


    public Label transactionType6;


    public Label transactionAmount6;


    public Separator transactionSeparator6;


    public Label transactionDate7;


    public Label transactionTime7;


    public Label transactionType7;


    public Label transactionAmount7;


    public Separator transactionSeparator7;


    public Label transactionDate8;


    public Label transactionTime8;


    public Label transactionType8;


    public Label transactionAmount8;


    public Separator transactionSeparator8;


    public Label transactionDate9;


    public Label transactionTime9;


    public Label transactionType9;


    public Label transactionAmount9;


    public Separator transactionSeparator9;


    public Label transactionDate10;


    public Label transactionTime10;


    public Label transactionType10;


    public Label transactionAmount10;


    public Separator transactionSeparator10;


    public Button transactionOkButton;
    public TextField transferAccFirstNameTf;
    public TextField transferAccLastNameTf;
    public DialogPane settingsDialog;


    // ****** TextField & Label Methods ******

    /**
     * Adds items to transaction history
     *
     * @param transactionType Transaction type Transfer/Deposit/Withdrawal
     * @param textfield       The TextField that holds the amount
     */
    public static void addToTransactionHistory(String transactionType, TextField textfield) {
        transactionHistoryList.add(new String[]{getDate(), getTime(), transactionType, textfield.getText()});
        for (int i = 0; i < transactionHistoryList.size(); i++) {
        }
    }

    /**
     * Displays the transaction history saved in transactionHistoryList in reverse in the designated labels.
     * Applies colors to the text based on the transaction.
     */
    public static void displayTransactionHistory() {
        // gets the last index because the list will be displayed in reverse last == most recent -> displayed first
        int transactionIndex = transactionHistoryList.size() - 1;
        for (int i = 0; i < labelList.size() && transactionIndex >= 0; i++) {
            for (int j = 0; j < 4; j++) {
                labelList.get(i)[j].setText(transactionHistoryList.get(transactionIndex)[j]);
                // this block checks the type of transaction to change the color of the amount
                if (j == 3) {
                    if (transactionHistoryList.get(transactionIndex)[2].equals("Withdrawal") ||
                            transactionHistoryList.get(transactionIndex)[2].contains("Transfer:")) {
                        labelList.get(i)[j].setTextFill(Color.RED);
                        labelList.get(i)[j].setText(SIGN_MINUS + labelList.get(i)[j].getText());
                    }
                    if (transactionHistoryList.get(transactionIndex)[2].equals("Deposit")) {
                        labelList.get(i)[j].setTextFill(Color.GREEN);
                        labelList.get(i)[j].setText(SIGN_PLUS + labelList.get(i)[j].getText());
                    }
                }
            }
            transactionIndex--;
        }
    }

    /**
     * Displays desired error message and turns the color of TextField's border to red.
     *
     * @param textfield any type of TextField
     * @param label     an Error Label
     * @param string    desired error message on the Label
     */
    public static void errorToLabel(TextField textfield, Label label, String string) {
        textfield.setStyle("-fx-border-color: red;");
        textfield.requestFocus();
        textfield.clear();
        label.setText(string);
    }

    /**
     * Checks if TextField contains only letters & spaces.
     *
     * @param textField any TextField
     * @return true if only Letters or spaces
     */
    public static Boolean isLetter(TextField textField) {
        return textField.getText().matches("^[a-zA-Z\\s]+");
    }

    /**
     * Checks if TextField is only digits.
     *
     * @param textField any TextField
     * @return true if only digits & [+-]
     */
    public static Boolean isDigit(TextField textField) {
        return textField.getText().matches("^[-+]?[0-9]*\\.?[0-9]+$");
    }

    /**
     * Checks if TextField has 8 digits.
     *
     * @param textField any TextField
     * @return true if >= 8 digits
     */
    public static Boolean validateAccountNo(TextField textField) {
        return textField.getText().length() >= 8;
    }

    /**
     * Clears any number of Labels. Used every time [OK][ACCEPT] buttons are pressed to clear previous error messages.
     *
     * @param labels error Labels
     */
    public static void clearLabel(Label... labels) {
        for (Label label : labels) {
            label.setText(EMPTY_STRING);
        }
    }

    /**
     * Resets the color of any number of TextFields.
     * It sets them back to default #2b5c50 ("greennish") after they have been turned red by an error.
     *
     * @param textFields TextFields
     */
    public static void resetTextFieldColor(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setStyle("-fx-border-color: #2b5c50;");
            textField.setStyle("-fx-focus-color: transparent;");
        }
    }

    /**
     * Checks if TextField is empty.
     *
     * @param textField any TextField
     * @return true if empty
     */
    public static Boolean isEmpty(TextField textField) {
        return textField.getText().equals(EMPTY_STRING);
    }

    /**
     * Shows the settingsDialog.
     */
    // ***** Settings Dialog methods *****
    public void getSettingsDialog() {
        settingsDialog.setVisible(true);
        // There is some strange behaviour in the scene of the dialog, you can see the hard edges which set to be round
        // however when you close the dialog and reopen it they appear. Officially this is a limitation of JavaFX unless
        // you hardcode a solution. It works at present therefore I will keep it.
        settingsDialog.getScene().setFill(Color.TRANSPARENT);
        settingsDialog.getScene().setFill(Color.DARKSALMON);
        displayTransactionHistory();
        updateScreenInformation();
    }

    /**
     * Closes the settingsDialog.
     */
    public void closeSettingsDialog() {
        settingsDialog.setVisible(false);
        resetLabelsAndTextFields();
    }

    // ***** Transaction Dialog methods *****

    /**
     * Shows the transactionDialog.
     */
    public void getTransactionDialog() {
        transactionDialog.setVisible(true);
        // There is some strange behaviour in the scene of the dialog, you can see the hard edges which set to be round
        // however when you close the dialog and reopen it they appear. Officially this is a limitation of JavaFX unless
        // you hardcode a solution. It works at present therefore I will keep it.
        transactionDialog.getScene().setFill(Color.TRANSPARENT);
        transactionDialog.getScene().setFill(Color.DARKSALMON);
        displayTransactionHistory();
        updateScreenInformation();
    }

    /**
     * Closes the transactionDialog
     */
    public void closeTransactionDialog() {
        transactionDialog.setVisible(false);
        resetLabelsAndTextFields();
    }


    // ***** Loan Dialog methods *****

    /**
     * Shows the loanDialog.
     */
    public void getLoanDialog() {
        loanDialog.setVisible(true);
        // There is some strange behaviour in the scene of the dialog, you can see the hard edges which set to be round
        // however when you close the dialog and reopen it they appear. Officially this is a limitation of JavaFX unless
        // you hardcode a solution. It works at present therefore I will keep it.
        loanDialog.getScene().setFill(Color.TRANSPARENT);
        loanDialog.getScene().setFill(Color.DARKSALMON);
    }

    /**
     * Method on the Accept button of loan dialog. It gets valid input from TextFields & displays monthly paymenets.
     */
    public void loanAccept() {
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

    /**
     * Closes loanDialog.
     */
    public void loanDecline() {
        //loanDialog.getScene().setFill(Color.TRANSPARENT);
        loanDialog.setVisible(false);
        monthlyPaymentLabel.setVisible(false);
        resetLabelsAndTextFields();
        // this has to be placed here because the Accept doesn't close the dialog after press therefore the amount has
        // to be reset when you press decline and be empty on the next getLoanDialog()
        loanAmountLabel.setText(EMPTY_STRING);
    }

    // ***** Withdraw Dialog methods *****

    /**
     * Shows withdrawDialog.
     */
    public void getWithdrawDialog() {
        withdrawDialog.setVisible(true);
        // There is some strange behaviour in the scene of the dialog, you can see the hard edges which set to be round
        // however when you close the dialog and reopen it they appear. Officially this is a limitation of JavaFX unless
        // you hardcode a solution. It works at present therefore I will keep it.
        withdrawDialog.getScene().setFill(Color.TRANSPARENT);
        withdrawDialog.getScene().setFill(Color.DARKSALMON);

    }

    /**
     * Method Accept button of withdrawDialog. Checks for empty TextFields and Valid inputs.
     */
    public void withdrawAccept() {
        withdrawDialog.getScene().setFill(Color.TRANSPARENT);
        clearLabel(withdrawErrorLabel);
        resetTextFieldColor(withdrawTf);
        if (isEmpty(withdrawTf)) errorToLabel(withdrawTf, withdrawErrorLabel, "Amount required");
        else if (!isDigit(withdrawTf)) errorToLabel(withdrawTf, withdrawErrorLabel, "Invalid number");
        else if ((Float.parseFloat(withdrawTf.getText()) <= 0))
            errorToLabel(withdrawTf, withdrawErrorLabel, "Invalid amount");
        else {
            try {
                if (withdraw(withdrawTf.getText())) {
                    addToTransactionHistory("Withdrawal", withdrawTf);
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

    /**
     * Closes withdrawDialog.
     */
    public void withdrawDecline() {
        withdrawDialog.setVisible(false);
        resetLabelsAndTextFields();
    }

    // ***** Interest Dialog methods *****

    /**
     * Shows interestDialog and immediately displays the interests based on current balance.
     */
    public void getInterestDialog() {
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

    /**
     * Closes the interestDialog.
     */
    public void closeInterestDialog() {
        interestDialog.setVisible(false);
        resetLabelsAndTextFields();
    }

    // ***** Transfer Dialog methods *****

    /**
     * Shows the transferDialog.
     */
    public void getTransferDialog() {
        transferDialog.setVisible(true);
        // There is some strange behaviour in the scene of the dialog, you can see the hard edges which set to be round
        // however when you close the dialog and reopen it they appear. Officially this is a limitation of JavaFX unless
        // you hardcode a solution. It works at present therefore I will keep it.
        transferDialog.getScene().setFill(Color.TRANSPARENT);
        transferDialog.getScene().setFill(Color.DARKSALMON);
    }

    /**
     * Method on the Accept button of transferDialog. It checks for empty TextFields and validates input.
     */
    public void transferAccept() {
        transferDialog.getScene().setFill(Color.TRANSPARENT);
        clearLabel(transferErrorLabel);
        resetTextFieldColor(transferAccFirstNameTf, transferAccLastNameTf, transferAccNoTf, transferAmountTf);

        // name
        if (isEmpty(transferAccFirstNameTf))
            errorToLabel(transferAccFirstNameTf, transferErrorLabel, "First name required");
        else if (!isLetter(transferAccFirstNameTf))
            errorToLabel(transferAccFirstNameTf, transferErrorLabel, "Invalid first name");
        else if (isEmpty(transferAccLastNameTf))
            errorToLabel(transferAccLastNameTf, transferErrorLabel, "Last name required");
        else if (!isLetter(transferAccLastNameTf))
            errorToLabel(transferAccLastNameTf, transferErrorLabel, "Invalid last name");

            // account number
        else if (isEmpty(transferAccNoTf))
            errorToLabel(transferAccNoTf, transferErrorLabel, "Account Number required");
        else if (!isDigit(transferAccNoTf)) errorToLabel(transferAccNoTf, transferErrorLabel, "Invalid Account No");
        else if (!validateAccountNo(transferAccNoTf))
            errorToLabel(transferAccNoTf, transferErrorLabel, "Account No has 8 digits");

            // amount
        else if (isEmpty(transferAmountTf))
            errorToLabel(transferAmountTf, transferErrorLabel, "Transfer Amount required");
        else if (!isDigit(transferAmountTf)) errorToLabel(transferAmountTf, transferErrorLabel, "Enter an amount");
        else if (Float.parseFloat(transferAmountTf.getText()) <= 0)
            errorToLabel(transferAmountTf, transferErrorLabel, "Invalid amount");

            // checks if customer exists
        else if (!existsCustomer(transferAccFirstNameTf.getText(), transferAccLastNameTf.getText(), transferAccNoTf.getText())) {
            transferErrorLabel.setText("Invalid account details");
            transferAccFirstNameTf.setStyle("-fx-border-color: red;");
            transferAccLastNameTf.setStyle("-fx-border-color: red;");
            transferAccNoTf.setStyle("-fx-border-color: red;");
        } else {
            // attempts to get valid input from TextFields and completes a money transfer
            try {
                if (transfer(transferAccFirstNameTf.getText(), transferAccLastNameTf.getText(), transferAccNoTf.getText(), transferAmountTf.getText())) {
                    addToTransactionHistory("Transfer:" + SPACE + transferAccFirstNameTf.getText() + SPACE + transferAccLastNameTf.getText(), transferAmountTf);
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

    /**
     * Closes the transferDialog
     */
    public void transferDecline() {
        transferDialog.setVisible(false);
        resetLabelsAndTextFields();
    }

    // ***** Deposit Dialog Methods  *****

    /**
     * Shows the depositDialog
     */
    public void getDepositDialog() {
        depositDialogue.setVisible(true);
        // There is some strange behaviour in the scene of the dialog, you can see the hard edges which set to be round
        // however when you close the dialog and reopen it they appear. Officially this is a limitation of JavaFX unless
        // you hardcode a solution. It works at present therefore I will keep it.
        depositDialogue.getScene().setFill(Color.TRANSPARENT);
        depositDialogue.getScene().setFill(Color.DARKSALMON);

    }

    /**
     * Method on the Accept button of the depositDialog. It gets valid input and completes a money deposit.
     */
    public void depositAccept() {
        depositDialogue.getScene().setFill(Color.TRANSPARENT);
        clearLabel(depositErrorLabel);
        resetTextFieldColor(depositTf);

        if (isEmpty(depositTf)) errorToLabel(depositTf, depositErrorLabel, "Deposit amount required");
        else if (!isDigit(depositTf)) errorToLabel(depositTf, depositErrorLabel, "Invalid number");
        else if ((Float.parseFloat(depositTf.getText()) <= 0))
            errorToLabel(depositTf, depositErrorLabel, "Invalid amount");
        else {
            // Attempts to get a valid input from the TextField and completes a money transfer
            try {
                if (deposit(depositTf.getText())) {
                    updateScreenInformation();
                    addToTransactionHistory("Deposit", depositTf);
                    depositDialogue.setVisible(false);
                    resetLabelsAndTextFields();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes the depositDialog.
     */
    public void depositDecline() {
        depositDialogue.setVisible(false);
        resetLabelsAndTextFields();

    }

    /**
     * Clears Labels and TextFields, used when pressing NO/YES on dialog to be ready for use when they open again.
     */
    public void resetLabelsAndTextFields() {
        transferAccFirstNameTf.clear();
        transferAccLastNameTf.clear();
        transferAccNoTf.clear();
        transferAmountTf.clear();
        depositTf.clear();
        withdrawTf.clear();
        loanAmountTf.clear();
        loanYearsTf.clear();
        // need to add newly implemented textfields
        resetTextFieldColor(transferAccFirstNameTf, transferAccLastNameTf, transferAccNoTf, transferAmountTf, depositTf, withdrawTf, loanAmountTf, loanAmountTf);
        clearAllDialogLabels();
    }

    /**
     * Clears all Labels in every dialog.
     */
    public void clearAllDialogLabels() {
        transferErrorLabel.setText(EMPTY_STRING);
        withdrawErrorLabel.setText(EMPTY_STRING);
        depositErrorLabel.setText(EMPTY_STRING);
        loanErrorLabel.setText(EMPTY_STRING);

    }

    /**
     * Updates information to the screen First/Last name, account number, balance, current time & the color of the circle
     * in the middle depending on balance.
     */
    public void updateScreenInformation() {
        nameLabel.setText(currentCustomer.getFirstName() + SPACE + currentCustomer.getLastName());
        accountLabel.setText(adjustAccountNumber()); // converts to string
        balanceLabel.setText(getUpdatedBalance());
        timeLabel.setText(LAST_UPDATE + getTime());
        updateMidCircleColor();
    }

    /**
     * Adds hyphens to the account number displayed on the screen.
     */
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

    /**
     * Updates the middle circle color behind the balance based on current balance.
     */
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

    /**
     * Exists application and closes the connection.
     */
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

    /**
     * Changes the profile at the top left corner.
     */
    public void changeProfilePic() {
        profileButton1.setVisible(false);
        profileButton2.setVisible(true);
        try {
            Image image = new Image(selectProfileIcon().toURI().toString());
            profileCircle2.setFill(new ImagePattern(image));
        }
        // if no image is selected then an exception will occur and the button will be empty without the SVG
        // therefore we change the visibility of the button on top to normal
        catch (Exception e) {
            profileButton1.setVisible(true);
            profileButton2.setVisible(false);
        }

    }

    /**
     * Initialize values to the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateScreenInformation();
        for (int i = 0; i < 11; i++) {
            labelList.add(new Label[4]);
        }
        // Places selected nodes into the labelList. Iterated later on the transactionDialog
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
