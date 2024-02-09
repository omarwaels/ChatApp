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
import javafx.scene.layout.Region;
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

public class ConnectionItemController implements Initializable {
    @FXML
    public Label connectionName;
    @FXML
    public Circle connectionPic;
    @FXML
    public Circle connectionStatus;
    @FXML
    public HBox connectionItem;
    @FXML
    public StackPane counterContainer;
    @FXML
    public Label counter;
    @FXML
    public Label lastMessageTimestamp;
    @FXML
    public Label userModeLabel;
    private int UserID;
    private Timestamp lastMessageTime = new Timestamp(System.currentTimeMillis());

    public FriendInfoDto user;
    private ChatDto chatDto;
    private ChatScreenController chatScreenController;
    private Image userImage;
    private boolean messageRestoredFlag = false;

    public void setData(FriendInfoDto user, ChatScreenController chatScreenController, ChatDto chatDto) {
        this.user = user;
        this.chatScreenController = chatScreenController;
        this.chatDto = chatDto;
        connectionName.setText(user.getUserFriendName());
        connectionStatus.setFill(user.getUserFriendStatus() == StatusEnum.ONLINE ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
        UserID = user.getUserFriendID();
        if (user.getUserFriendPhoto() != null) {
            Image userImg = new Image(new ByteArrayInputStream(user.getUserFriendPhoto()));
            connectionPic.setFill(new ImagePattern(userImg));
            this.userImage = userImg;
        }

        System.out.println("User Mode: " + user.getUserFriendStatus().getStatus());
        if (user.getUserFriendStatus().getStatus().equalsIgnoreCase("Online")) {
            userModeLabel.setText(user.getUserFriendMode().getMode());
            switch (user.getUserFriendMode().getMode()) {
                case "Available":
                    userModeLabel.setStyle("-fx-text-fill: green;");
                    break;
                case "Busy":
                    userModeLabel.setStyle("-fx-text-fill: orange;");
                    break;
                case "Away":
                    userModeLabel.setStyle("-fx-text-fill: yellow;");
                    break;
            }
        } else
            userModeLabel.setText("");
    }

    private Integer counterNumber = 0;

    public void updateCounter() {
        counterContainer.setVisible(false);
        counterNumber = counterNumber + 1;
        counter.setText(counterNumber.toString());
        counterContainer.setVisible(true);
    }


    @FXML
    public void friendClicked() throws RemoteException, NotBoundException {
        chatScreenController.currentConnection = this;
        counterContainer.setVisible(false);
        counterNumber = 0;
        chatScreenController.temporaryScreen.setVisible(false);
        chatScreenController.updateChatLayout(UserID, chatDto.getChatId());
        chatScreenController.updateConnectionName(user.getUserFriendName());
        chatScreenController.updateCurrentScreenStatusWord(user.getUserFriendStatus());
        chatScreenController.chatArea.setVisible(true);
        chatScreenController.setCurrentScreenImage(userImage);
        chatScreenController.currentConnection = this;
        chatScreenController.isSingleChat = true;
        chatScreenController.leaveGroupBtn.setVisible(false);
        chatScreenController.deleteBtn.setVisible(true);


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

    public void onDeleteFriend() throws NotBoundException, RemoteException {
        chatScreenController.chatArea.setVisible(false);
        chatScreenController.temporaryScreen.setVisible(true);
        chatScreenController.deleteFriend(user.getUserFriendID(), chatDto.getChatId());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
