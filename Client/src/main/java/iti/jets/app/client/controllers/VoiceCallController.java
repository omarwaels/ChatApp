package iti.jets.app.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class VoiceCallController implements Initializable {
    @FXML
    public ImageView acceptBtn;

    @FXML
    public VBox callResponseLayout;

    @FXML
    public VBox callingLayout;

    @FXML
    public ImageView declineBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        callResponseLayout.setVisible(false);

    }

    public void accept(javafx.event.ActionEvent actionEvent) {
        System.out.println("Inside accept");
        callingLayout.setVisible(false);
        callResponseLayout.setVisible(true);
    }

    public void decline(javafx.event.ActionEvent actionEvent) {
        System.out.println("Inside decline");
        System.exit(0);
    }
}
