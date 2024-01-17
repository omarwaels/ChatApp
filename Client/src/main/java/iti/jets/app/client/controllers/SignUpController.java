package iti.jets.app.client.controllers;
import iti.jets.app.Interfaces.RegisterService;
import iti.jets.app.server.Implementation.RegisterServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    public Label countryErrorLabel;

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

    public ComboBox<?> countryComboBox;

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

    public TextField bioTextField;

    public Label confirmPasswordErrorLabel;

    public TextField confirmPasswordTextField;

    public byte[] picture;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void onEmailInput() {
        emailErrorLabel.setText("");
    }

    @FXML
    public void onFullNameInput() {
        fullNameErrorLabel.setText("");
    }

    @FXML
    public void onPasswordInput() {
        passwordErrorLabel.setText("");
    }

    @FXML
    public void onPhoneNumberInput() {
        phoneNumberErrorLabel.setText("");
    }

    public void onUploadPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Photo");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(imageFilter);
        java.io.File selectedFile = fileChooser.showOpenDialog(phoneNumberTextField.getScene().getWindow());

        if (selectedFile != null) {
            try {
                picture = Files.readAllBytes(selectedFile.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onConfirmPasswordInput() {
        confirmPasswordErrorLabel.setText("");
    }

    public void onCountryChoose() {
        countryErrorLabel.setText("");
    }

    public void onGenderChoose() {
        genderErrorLabel.setText("");
    }

    @FXML
    public void onSignUpSubmit() {
        if (fullNameTextField.getText().isEmpty())
            fullNameErrorLabel.setText("You must enter your user name");
        if (phoneNumberTextField.getText().isEmpty())
            phoneNumberErrorLabel.setText("You must enter your phone number");
        if (emailTextField.getText().isEmpty())
            emailErrorLabel.setText("You must enter your email");
        if (passwordTextField.getText().isEmpty())
            passwordErrorLabel.setText("You must enter your password");
        if (confirmPasswordTextField.getText().isEmpty())
            confirmPasswordErrorLabel.setText("You must confirm your password");
        if (dobDatePicker.getEditor().getText().isEmpty())
            dobErrorLabel.setText("You must enter a valid date");
        if (genderComboBox.getSelectionModel().isEmpty())
            genderErrorLabel.setText("You must choose a gender");
        if (countryComboBox.getSelectionModel().isEmpty())
            countryErrorLabel.setText("You must choose a country");
        else{
            addUser();
            redirectToSignInPage();
        }

    }

    private void addUser(){
        RegisterService registerService = new RegisterServiceImpl();
    }

    @FXML
    public void onLogInLabelClicked() {
        redirectToSignInPage();
    }

    public void onBioInput() {

    }

    private void redirectToSignInPage() {
        try {
            Stage currentStage = (Stage) logInLabel.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/iti/jets/app/client/views/sign-in.fxml"));
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onChooseDate() {
        dobErrorLabel.setText("");
    }

}
