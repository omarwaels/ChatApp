package iti.jets.app.client.controllers;

import iti.jets.app.client.CallBack.ClientImpl;
import iti.jets.app.client.utils.ViewsFactory;
import iti.jets.app.shared.DTOs.*;
import iti.jets.app.shared.Interfaces.server.ServerService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;


public class ChatScreenController implements Initializable {
    @FXML
    public ImageView attachementBtn;
    @FXML
    public BorderPane chatBorderPane;
    @FXML
    public VBox chatArea;

    @FXML
    public HBox chatHeader;

    @FXML
    public VBox chatLayout;

    @FXML
    public VBox connectionLayout;

    @FXML
    public Label connectionName;

    @FXML
    public ScrollPane connectionsScrollPane;

    @FXML
    public ImageView emojiBtn;

    @FXML
    public TextField messageTextField;

    @FXML
    public ImageView sendBtn;

    @FXML
    public Circle statusColor;

    @FXML
    public Label statusWord;

    @FXML
    public ImageView threeDotsBtn;

    @FXML
    public ImageView videoCallBtn;

    @FXML
    public ImageView voiceCallBtn;

    @FXML
    public ScrollPane chatScrollPane;
    @FXML
    public HBox chatFooter;
    private LoginResultDto loginResultDto;
    Integer currentScreenUserId = null;
    Integer currentScreenChatId = null;
    HashMap<Integer, Node[]> chatsArr = new HashMap<>();
    ClientImpl client;
    ServerService serverService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setChatScreenDto(LoginResultDto loginResultDto) throws IOException {
        this.loginResultDto = loginResultDto;
        customInit();
        new Thread(() -> {
            try {
                client = new ClientImpl(this, loginResultDto.getUserDto().getId());
                serverService = getServerService();
                serverService.register(client);
            } catch (IOException | NotBoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    ServerService getServerService() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(8189);
        return ((ServiceFactory) registry.lookup("ServiceFactory")).getServerService();
    }

    public void customInit() throws IOException {
        List<FriendInfoDto> contactListArray = getContactListArray();
        this.showContactList(contactListArray);
    }

    void updateCurrentScreenChatId(int newChatId) {
        this.currentScreenChatId = newChatId;
    }

    public void updateConnectionName(String name) {
        connectionName.setText(name);
    }

    public void sendMessageByKeyboard(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER)
            sendMessage();
    }

    public void sendMessage() throws IOException {
        String text = messageTextField.getText().trim();
        messageTextField.setText("");
        if (!text.isEmpty()) {
            FXMLLoader fxmlLoader = ViewsFactory.getViewsFactory().getMessageSentLoader();
            HBox hbox = fxmlLoader.load();
            MessageDto newMessage = createMessageDto(text);
            MessageSentController msc = fxmlLoader.getController();
            msc.setData(newMessage);
            chatLayout.setAlignment(Pos.CENTER_RIGHT);
            chatLayout.getChildren().add(hbox);
            new Thread(() -> {
                try {
                    serverService.sendMessage(newMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private MessageDto createMessageDto(String text) {
        return new MessageDto(currentScreenUserId, currentScreenChatId, false, text, new Timestamp(System.currentTimeMillis()));
    }

    public void receiveMessage(MessageDto message) {
        Platform.runLater(() -> {
            FXMLLoader fxmlLoader = ViewsFactory.getViewsFactory().getMessageReceivedLoader();
            HBox hbox = null;
            try {
                hbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            MessageReceiveController msc = fxmlLoader.getController();
            msc.setData(message);
            chatLayout.setAlignment(Pos.CENTER_LEFT);
            chatLayout.getChildren().add(hbox);
        });
    }

    public void updateChatLayout(int newUserIdScreen) {
        if (this.currentScreenUserId != null) {
            Node[] currentChildren = chatLayout.getChildren().toArray(new Node[0]);
            chatsArr.put(this.currentScreenUserId, currentChildren);
        }
        chatLayout.getChildren().clear();
        if (chatsArr.containsKey(newUserIdScreen))
            chatLayout.getChildren().addAll(chatsArr.get(newUserIdScreen));
        this.currentScreenUserId = newUserIdScreen;
    }

    private void showContactList(List<FriendInfoDto> connections) throws IOException {
        for (FriendInfoDto connection : connections) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/iti/jets/app/client/views/connection-item.fxml"));
            HBox hbox = fxmlLoader.load();
            ConnectionItemController cic = fxmlLoader.getController();
            ChatDto associateChatDto = loginResultDto.getUserFriendsAndChatDto().get(connection);
            cic.setData(connection, this, associateChatDto);
            connectionLayout.getChildren().add(hbox);
        }
    }

    private List<FriendInfoDto> getContactListArray() {
        return new ArrayList<>(loginResultDto.getUserFriendsAndChatDto().keySet());
    }
}
