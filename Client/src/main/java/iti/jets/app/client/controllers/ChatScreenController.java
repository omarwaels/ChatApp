package iti.jets.app.client.controllers;

import iti.jets.app.client.CallBack.ClientImpl;
import iti.jets.app.client.utils.ViewsFactory;
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
import java.rmi.RemoteException;
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
    HashMap<Integer, Image> allUserFriendsImg = new HashMap<>();

    ClientImpl client;
    ServerService serverService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setCurrentScreenImage(Image currentScreenImage) {
        this.currentScreenImage = currentScreenImage;
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
        saveUserFriendsImgs(contactListArray);
        this.showContactList(contactListArray);

    }

    private void saveUserFriendsImgs (List<FriendInfoDto> contactListArray){
        for(FriendInfoDto friend : contactListArray){
            int friendId = friend.getUserFriendID();
            Image image = null ;
            if(friend.getUserFriendPhoto() != null){
                image =new Image(new ByteArrayInputStream(friend.getUserFriendPhoto()));
            }
            allUserFriendsImg.put(friendId, image);
        }
    }

    void updateCurrentScreenChatId(int newChatId) {
        this.currentScreenChatId = newChatId;
    }

    void updateCurrentScreenStatusWord(StatusEnum statusWord) {
        currentScreenStatusWord.setText(statusWord.getStatus());
        String statusColor = getOnlineAndOfflineColor(statusWord);
        currentScreenStatusColor.setFill(Color.web(statusColor));
    }

    private String getOnlineAndOfflineColor(StatusEnum statusWord) {
        switch (statusWord) {
            case ONLINE:
                return "#50c984";
            default:
                return "#ff0000";
        }
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
            Image userImg = new Image(new ByteArrayInputStream(loginResultDto.getUserDto().getPicture()));
            msc.setData(newMessage, userImg);
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
        return new MessageDto(loginResultDto.getUserDto().getId() , currentScreenUserId, currentScreenChatId, false, text, new Timestamp(System.currentTimeMillis()));
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
            msc.setData(message, allUserFriendsImg.get(message.getSenderId()));
            //update Current Screen

            if( currentScreenChatId != null && currentScreenChatId.equals(message.getChatId()) ){

                chatLayout.setAlignment(Pos.CENTER_LEFT);
                chatLayout.getChildren().add(hbox);
            }else{
                //Add message between nodes

                //If chat screen array includes the chat
                if (chatsArr.containsKey(message.getChatId())){
                    System.out.println(chatsArr.get(message.getChatId()).length);
                    Node[] nodes = chatsArr.get(message.getChatId());
                    ArrayList<Node> nodeList = new ArrayList<>(Arrays.asList(nodes));
                    nodeList.add(hbox);
                    Node[] updatedNodesArray = nodeList.toArray(new Node[0]);
                    chatsArr.put(message.getChatId(), updatedNodesArray);
                }else{
                    //If the chat didn't start
                    ArrayList<Node> nodeList = new ArrayList<>();
                    nodeList.add(hbox);
                    Node[] nodesArray = nodeList.toArray(new Node[0]);
                    chatsArr.put(message.getChatId(),nodesArray);
                }
            }
        });
    }

    public void updateChatLayout(int newUserIdScreen , int newChatIdScreen) {
        if (this.currentScreenChatId != null) {
            Node[] currentChildren = chatLayout.getChildren().toArray(new Node[0]);
            chatsArr.put(this.currentScreenChatId, currentChildren);
        }
        chatLayout.getChildren().clear();
        if (chatsArr.containsKey(newChatIdScreen)) {
            System.out.println(chatsArr.get(newChatIdScreen).length);
            System.out.println("reached here");
            chatLayout.getChildren().addAll(chatsArr.get(newChatIdScreen));

        }
        //Just update the current Screen user Id & current Screen chat Id
        this.currentScreenUserId = newUserIdScreen;
        this.currentScreenChatId = newChatIdScreen;
    }

    private void showContactList(List<FriendInfoDto> connections) throws IOException {
        for (FriendInfoDto connection : connections) {
            FXMLLoader fxmlLoader = ViewsFactory.getViewsFactory().getConnectionLoader();
            HBox hbox = fxmlLoader.load();
            ConnectionItemController connectionItemController = fxmlLoader.getController();
            ChatDto associateChatDto = loginResultDto.getUserFriendsAndChatDto().get(connection);
            connectionItemController.setData(connection, this, associateChatDto);
            connectionLayout.getChildren().add(hbox);
        }
    }

    private List<FriendInfoDto> getContactListArray() {
        HashMap<FriendInfoDto, ChatDto> userFriendsAndChatDto = loginResultDto.getUserFriendsAndChatDto();
        List<FriendInfoDto> ls = new ArrayList<>(userFriendsAndChatDto.keySet());
        ls.sort(Comparator.comparing(FriendInfoDto::getUserFriendStatus));
        return ls;
    }
}
