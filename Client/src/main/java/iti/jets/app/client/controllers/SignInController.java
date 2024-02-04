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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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

    @FXML
    public Label haveAccountLabel;

    @FXML
    public Button loginBtn;

    @FXML
    public VBox firstScreen;

    @FXML
    public VBox secondScreen;

    @FXML
    public TextField tmpField;

    @FXML
    public PasswordField passwordField;

    boolean isFirstScreen = true;

    String phoneNumber;

    public Parent signUpParent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tmpField.requestFocus();
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
        if (isFirstScreen) {
            if (!validPhoneNumber(userNameTextField.getText())) {
                userNameErrorLabel.setText("Invalid phone number");
            } else {
                isFirstScreen = false;
                firstScreen.setVisible(false);
                secondScreen.setVisible(true);
                tmpField.requestFocus();
            }
        } else {
            if (passwordField.getText().isEmpty())
                userNameErrorLabel.setText("Password Can't be empty");
            else
                new Thread(
                        () -> {
                            try {
                                login();
                            } catch (NotBoundException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                ).start();
        }
    }

    private boolean validPhoneNumber(String phoneNumber) throws NotBoundException, RemoteException {
        return getLoginService().userExists(phoneNumber);
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
        UserLoginDto userLoginDto = new UserLoginDto(userNameTextField.getText(), passwordField.getText());
        LoginResultDto loginResultDto = getLoginService().login(userLoginDto);
        Platform.runLater(() -> {
            if (loginResultDto == null)
                errorInLogin();
            else {
                try {
                    redirectToChatScreenPage(loginResultDto);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (NotBoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    LoginService getLoginService() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(8189);
        return ((ServiceFactory) registry.lookup("ServiceFactory")).getLoginService();
    }

    private void errorInLogin() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Password is incorrect, please try again");
        alert.showAndWait();
    }

    private void redirectToSignUpPage() throws IOException {
        Stage currentStage = (Stage) signUpLabel.getScene().getWindow();
        currentStage.setScene(ViewsFactory.getViewsFactory().getSignUpScene());
        currentStage.show();
    }

    private void redirectToChatScreenPage(LoginResultDto loginResultDto) throws IOException, NotBoundException {
        FXMLLoader loader = ViewsFactory.getViewsFactory().getChatLoader();
        ((Stage) signUpLabel.getScene().getWindow()).setScene(ViewsFactory.getViewsFactory().getChatScene());
        ChatScreenController chatScreenController = loader.getController();
        chatScreenController.setChatScreenDto(loginResultDto);
    }
}



