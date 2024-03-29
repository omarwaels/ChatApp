package iti.jets.app.client.controllers;

import iti.jets.app.client.utils.ServerIPAddress;
import iti.jets.app.client.utils.ViewsFactory;
import iti.jets.app.shared.DTOs.UserRegisterDto;
import iti.jets.app.shared.Interfaces.server.RegisterService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @FXML
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

    @FXML
    public TextField bioTextField;

    @FXML
    public Label confirmPasswordErrorLabel;

    @FXML
    public TextField confirmPasswordTextField;

    @FXML
    public Label tmpLabel;

    public byte[] picture;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        phoneNumberTextField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume();
            }
        });
        Platform.runLater(() -> {
            tmpLabel.requestFocus();
        });
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

    @FXML
    public void onUploadPhoto() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Photo");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(phoneNumberTextField.getScene().getWindow());
        if (selectedFile != null)
            picture = Files.readAllBytes(selectedFile.toPath());
    }

    @FXML
    public void onSignUpSubmit() throws IOException, NotBoundException {
        if (nonEmptyRequiredFields())
            addUser();
    }

    @FXML
    public void onConfirmPasswordInput() {
        confirmPasswordErrorLabel.setText("");
    }

    @FXML
    public void onCountryChoose() {
        countryErrorLabel.setText("");
    }

    @FXML
    public void onGenderChoose() {
        genderErrorLabel.setText("");
    }

    @FXML
    public void onChooseDate() {
        dobErrorLabel.setText("");
    }

    @FXML
    public void onBioInput() {
    }

    @FXML
    public void onLogInLabelClicked() throws IOException {
        redirectToSignInPage();
    }

    private void addUser() throws IOException {
        try {
            UserRegisterDto userRegisterDto = createUserRegisterDto();
            int ret = getRegisterService().register(userRegisterDto);
            if (ret == 1) {
                successfulSignUp();
                redirectToSignInPage();
            } else
                errorInSignUp();
        } catch (NotBoundException | RemoteException e) {
            showServerDownAlert();
        }
    }

    private RegisterService getRegisterService() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ServerIPAddress.getIp(), ServerIPAddress.getPort());
        return ((ServiceFactory) registry.lookup("ServiceFactory")).getRegisterService();
    }

    private void errorInSignUp() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Phone number, email, or user name already exists, please try again");
        alert.showAndWait();
    }

    private void redirectToSignInPage() throws IOException {
        FXMLLoader fxml = ViewsFactory.getViewsFactory().getLoginLoader();
        Parent root = fxml.load();
        Stage currentStage = (Stage) logInLabel.getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.show();
    }

    private UserRegisterDto createUserRegisterDto() throws IOException {
        UserRegisterDto userRegisterDto = new UserRegisterDto(phoneNumberTextField.getText(), passwordTextField.getText());
        userRegisterDto.setDisplayName(fullNameTextField.getText());
        userRegisterDto.setEmail(emailTextField.getText());
        userRegisterDto.setGender(genderComboBox.getSelectionModel().getSelectedItem().toString());
        userRegisterDto.setCountry(countryComboBox.getSelectionModel().getSelectedItem().toString());
        userRegisterDto.setDateOfBirth(java.sql.Date.valueOf(dobDatePicker.getValue()));
        userRegisterDto.setBio("");

        InputStream is = getClass().getResourceAsStream("/img/user.png");
        BufferedImage bImage = ImageIO.read(is);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos);
        byte[] imgBytes = bos.toByteArray();

        userRegisterDto.setPicture(imgBytes);
        return userRegisterDto;
    }

    private boolean nonEmptyRequiredFields() {
        boolean valid = true;
        if (fullNameTextField.getText().isEmpty()) {
            fullNameErrorLabel.setText("please enter a valid username.");
            valid = false;
        }
        if (phoneNumberTextField.getText().isEmpty() || !phoneNumberTextField.getText().matches("\\b(?:010|011|012|015)\\d{8}\\b")) {
            phoneNumberErrorLabel.setText("Please enter a valid phone number");
            valid = false;
        }
        if (!isValidEmail(emailTextField.getText())) {
            emailErrorLabel.setText("Please enter a valid email address");
            valid = false;
        }
        if (passwordTextField.getText().isEmpty()) {
            passwordErrorLabel.setText("Please enter a valid password");
            valid = false;
        }
        if (confirmPasswordTextField.getText().isEmpty() || !confirmPasswordTextField.getText().equals(passwordTextField.getText())) {
            confirmPasswordErrorLabel.setText("Your password doesn't match");
            valid = false;
        }
        if (dobDatePicker.getEditor().getText().isEmpty()) {
            dobErrorLabel.setText("Please choose a valid date of birth");
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
        return valid;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void successfulSignUp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("You have successfully signed up");
        alert.showAndWait();
    }

    public void showServerDownAlert() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Server Down");
            alert.setHeaderText(null);
            alert.setContentText("Server is down, please try again later.");
            // Check if this is correct
            alert.initOwner(logInLabel.getScene().getWindow());
            alert.showAndWait();
        });
    }
}

