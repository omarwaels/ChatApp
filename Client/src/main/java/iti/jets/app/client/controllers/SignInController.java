package iti.jets.app.client.controllers;


import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import iti.jets.app.client.utils.ViewsFactory;
import iti.jets.app.shared.DTOs.*;

import iti.jets.app.shared.Interfaces.server.LoginService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInController implements Initializable {
    @FXML
    public Label userNameErrorLabel;
    @FXML
    public Label PasswordErrorLabel;

    @FXML
    public Label LogInErrorLabel;

    @FXML
    public TextField userNameTextField;

    @FXML
    public PasswordField passwordTextField;
    @FXML
    public Label signUpLabel;

    public Parent signUpParent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    public void onLoginSubmit() throws NotBoundException, IOException {
        if (nonEmptyPhoneAndPassword())
            login();
    }

    @FXML
    public void onSignUpLabelClicked() throws IOException {
        redirectToSignUpPage();
    }

    private boolean nonEmptyPhoneAndPassword() {
        boolean isInputValid = true;
        if (userNameTextField.getText().isEmpty()) {
            userNameErrorLabel.setText("You must enter your phone number");
            isInputValid = false;
        }
        if (passwordTextField.getText().isEmpty()) {
            PasswordErrorLabel.setText("You must enter your password");
            isInputValid = false;
        }
        return isInputValid;
    }

    private void login() throws NotBoundException, IOException {
        UserLoginDto userLoginDto = new UserLoginDto(userNameTextField.getText(), passwordTextField.getText());
        LoginResultDto loginResultDto = getLoginService().login(userLoginDto);
        if (loginResultDto == null)
            errorInLogin();
        else
            redirectToChatScreenPage(loginResultDto);
    }

    LoginService getLoginService() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(8189);
        return ((ServiceFactory) registry.lookup("ServiceFactory")).getLoginService();
    }

    private void errorInLogin() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Phone number or password is incorrect, please try again");
        alert.showAndWait();
    }

    private void redirectToSignUpPage() throws IOException {
        Stage currentStage = (Stage) signUpLabel.getScene().getWindow();
        if (signUpParent == null)
            signUpParent = ViewsFactory.getViewsFactory().getSignUpLoader().load();
        currentStage.setScene(new Scene(signUpParent));
        currentStage.show();
    }

    private void redirectToChatScreenPage(LoginResultDto loginResultDto) throws IOException, NotBoundException {
        FXMLLoader loader = ViewsFactory.getViewsFactory().getChatLoader();
        Parent root = loader.load();
        ChatScreenController chatScreenController = loader.getController();
        chatScreenController.setChatScreenDto(loginResultDto);
        ((Stage) signUpLabel.getScene().getWindow()).setScene(new Scene(root));
    }
}



