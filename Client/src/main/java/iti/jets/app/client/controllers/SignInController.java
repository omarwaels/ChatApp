package iti.jets.app.client.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import iti.jets.app.DTOs.UserLoginDto;
import iti.jets.app.Interfaces.LoginService;
import iti.jets.app.server.Implementation.LoginServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInController implements Initializable {
    public Label userNameErrorLabel;
    public Label PasswordErrorLabel;
    public Label LogInErrorLabel;
    public TextField userNameTextField;
    public PasswordField passwordTextField;
    @FXML
    public Label signUpLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void onLoginSubmit() {
        if (isEmptyField(userNameTextField)) {
            userNameErrorLabel.setText("You must enter your phone number");
            return;
        }

        if (isEmptyField(passwordTextField)) {
            PasswordErrorLabel.setText("You must enter your password");
        }
        login();
    }

    private void login() {
        LoginService loginService = new LoginServiceImpl();
        UserLoginDto userLoginDto = new UserLoginDto(userNameTextField.getText(), passwordTextField.getText());
//        if (loginService.login(userLoginDto) != null)
//            redirectToSignUpPage();
    }

    @FXML
    public void onUserNameInput() {

        userNameErrorLabel.setText("");
    }

    @FXML
    public void onPasswordInput() {
        PasswordErrorLabel.setText("");
    }

    public static boolean isEmptyField(TextField textField) {
        return textField.getText().isEmpty();
    }

    @FXML
    public void onSignUpLabelClicked() {
        redirectToSignUpPage();
    }

    private void redirectToSignUpPage() {
        try {
            Stage currentStage = (Stage) signUpLabel.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/iti/jets/app/client/views/sign-up.fxml"));
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
