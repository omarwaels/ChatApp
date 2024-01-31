package iti.jets.app.client.controllers;

import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.enums.StatusEnum;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
    @FXML
    public HBox connectionItem;
    private int UserID;

    public FriendInfoDto user;
    private ChatDto chatDto;
    private ChatScreenController chatScreenController;
    private Image userImage;

    public void setData(FriendInfoDto user, ChatScreenController chatScreenController, ChatDto chatDto) {
        this.user = user;
        this.chatScreenController = chatScreenController;
        this.chatDto = chatDto;
        connectionName.setText(user.getUserFriendName());
        connectionStatus.setFill(user.getUserFriendStatus() == StatusEnum.ONLINE ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
        UserID = user.getUserFriendID();
        if (user.getUserFriendPhoto() != null) {
            Image userImg = new Image(new ByteArrayInputStream(user.getUserFriendPhoto()));
            connectionPic.setImage(userImg);
            this.userImage = userImg;
        }
        chatScreenController.currentConnection = this;
    }


    @FXML
    public void friendClicked() {
        chatScreenController.temporaryScreen.setVisible(false);
        chatScreenController.updateChatLayout(UserID, chatDto.getChatId());
        chatScreenController.updateConnectionName(user.getUserFriendName());
        chatScreenController.updateCurrentScreenStatusWord(user.getUserFriendStatus());
        chatScreenController.chatArea.setVisible(true);
        chatScreenController.setCurrentScreenImage(userImage);
    }

    public void hoverEnterEffect() {
        connectionItem.setStyle("-fx-background-color: rgba(230, 230, 230, 0.7);");
    }

    public void hoverExitEffect() {
        connectionItem.setStyle("-fx-background-color: rgba(230, 230, 230, 0.0);");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
