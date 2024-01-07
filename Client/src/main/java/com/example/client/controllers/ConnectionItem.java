package com.example.client.controllers;

import com.example.client.Models.User;
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
    public Label connectionName = new Label();

    @FXML
    public ImageView connectionPic = new ImageView();

    @FXML
    public Circle connectionStatus = new Circle();

    public void setData(User user) {
        //Image image = new Image(getClass().getResourceAsStream(user.getImgSrc()));

        //connectionPic.setImage(image);
        connectionName.setText(user.getName());
        connectionStatus.setFill(user.getStatus() == User.Status.Online ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
