package iti.jets.app.client.controllers;

import iti.jets.app.models.entities.User;
import iti.jets.app.models.enums.StatusEnum;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionItemController implements Initializable {
    @FXML
    public Label connectionName;

    @FXML
    public ImageView connectionPic;

    @FXML
    public Circle connectionStatus;

    public void setData(User user) {
        connectionName.setText(user.getDisplayName());
        connectionStatus.setFill(user.getStatus() == StatusEnum.ONLINE ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
