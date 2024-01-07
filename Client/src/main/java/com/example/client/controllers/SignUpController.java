package com.example.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    @FXML
    public Label countryErrorLabel;

    @FXML
    public TextField countryTextField;

    @FXML
    public DatePicker dobDatePicker;

    @FXML
    public Label dobErrorLabel;

    @FXML
    public Label emailErrorLabel;

    @FXML
    public TextField emailTextField;

    @FXML
    public Label fullNameErrorLabel;

    @FXML
    public TextField fullNameTextField;

    @FXML
    public ComboBox<?> genderComboBox;

    @FXML
    public Label genderErrorLabel;

    @FXML
    public Label passwordErrorLabel;

    @FXML
    public PasswordField passwordTextField;

    @FXML
    public Label phoneNumberErrorLabel;

    @FXML
    public TextField phoneNumberTextField;

    @FXML
    public Label signUpErrorLabel;
    @FXML
    public Label logInLabel;

    @FXML
    public void onCountryInput(KeyEvent event) {

    }

    @FXML
    public void onEmailInput(KeyEvent event) {

    }

    @FXML
    public void onFullNameInput(KeyEvent event) {

    }

    @FXML
    public void onPasswordInput(KeyEvent event) {

    }

    @FXML
    public void onPhoneNumberInput(KeyEvent event) {

    }

    @FXML
    public void onSignUpSubmit(ActionEvent event) {

    }

    @FXML
    public void onLogInLabelClicked() {
        redirectToSignInPage();
    }
    private void redirectToSignInPage() {
        try {
            Stage currentStage = (Stage) logInLabel.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/client/views/sign-in.fxml"));
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
