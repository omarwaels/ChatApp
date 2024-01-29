package iti.jets.app.client.controllers;


import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.FriendInfoDto;
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
    private int UserID;
    private FriendInfoDto user;
    private ChatDto chatDto;

    public void setData(FriendInfoDto user, ChatScreenController chatScreenController, ChatDto chatDto) {
        this.user = user;
        this.chatScreenController = chatScreenController;
        this.chatDto = chatDto;
        connectionName.setText(user.getUserFriendName());
        connectionStatus.setFill(user.getUserFriendStatus() == StatusEnum.ONLINE ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
        UserID = user.getUserFriendID();
        if (user.getUserFriendPhoto() != null) {
            connectionPic.setImage(new Image(new ByteArrayInputStream(user.getUserFriendPhoto())));
        }
    }

    public void friendClicked() {
        chatScreenController.updateChatLayout(UserID);
        chatScreenController.updateConnectionName(user.getUserFriendName());
        chatScreenController.updateCurrentScreenChatId(chatDto.getChatId());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
