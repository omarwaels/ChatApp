package iti.jets.app.client.controllers;


import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.enums.StatusEnum;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionItemController implements Initializable {
    @FXML
    public Label connectionName;

    @FXML
    public ImageView connectionPic;

    @FXML
    public Circle connectionStatus;

    ChatScreenController chatScreenController;

    private int UserID ;

    private UserDto user;

    public void setData(UserDto user,ChatScreenController chatScreenController) {
        this.user = user;
        this.chatScreenController = chatScreenController;
        connectionName.setText(user.getDisplayName());
        connectionStatus.setFill(user.getStatus() == StatusEnum.ONLINE ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
        UserID = user.getId();
        if(user.getPicture() != null){
            connectionPic.setImage(new Image(new ByteArrayInputStream(user.getPicture())));
        }
    }
    public void friendClicked() {

        chatScreenController.updateConnectionLayout(UserID);
        chatScreenController.updateConnectionName(user.getDisplayName());

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
