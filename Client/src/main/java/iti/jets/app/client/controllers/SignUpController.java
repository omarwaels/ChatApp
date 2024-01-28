package iti.jets.app.client.controllers;

import iti.jets.app.shared.DTOs.UserRegisterDto;
import iti.jets.app.shared.Interfaces.RegisterService;
import iti.jets.app.server.Implementation.RegisterServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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

    private RegisterService registerService;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Registry registry = LocateRegistry.getRegistry(8090);
        registerService = (RegisterService) registry.lookup("RegisterService");
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

    public void onChooseDate() {
        dobErrorLabel.setText("");
    }

    @FXML
    public void onSignUpSubmit() throws RemoteException {
        boolean valid = true;
        if (fullNameTextField.getText().isEmpty()) {
            fullNameErrorLabel.setText("You must enter your user name");
            valid = false;
        }
        if (phoneNumberTextField.getText().isEmpty()) {
            phoneNumberErrorLabel.setText("You must enter your phone number");
            valid = false;
        }
        if (emailTextField.getText().isEmpty()) {
            emailErrorLabel.setText("You must enter your email");
            valid = false;
        }
        if (passwordTextField.getText().isEmpty()) {
            passwordErrorLabel.setText("You must enter your password");
            valid = false;
        }
        if (confirmPasswordTextField.getText().isEmpty() || !confirmPasswordTextField.getText().equals(passwordTextField.getText())) {
            confirmPasswordErrorLabel.setText("You must confirm your password");
            valid = false;
        }
        if (dobDatePicker.getEditor().getText().isEmpty()) {
            dobErrorLabel.setText("You must enter a valid date");
            valid = false;
        }
        if (genderComboBox.getSelectionModel().isEmpty()) {
            genderErrorLabel.setText("You must choose a gender");
            valid = false;
        }
        if (countryComboBox.getSelectionModel().isEmpty()) {
            countryErrorLabel.setText("You must choose a country");
            valid = false;
        }
        if (valid)
            addUser();
    }

    private void addUser() throws RemoteException {
        UserRegisterDto userRegisterDto = new UserRegisterDto(phoneNumberTextField.getText(), passwordTextField.getText());
        userRegisterDto.setDisplayName(fullNameTextField.getText());
        userRegisterDto.setEmail(emailTextField.getText());
        userRegisterDto.setGender(genderComboBox.getSelectionModel().getSelectedItem().toString());
        userRegisterDto.setCountry(countryComboBox.getSelectionModel().getSelectedItem().toString());
        userRegisterDto.setDateOfBirth(java.sql.Date.valueOf(dobDatePicker.getValue()));
        userRegisterDto.setBio(bioTextField.getText());
        userRegisterDto.setPicture(picture);
        int ret = registerService.register(userRegisterDto);
        if (ret == 1) {
            System.out.println("Done");
            redirectToSignInPage();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Phone number or email already exists, please try again");
            alert.showAndWait();
        }
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

}
