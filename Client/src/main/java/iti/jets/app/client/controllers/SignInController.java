package iti.jets.app.client.controllers;


import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import iti.jets.app.shared.DTOs.*;

import iti.jets.app.shared.Interfaces.server.LoginService;
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

    }

    @FXML
    public void onLoginSubmit() throws NotBoundException {
        boolean isInputValid = true;
        if (userNameTextField.getText().isEmpty()) {
            userNameErrorLabel.setText("You must enter your phone number");
            isInputValid = false;
        }
        if (passwordTextField.getText().isEmpty()) {
            PasswordErrorLabel.setText("You must enter your password");
            isInputValid = false;
        }
        if (isInputValid)
            login();
    }

    private void login() throws NotBoundException {
        UserLoginDto userLoginDto = new UserLoginDto(userNameTextField.getText(), passwordTextField.getText());
        LoginResultDto loginResultDto = null;
        try {
            Registry registry = LocateRegistry.getRegistry("192.168.254.214", 8189);
            LoginService loginService = (LoginService) registry.lookup("LoginService");
            loginResultDto = loginService.login(userLoginDto);
            if(loginResultDto == null)
                System.out.println("loginResultDto is null");
        } catch (RemoteException e) {
            System.out.println("Server is not responding");
        }
        if (loginResultDto == null) {
            LogInErrorLabel.setText("Invalid username or password");
            return;
        }
        redirectToChatScreenPage(loginResultDto);
    }

    @FXML
    public void onUserNameInput() {
        userNameErrorLabel.setText("");
    }

    @FXML
    public void onPasswordInput() {
        PasswordErrorLabel.setText("");
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

    private void redirectToChatScreenPage(LoginResultDto loginResultDto) throws NotBoundException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/iti/jets/app/client/views/chat-screen.fxml"));
            Parent root = loader.load();
            ChatScreenController anotherController = loader.getController();
            anotherController.setChatScreenDto(loginResultDto);
            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);
            // Get the stage (window) from the current button
            Stage stage = (Stage) signUpLabel.getScene().getWindow();
            // Set the new scene on the stage
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



