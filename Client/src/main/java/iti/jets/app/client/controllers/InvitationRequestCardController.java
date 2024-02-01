package iti.jets.app.client.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class InvitationRequestCardController implements Initializable {
    @FXML
    public ImageView friendImage;

    @FXML
    public Label friendName;

    @FXML
    public Label friendPhone;

    @FXML
    public MFXButton acceptButton;
    @FXML
    public MFXButton declineButton;
    public final String name;
    public final String phone;
    public final Image image;
    public final int friendId;

    public InvitationRequestCardController(String friendName, String friendPhoneNumber, Image friendImage, int id) {
        this.friendId = id;
        this.name = friendName;
        this.phone = friendPhoneNumber;
        this.image = friendImage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        friendName.setText(name);
        friendImage.setImage(image);
        Circle circle = new Circle(25, 25, 25);
        friendImage.setClip(circle);
        friendPhone.setText(phone);
    }

    @FXML
    public void acceptInvitation(ActionEvent event) {

    }

    @FXML
    public void declineInvitation(ActionEvent event) {

    }
}
