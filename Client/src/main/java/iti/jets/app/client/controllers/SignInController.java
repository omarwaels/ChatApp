package iti.jets.app.client.controllers;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import iti.jets.app.client.CallBack.ClientImpl;
import iti.jets.app.client.utils.ViewsFactory;
import iti.jets.app.shared.DTOs.*;

import iti.jets.app.shared.Interfaces.server.LoginService;
import iti.jets.app.shared.Interfaces.server.ServerService;
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

    private final String appDirectoryForLogin = "AppDirectory\\userObj";
    private final String appDirectoryForNumber = "AppDirectory\\phoneNumObj";
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
    ClientImpl client;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tmpField.requestFocus();
        UserLoginDto userLoginDto = loadObjectFromFile(appDirectoryForLogin);

        if(userLoginDto != null){
            try {
                login(userLoginDto);
            } catch (NotBoundException e) {
                System.out.println("Cant automatic Login");
            } catch (RemoteException e) {
                System.out.println("Cant automatic Login");
            }
        }
        String phoneNumber = loadPhoneNumObjectFromFile(appDirectoryForNumber);
        if(phoneNumber != null){
            userNameTextField.setText(phoneNumber);
        }

    }

    @FXML
    public void onUserNameInput() {
        userNameErrorLabel.setText("");
    }

    @FXML
    public void onPasswordInput() {
        PasswordErrorLabel.setText("");
    }

    ServerService getServerService() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(8189);
        return ((ServiceFactory) registry.lookup("ServiceFactory")).getServerService();
    }

    @FXML
    public void onLoginSubmit() throws NotBoundException, IOException {
        Platform.runLater(() -> {
            if (isFirstScreen) {
                try {
                    if (!validPhoneNumber(userNameTextField.getText())) {
                        userNameErrorLabel.setText("Invalid phone number");
                    } else {
                        isFirstScreen = false;
                        firstScreen.setVisible(false);
                        secondScreen.setVisible(true);
                        tmpField.requestFocus();
                    }
                } catch (NotBoundException e) {
                    throw new RuntimeException(e);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } else {
                if (passwordField.getText().isEmpty())
                    userNameErrorLabel.setText("Password Can't be empty");
                else {
                    try {
                        getDataFromScreenAndlogin();
                    } catch (NotBoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
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

    private void getDataFromScreenAndlogin() throws NotBoundException, IOException {
        UserLoginDto userLoginDto = new UserLoginDto(userNameTextField.getText(), passwordField.getText());
        login(userLoginDto);
    }

    private void login(UserLoginDto userLoginDto) throws NotBoundException, RemoteException {
        LoginResultDto loginResultDto = getLoginService().login(userLoginDto);
        Platform.runLater(() -> {
            if (loginResultDto == null)
                errorInLogin();
            else {
                try {
                    saveLoginObjectToFile(userLoginDto, appDirectoryForLogin);
                    savePhoneNumObjectToFile(userLoginDto.getPhoneNumber(), appDirectoryForNumber);
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
        FXMLLoader loader = ViewsFactory.getViewsFactory().getNewChatLoader();
        Parent root = loader.load();
        ((Stage) signUpLabel.getScene().getWindow()).setScene(new Scene(root));
        ChatScreenController chatScreenController = loader.getController();
        chatScreenController.setChatScreenDto(loginResultDto);
    }
    public static void saveLoginObjectToFile(UserLoginDto object, String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
                objectOutputStream.writeObject(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void savePhoneNumObjectToFile(String object, String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
                objectOutputStream.writeObject(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static UserLoginDto loadObjectFromFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path.toFile()))) {
                    return (UserLoginDto) objectInputStream.readObject();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String loadPhoneNumObjectFromFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path.toFile()))) {
                    return (String) objectInputStream.readObject();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setUserNameInScreen(String userPhoneNumber){
        userNameTextField.setText(userPhoneNumber);
    }
}



