package iti.jets.app.client.controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.net.URL;
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
    public ImageView profilePic;

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
    public void toggleButton(ActionEvent event)
    {
        if(showPassBtn.isSelected())
        {
            showPassBtn.setText("Hide");
            shownPasswordField.setText(passwordField.getText());
            shownPasswordField.setVisible(true);
            passwordField.setVisible(false);
        }
        else
        {
            showPassBtn.setText("Show");
            passwordField.setVisible(true);
            shownPasswordField.setVisible(false);
        }
    }


    public void switchButtonsVisibility(HBox fieldBox)
    {
        for (Node node : fieldBox.getChildren())
        {
            if (node instanceof Button)
            {
                node.setVisible(!node.isVisible());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(passStatus == null)
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
        editEmailBtn.setOnAction(event -> {
            emailField.setEditable(true);
            switchButtonsVisibility(emailBox);
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
            // TODO: Save the changes
            nameField.setEditable(false);
            switchButtonsVisibility(nameBox);
        });
        saveEmailBtn.setOnAction(event -> {
            // TODO: Save the changes
            emailField.setEditable(false);
            switchButtonsVisibility(emailBox);
        });
        saveDobBtn.setOnAction(event -> {
            // TODO: Save the changes
            dobPicker.setEditable(false);
            switchButtonsVisibility(dobBox);
        });

        savePassBtn.setOnAction(event -> {
            // TODO: Save the changes
            passStatus.setVisible(false);
            passwordField.setText(newPassField.getText());
            clearPassFields();
            switchButtonsVisibility(changePassBox);
            currentPassBox.setVisible(true);
            newPassBox.setVisible(false);
            confirmPassBox.setVisible(false);
        });

        cancelNameBtn.setOnAction(event -> {
            nameField.setEditable(false);
            switchButtonsVisibility(nameBox);
        });
        cancelEmailBtn.setOnAction(event -> {
            emailField.setEditable(false);
            switchButtonsVisibility(emailBox);
        });
        cancelDobBtn.setOnAction(event -> {
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
    public void clearPassFields()
    {
        newPassField.clear();
        confirmPassField.clear();
    }
}