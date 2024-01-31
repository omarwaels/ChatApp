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

public class ConnectionGroupItemController implements Initializable {
    @FXML
    public Label connectionName;

    @FXML
    public ImageView connectionPic;
    @FXML
    public Circle connectionStatus;
    @FXML
    public HBox connectionItem;
    private int UserID;

    private ChatDto chatDto;
    private ChatScreenController chatScreenController;


    public void setData( ChatDto chatDto, ChatScreenController chatScreenController) {

        this.chatScreenController = chatScreenController;
        this.chatDto = chatDto;
        connectionName.setText(chatDto.getChatName());
        if (chatDto.getChatImage() != null) {
            Image chatImg = new Image(new ByteArrayInputStream(chatDto.getChatImage()));
            connectionPic.setImage(chatImg);

        }
    }


    @FXML
    public void friendClicked() {
        chatScreenController.temporaryScreen.setVisible(false);
        chatScreenController.updateChatLayout(null , chatDto.getChatId());
        chatScreenController.updateConnectionName(chatDto.getChatName());
        chatScreenController.updateCurrentScreenStatusWordForGroups("");
        chatScreenController.chatArea.setVisible(true);
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
