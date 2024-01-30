package iti.jets.app.client.controllers;

import iti.jets.app.client.CallBack.ClientImpl;
import iti.jets.app.shared.DTOs.*;
import iti.jets.app.shared.Interfaces.server.ServerService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
import iti.jets.app.shared.enums.StatusEnum;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Timestamp;
import java.util.*;


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
    public Circle currentScreenStatusColor;


    @FXML
    public Label currentScreenStatusWord;


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
    @FXML
    public VBox temporaryScreen;


    private LoginResultDto loginResultDto;
    Integer currentScreenUserId = null;
    Integer currentScreenChatId = null;
    Image currentScreenImage = null;



    HashMap<Integer, Node[]> chatsArr = new HashMap<>();

    ClientImpl client;

    ServerService serverService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setCurrentScreenImage(Image currentScreenImage) {
        this.currentScreenImage = currentScreenImage;
    }

    public void setChatScreenDto(LoginResultDto loginResultDto) throws NotBoundException {
        this.loginResultDto = loginResultDto;
        customInit();
        new Thread(() -> {
            try {
                Registry registry = LocateRegistry.getRegistry(8090);
                client = new ClientImpl(this, loginResultDto.getUserDto().getId());
                ServiceFactory serviceFactory = (ServiceFactory) registry.lookup("ServiceFactory");
                serverService = serviceFactory.getServerService();
                serverService.register(client);
                System.out.println("Client registered successfully");
            } catch (IOException | NotBoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void customInit() {
        List<FriendInfoDto> contactListArray = getContactListArray();
        this.showContactList(contactListArray);

    }

    void updateCurrentScreenChatId(int newChatId) {
        this.currentScreenChatId = newChatId;
    }

    void updateCurrentScreenStatusWord(StatusEnum statusWord) {
        currentScreenStatusWord.setText(statusWord.getStatus());
        String statusColor = getOnlineAndOfflineColor(statusWord);
        currentScreenStatusColor.setFill(Color.web(statusColor));
    }

    private String getOnlineAndOfflineColor (StatusEnum statusWord){
        switch (statusWord){
            case ONLINE:
                return"#50c984";
            default:
                return"#ff0000";

        }
    }

    public void updateConnectionName(String name) {
        connectionName.setText(name);
    }

    public void sendMessageByKeyborad(KeyEvent event) throws IOException {
        if (((KeyEvent) event).getCode() == KeyCode.ENTER) {
            sendMessage();
        }
    }

    public void sendMessage() throws IOException {
        String text = messageTextField.getText().trim();
        if (!text.isEmpty()) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/iti/jets/app/client/views/message-sent.fxml"));
            chatLayout.setAlignment(Pos.CENTER_RIGHT);
            HBox hbox = fxmlLoader.load();
            MessageSentController msc = fxmlLoader.getController();
            Image userImg = new Image(new ByteArrayInputStream(loginResultDto.getUserDto().getPicture()));
            msc.setData(new MessageDto(currentScreenUserId, currentScreenChatId, false, text, new Timestamp(System.currentTimeMillis())) ,userImg );
            chatLayout.getChildren().add(hbox);
            messageTextField.setText("");

            new Thread(() -> {
                try {
                    serverService.sendMessage(new MessageDto(currentScreenUserId, currentScreenChatId, false, text, new Timestamp(System.currentTimeMillis())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void receiveMessage(MessageDto message) {
        Platform.runLater(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/iti/jets/app/client/views/message-receive.fxml"));
            chatLayout.setAlignment(Pos.CENTER_LEFT);
            HBox hbox = null;
            try {
                hbox = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            MessageReceiveController msc = fxmlLoader.getController();
            msc.setData(message , currentScreenImage);
            chatLayout.getChildren().add(hbox);
        });
    }

    public void updateChatLayout(int newUserIdScreen) {
        if (this.currentScreenUserId != null) {
            Node[] currentChildren = chatLayout.getChildren().toArray(new Node[0]);
            chatsArr.put(this.currentScreenUserId, currentChildren);
        }
        chatLayout.getChildren().clear();
        if (chatsArr.containsKey(newUserIdScreen)) {

            chatLayout.getChildren().addAll(chatsArr.get(newUserIdScreen));
        }
        this.currentScreenUserId = newUserIdScreen;
    }

    private void showContactList(List<FriendInfoDto> connections) {
        for (FriendInfoDto connection : connections) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/iti/jets/app/client/views/connection-item.fxml"));
            try {
                HBox hbox = fxmlLoader.load();
                ConnectionItemController cic = fxmlLoader.getController();

                ChatDto associateChatDto = loginResultDto.getUserFriendsAndChatDto().get(connection);
                cic.setData(connection, this, associateChatDto);
                connectionLayout.getChildren().add(hbox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<FriendInfoDto> getContactListArray() {
        List<FriendInfoDto> ls = new ArrayList<>();
        HashMap<FriendInfoDto, ChatDto> userFriendsAndChatDto = loginResultDto.getUserFriendsAndChatDto();
        for (FriendInfoDto friendInfoDto : userFriendsAndChatDto.keySet()) {
            ls.add(friendInfoDto);
        }
        Collections.sort(ls, Comparator.comparing(FriendInfoDto::getUserFriendStatus));
        return ls;
    }
}
