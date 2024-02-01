package iti.jets.app.client.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddConnectionController{

    @FXML
    public ImageView addTextFieldIcon;

    @FXML
    public Button btnInvite;

    @FXML
    public ListView<TextField> listOfInvitations;


    @FXML
    public void addField(MouseEvent event)
    {
        TextField newField = new TextField();
        newField.setPromptText("Enter Phone Number");
        ((ObservableList<TextField>) listOfInvitations.getItems()).add(newField);
    }

    @FXML
    public void sendInvitation(ActionEvent event)
    {
        List<String> sendInvitationTo = new ArrayList<>();
        for (TextField field : listOfInvitations.getItems())
        {
            String text = field.getText();
            if (text != null && !text.isEmpty())
                sendInvitationTo.add(text);
        }
        /**
         * We will loop over the list and check on two scenarios:

         *      1- if one of the entered users isn't registered on the system.
         *      2- if one of the entered users is already in the connections list of the user.

         * if (!isUserRegistered(username))     -> invoke showNotRegisteredAlert
         * if (isUserAlreadyConnected(username))-> invoke showAlreadyConnectedAlert

         * */
    }
    public void showNotRegisteredAlert(String phoneNumber) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText("The user " + phoneNumber + " is not registered on the system.");

        alert.showAndWait();
    }

    public void showAlreadyConnectedAlert(String phoneNumber) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText("The user " + phoneNumber + " is already in your connections list.");

        alert.showAndWait();
    }

}