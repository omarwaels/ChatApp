package iti.jets.app.client.controllers;
import iti.jets.app.shared.DTOs.*;
import iti.jets.app.shared.enums.StatusEnum;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
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
    private ChatScreenDto  chatScreenDto;
    Integer currentScreenUserId = null ;
    Integer currentScreenChatId = null ;
    HashMap<Integer,Node[]> chatsArr = new HashMap<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void setChatScreenDto(ChatScreenDto chatScreenDto) {
        this.chatScreenDto = chatScreenDto;
        customInit();
    }
    public void customInit() {
        List<FriendInfoDto> contactListArray = getContactListArray();
        this.showContactList(contactListArray);
    }

    void updateCurrentScreenChatId(int newChatId ){
        this.currentScreenChatId = newChatId;
    }

    public void updateConnectionName(String name){
        connectionName.setText(name);
    }

    public void sendMessageByKeyborad(KeyEvent event) throws IOException {
        if ( ((KeyEvent) event).getCode() == KeyCode.ENTER){
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
                msc.setData(new MessageDto(currentScreenUserId,currentScreenChatId,false,text,new Timestamp(System.currentTimeMillis()) ));
                chatLayout.getChildren().add(hbox);

            }
    }

    public void updateChatLayout(int newUserIdScreen) {

        if(this.currentScreenUserId != null){
            Node[] currentChildren = chatLayout.getChildren().toArray(new Node[0]);
            chatsArr.put(this.currentScreenUserId , currentChildren);

        }
        chatLayout.getChildren().clear();
        if(chatsArr.containsKey(newUserIdScreen) ){

            chatLayout.getChildren().addAll(chatsArr.get(newUserIdScreen));
        }

        this.currentScreenUserId = newUserIdScreen ;


    }


    private void showContactList (List<FriendInfoDto> connections){
        for (FriendInfoDto connection : connections) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/iti/jets/app/client/views/connection-item.fxml"));
            try {
                HBox hbox = fxmlLoader.load();
                ConnectionItemController cic = fxmlLoader.getController();
                ChatDto associateChatDto = chatScreenDto.getUserFriendsAndChatDto().get(connection);
                cic.setData(connection,this, associateChatDto);
                connectionLayout.getChildren().add(hbox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<FriendInfoDto> getContactListArray() {
        List<FriendInfoDto> ls = new ArrayList<>();
        HashMap<FriendInfoDto, ChatDto> userFriendsAndChatDto = chatScreenDto.getUserFriendsAndChatDto();
        for (FriendInfoDto friendInfoDto : userFriendsAndChatDto.keySet()) {
            ls.add(friendInfoDto);
        }
        return ls;
    }


}
