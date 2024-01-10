package com.example.client.controllers;

import com.example.client.Models.entities.User;
import com.example.client.Models.enums.StatusEnum;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionItem implements Initializable {
    @FXML
    public Label connectionName;

    @FXML
    public ImageView connectionPic;

    @FXML
    public Circle connectionStatus;

    public void setData(User user) {
        Image image = new Image(getClass().getResourceAsStream(user.getImgSrc()));

        connectionPic.setImage(image);
        connectionName.setText(user.getDisplayName());
        connectionStatus.setFill(user.getStatus() == StatusEnum.ONLINE ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
