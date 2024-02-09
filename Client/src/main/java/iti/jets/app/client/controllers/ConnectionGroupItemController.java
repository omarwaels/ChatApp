package iti.jets.app.client.controllers;

import iti.jets.app.client.utils.ServerIPAddress;
import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.Interfaces.server.ChatMessagesService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
import iti.jets.app.shared.enums.StatusEnum;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConnectionGroupItemController implements Initializable {
    @FXML
    public Label connectionName;
    @FXML
    public Label lastMessageTimestamp;
    @FXML
    public Circle connectionPic;
    @FXML
    public Circle connectionStatus;
    @FXML
    public HBox connectionItem;
    private int UserID;
    @FXML
    public StackPane counterContainer;
    @FXML
    public Label counter;

    @FXML
    public ImageView leaveGroupBtn;

    private ChatDto chatDto;
    private ChatScreenController chatScreenController;
    private Integer counterNumber = 0;
    private Timestamp lastMessageTime = new Timestamp(System.currentTimeMillis());

    private boolean messageRestoredFlag = false;

    public void updateCounter() {

        counterContainer.setVisible(false);
        counterNumber = counterNumber + 1;
        counter.setText(counterNumber.toString());
        counterContainer.setVisible(true);
    }

    public void setData(ChatDto chatDto, ChatScreenController chatScreenController) {

        this.chatScreenController = chatScreenController;
        this.chatDto = chatDto;
        connectionName.setText(chatDto.getChatName());
        if (chatDto.getChatImage() != null) {
            Image chatImg = new Image(new ByteArrayInputStream(chatDto.getChatImage()));
            connectionPic.setFill(new ImagePattern(chatImg));
            //connectionPic.setImage(chatImg);
        }
    }


    @FXML
    public void friendClicked() throws RemoteException, NotBoundException {
        counterContainer.setVisible(false);
        counterNumber = 0;
        chatScreenController.currentGroup = this;
        chatScreenController.temporaryScreen.setVisible(false);
        chatScreenController.updateChatLayout(null, chatDto.getChatId());
        chatScreenController.updateConnectionName(chatDto.getChatName());
        chatScreenController.updateCurrentScreenStatusWordForGroups("");
        chatScreenController.chatArea.setVisible(true);
        chatScreenController.isSingleChat = false;
        chatScreenController.leaveGroupBtn.setVisible(true);
        chatScreenController.deleteBtn.setVisible(false);

        if (!messageRestoredFlag) {
            messageRestoredFlag = true;
            chatScreenController.deleteChatsOfChatID(chatDto.getChatId());
            Registry registry = LocateRegistry.getRegistry(ServerIPAddress.getIp(), ServerIPAddress.getPort());
            ChatMessagesService chatMessagesService = ((ServiceFactory) registry.lookup("ServiceFactory")).getChatMessagesService();
            ArrayList<MessageDto> messages = chatMessagesService.getChatMessages(chatDto.getChatId());
            try {
                chatScreenController.getStoredMessage(messages);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void hoverEnterEffect() {
        connectionItem.setStyle("-fx-background-color: rgba(230, 230, 230, 0.7);");
    }

    public void hoverExitEffect() {
        connectionItem.setStyle("-fx-background-color: rgba(230, 230, 230, 0.0);");
    }

    public void setLastTimeStamp(Timestamp timeStamp) {
        this.lastMessageTime = timeStamp;
        lastMessageTimestamp.setText(timeStamp.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void onClickLeaveGroup() throws RemoteException, NotBoundException {
        chatScreenController.chatArea.setVisible(true);
        chatScreenController.leaveGroup(chatDto.getChatId());
    }
}
