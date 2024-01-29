package iti.jets.app.client.controllers;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import iti.jets.app.client.services.ConnectServerService;
import iti.jets.app.shared.DTOs.*;

import iti.jets.app.shared.Interfaces.server.Server;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import iti.jets.app.client.services.LoginServices;

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
        if (isEmptyField(userNameTextField)) {
            userNameErrorLabel.setText("You must enter your phone number");
            return;
        }

        if (isEmptyField(passwordTextField)) {
            PasswordErrorLabel.setText("You must enter your password");
        }
        this.login();

    }

    private void login() throws NotBoundException {

        UserLoginDto userLoginDto = new UserLoginDto(userNameTextField.getText(), passwordTextField.getText());
        System.out.println(userNameTextField.getText());
        System.out.println(passwordTextField.getText());
        ChatScreenDto chatScreenDto = null;
        try{
            chatScreenDto = LoginServices.login(userLoginDto);
        }catch (RemoteException e){
            System.out.println("Server is not responding");
        }
        System.out.println("hello");
        if(chatScreenDto == null) return;

        redirectToChatScreenPage(chatScreenDto);

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


    private void redirectToChatScreenPage(ChatScreenDto chatScreenDto) throws NotBoundException {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/iti/jets/app/client/views/chat-screen.fxml"));
            Parent root = loader.load();
            ChatScreenController anotherController = loader.getController();
            anotherController.setChatScreenDto(chatScreenDto);

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



