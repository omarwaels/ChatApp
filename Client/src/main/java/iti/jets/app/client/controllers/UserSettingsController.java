package iti.jets.app.client.controllers;


import iti.jets.app.client.utils.ViewsFactory;
import iti.jets.app.shared.DTOs.UpdateInfoDto;
import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.DTOs.UserRegisterDto;
import iti.jets.app.shared.Interfaces.server.ServerService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
import iti.jets.app.shared.Interfaces.server.UpdateInfoService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserSettingsController implements Initializable {

    @FXML
    public TextArea bio;

    @FXML
    public Button cancelDobBtn;

    @FXML
    public Button cancelEmailBtn;

    @FXML
    public Button cancelNameBtn;

    @FXML
    public Button cancelPassBtn;

    @FXML
    public HBox changePassBox;

    @FXML
    public HBox confirmPassBox;

    @FXML
    public PasswordField confirmPassField;

    @FXML
    public HBox currentPassBox;

    @FXML
    public HBox dobBox;

    @FXML
    public DatePicker dobPicker;

    @FXML
    public Button editDobBtn;

    @FXML
    public Button editEmailBtn;

    @FXML
    public Button editNameBtn;

    @FXML
    public Button editPassBtn;

    @FXML
    public Button editPhotoBtn;

    @FXML
    public HBox emailBox;

    @FXML
    public TextField emailField;

    @FXML
    public HBox nameBox;

    @FXML
    public TextField nameField;

    @FXML
    public HBox newPassBox;

    @FXML
    public PasswordField newPassField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Circle profilePic;

    @FXML
    public Button removePhotoBtn;

    @FXML
    public Button saveDobBtn;

    @FXML
    public Button saveEmailBtn;

    @FXML
    public Label passStatus;

    @FXML
    public Button saveNameBtn;

    @FXML
    public Button savePassBtn;

    @FXML
    public ToggleButton showPassBtn;

    @FXML
    public TextField shownPasswordField;

    @FXML
    public ImageView backBtn;

    @FXML
    public Button saveBioBtn;

    @FXML
    public Label nameLabel;
    ChatScreenController chatScreenController;

    private UpdateInfoService updateInfoService;

    private UserDto user;

    @FXML
    public void toggleButton(ActionEvent event) {
        if (showPassBtn.isSelected()) {
            showPassBtn.setText("Hide");
            shownPasswordField.setText(passwordField.getText());
            shownPasswordField.setVisible(true);
            passwordField.setVisible(false);
        } else {
            showPassBtn.setText("Show");
            passwordField.setVisible(true);
            shownPasswordField.setVisible(false);
        }
    }


    public void switchButtonsVisibility(HBox fieldBox) {
        for (Node node : fieldBox.getChildren()) {
            if (node instanceof Button) {
                node.setVisible(!node.isVisible());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dobPicker.setEditable(false);
        dobPicker.getEditor().setDisable(true);
        try {
            Registry registry = LocateRegistry.getRegistry(8189);
            updateInfoService = ((ServiceFactory) registry.lookup("ServiceFactory")).getUpdateInfoService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (passStatus == null)
            passStatus = new Label();
        confirmPassField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                passStatus.setVisible(true);
                if (!newValue.equals(newPassField.getText())) {
                    passStatus.setText("Password don't match");
                    passStatus.setTextFill(Color.RED);
                } else {
                    passStatus.setText("You can change the password now");
                    savePassBtn.setDisable(false);
                    passStatus.setTextFill(Color.GREEN);
                }
            }
        });

        editNameBtn.setOnAction(event -> {
            nameField.setEditable(true);
            switchButtonsVisibility(nameBox);
        });


        editDobBtn.setOnAction(event -> {
            dobPicker.setEditable(true);
            switchButtonsVisibility(dobBox);
        });

        editPassBtn.setOnAction(event -> {
            switchButtonsVisibility(changePassBox);
            savePassBtn.setDisable(true);
            currentPassBox.setVisible(false);
            newPassBox.setVisible(true);
            confirmPassBox.setVisible(true);
        });

        saveNameBtn.setOnAction(event -> {
            try {
                int ret = updateInfoService.updateField(new UpdateInfoDto("display_name", nameField.getText(), user.getId()));
                if (ret == 1) {
                    user.setDisplayName(nameField.getText());
                    Registry registry = LocateRegistry.getRegistry(8189);
                    ServerService serverService = ((ServiceFactory) registry.lookup("ServiceFactory")).getServerService();
                    new Thread(() -> {
                        try {
                            serverService.updateUserName(new ArrayList<>(chatScreenController.onlineUsers.keySet()), user.getId(), user.getDisplayName());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
                nameField.setText(user.getDisplayName());
                nameLabel.setText(user.getDisplayName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            nameField.setEditable(false);
            switchButtonsVisibility(nameBox);
        });

        saveDobBtn.setOnAction(event -> {
            try {
                int ret = updateInfoService.updateDOB(new UpdateInfoDto(java.sql.Date.valueOf(dobPicker.getValue()), user.getId()));
                if (ret == 1) {
                    user.setDateOfBirth(java.sql.Date.valueOf(dobPicker.getValue()));
                }
                dobPicker.setValue(user.getDateOfBirth().toLocalDate());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            dobPicker.setEditable(false);
            switchButtonsVisibility(dobBox);
        });

        savePassBtn.setOnAction(event -> {
            try {
                int ret = updateInfoService.updateField(new UpdateInfoDto("password", newPassField.getText(), user.getId()));
                if (ret == 1) {
                    user.setPassword(newPassField.getText());
                }
                passwordField.setText(user.getPassword());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            passStatus.setVisible(false);
            passwordField.setText(newPassField.getText());
            clearPassFields();
            switchButtonsVisibility(changePassBox);
            currentPassBox.setVisible(true);
            newPassBox.setVisible(false);
            confirmPassBox.setVisible(false);
        });

        cancelNameBtn.setOnAction(event -> {
            nameField.setText(user.getDisplayName());
            nameField.setEditable(false);
            switchButtonsVisibility(nameBox);
        });

        cancelDobBtn.setOnAction(event -> {
            dobPicker.setValue(user.getDateOfBirth().toLocalDate());
            dobPicker.setEditable(false);
            switchButtonsVisibility(dobBox);
        });

        cancelPassBtn.setOnAction(event -> {
            clearPassFields();
            switchButtonsVisibility(changePassBox);
            currentPassBox.setVisible(true);
            newPassBox.setVisible(false);
            confirmPassBox.setVisible(false);
            passStatus.setVisible(false);
        });
    }

    public void clearPassFields() {
        newPassField.clear();
        confirmPassField.clear();
    }

    public void setUser(UserDto user) {
        this.user = user;
        initializeFields();
    }

    private void initializeFields() {
        nameField.setText(user.getDisplayName());
        nameLabel.setText(user.getDisplayName());
        emailField.setText(user.getEmail());
        dobPicker.setValue(user.getDateOfBirth().toLocalDate());
        passwordField.setText(user.getPassword());
        bio.setText(user.getBio());

        profilePic.setFill(new ImagePattern(new Image(new ByteArrayInputStream(user.getPicture()))));
        //profilePic.setImage(new Image(new ByteArrayInputStream(user.getPicture())));
    }

    public void setChatScreenController(ChatScreenController chatScreenController) {
        this.chatScreenController = chatScreenController;
    }

    public void redirectToChatScreen() throws IOException {
        Stage currentStage = (Stage) backBtn.getScene().getWindow();
        currentStage.setScene(chatScreenController.chatSettingImg.getScene());
    }

    @FXML
    public void onClickSaveBioButton() throws Exception {
        updateInfoService.updateField(new UpdateInfoDto("bio", bio.getText(), user.getId()));
        user.setBio(bio.getText());
    }

    @FXML
    public void onClickRemovePhoto() throws IOException, NotBoundException {
        File img = new File("Client/src/main/resources/iti/jets/app/client/img/user.png");
        profilePic.setFill(new ImagePattern(new Image(new ByteArrayInputStream(Files.readAllBytes(img.toPath())))));
        chatScreenController.profilePic.setFill(new ImagePattern(new Image(new ByteArrayInputStream(Files.readAllBytes(img.toPath())))));
        user.setPicture(Files.readAllBytes(img.toPath()));
        Registry registry = LocateRegistry.getRegistry(8189);
        ServerService serverService = ((ServiceFactory) registry.lookup("ServiceFactory")).getServerService();
        new Thread(() -> {
            try {
                serverService.updatePhoto(new ArrayList<>(chatScreenController.onlineUsers.keySet()), user.getId(), user.getPicture());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    public void onClickEditPhoto() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Photo");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(editPhotoBtn.getScene().getWindow());
        if (selectedFile != null) {
            profilePic.setFill(new ImagePattern(new Image(new ByteArrayInputStream(Files.readAllBytes(selectedFile.toPath())))));
            chatScreenController.profilePic.setFill(new ImagePattern(new Image(new ByteArrayInputStream(Files.readAllBytes(selectedFile.toPath())))));
            user.setPicture(Files.readAllBytes(selectedFile.toPath()));
            Registry registry = LocateRegistry.getRegistry(8189);
            ServerService serverService = ((ServiceFactory) registry.lookup("ServiceFactory")).getServerService();
            new Thread(() -> {
                try {
                    serverService.updatePhoto(new ArrayList<>(chatScreenController.onlineUsers.keySet()), user.getId(), user.getPicture());
                    updateInfoService.updateImage(user.getId(), user.getPicture());
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}