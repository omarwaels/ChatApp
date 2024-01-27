package iti.jets.app.client.controllers;
import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.ChatScreenDto;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.enums.StatusEnum;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

class Message {
    private int type;
    private String message;


    public Message(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}

public class ChatScreenController implements Initializable {


    private ChatScreenDto  chatScreenDto;

    public void setChatScreenDto(ChatScreenDto chatScreenDto) {
        this.chatScreenDto = chatScreenDto;
        customInit();
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void customInit() {
        List<UserDto> connections = getConnections();
        List<Message> messages = getMessages();
        for(Message message : messages)
        {
            if(message.getType() == 1)
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/iti/jets/app/client/views/message-sent.fxml"));
                try {
                    chatLayout.setAlignment(Pos.CENTER_RIGHT);
                    HBox hbox = fxmlLoader.load();
                    MessageSentController msc = fxmlLoader.getController();
                    msc.setData(message);
                    chatLayout.getChildren().add(hbox);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/iti/jets/app/client/views/message-receive.fxml"));
                try {
                    chatLayout.setAlignment(Pos.CENTER_LEFT);
                    HBox hbox = fxmlLoader.load();
                    MessageReceiveController msc = fxmlLoader.getController();
                    msc.setData(message);
                    chatLayout.getChildren().add(hbox);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        }

        for (UserDto connection : connections) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/iti/jets/app/client/views/connection-item.fxml"));

            try {
                HBox hbox = fxmlLoader.load();
                ConnectionItemController cic = fxmlLoader.getController();
                cic.setData(connection);
                connectionLayout.getChildren().add(hbox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<UserDto> getConnections() {
        List<UserDto> ls = new ArrayList<>();
        HashMap<FriendInfoDto, ChatDto> userFriendsAndChatDto = chatScreenDto.getUserFriendsAndChatDto();
        for (FriendInfoDto friendInfoDto : userFriendsAndChatDto.keySet()) {
            // Access each key (FriendInfoDto) here


            UserDto user = new UserDto.UserDtoBuilder().setDisplayName(friendInfoDto.getUserFriendName())
                .setStatus(friendInfoDto.getUserFriendStatus())
                    .setPicture(friendInfoDto.getUserFriendPhoto())
                .build();
            ls.add(user);
        }



//        UserDto user = new UserDto.UserDtoBuilder().setDisplayName("Omar Elsherif")
//                .setStatus(StatusEnum.ONLINE)
//                .build();
//        ls.add(user);
//
//        user = new UserDto.UserDtoBuilder().setDisplayName("Ahmed Elsherif")
//                .setStatus(StatusEnum.OFFLINE)
//                .build();
//        ls.add(user);
//
//        user = new UserDto.UserDtoBuilder().setDisplayName("Sherif Elsherif")
//                .setStatus(StatusEnum.ONLINE)
//                .build();
//        ls.add(user);
//
//        user = new UserDto.UserDtoBuilder().setDisplayName("Youssef Elsherif")
//                .setStatus(StatusEnum.ONLINE)
//                .build();
//        ls.add(user);
//
//        user = new UserDto.UserDtoBuilder().setDisplayName("Nour Elsherif")
//                .setStatus(StatusEnum.ONLINE)
//                .build();
//        ls.add(user);

        return ls;
    }
    private List<Message> getMessages()
    {
        List<Message> ls = new ArrayList<>();
        Message message = new Message(1,"Hejjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjllo");
        ls.add(message);
        message = new Message(2,"Hi");
        ls.add(message);
        message = new Message(1,"How are you?");
        ls.add(message);
        message = new Message(2,"I'm fine, thanks");
        ls.add(message);
        message = new Message(1,"What about you?");
        ls.add(message);
        message = new Message(2,"I'm fine too");
        ls.add(message);
        message = new Message(1,"Good to hear that");
        ls.add(message);
        message = new Message(2,"Good to hear that too");
        ls.add(message);
        message = new Message(1,"Goodbye");
        ls.add(message);
        message = new Message(2,"Goodbye");
        ls.add(message);
        return ls;
    }
}
