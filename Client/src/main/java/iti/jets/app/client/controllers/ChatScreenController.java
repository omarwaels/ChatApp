package iti.jets.app.client.controllers;


import iti.jets.app.client.models.entities.Message;
import iti.jets.app.client.models.entities.User;
import iti.jets.app.client.models.enums.StatusEnum;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChatScreenController implements Initializable {
    @FXML
    public VBox connectionLayout;
    @FXML
    public VBox chatLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        List<User> connections = getConnections();

        for (User connection : connections) {
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

    private List<User> getConnections() {
        List<User> ls = new ArrayList<>();
        User user = new User.UserBuilder().setDisplayName("Omar Elsherif")
                .setImgSrc("/iti/jets/app/client/img/OSherif.jpg")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Ahmed Elsherif")
                .setImgSrc("/iti/jets/app/client/img/ASherif.jpg")
                .setStatus(StatusEnum.OFFLINE)
                .build();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Sherif Elsherif")
                .setImgSrc("/iti/jets/app/client/img/SSherif.jpg")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Youssef Elsherif")
                .setImgSrc("/iti/jets/app/client/img/YSherif.jpg")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/iti/jets/app/client/img/NSherif.jpg")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/iti/jets/app/client/img/NSherif.jpg")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/iti/jets/app/client/img/NSherif.jpg")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/iti/jets/app/client/img/NSherif.jpg")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/iti/jets/app/client/img/NSherif.jpg")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/iti/jets/app/client/img/NSherif.jpg")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/iti/jets/app/client/img/NSherif.jpg")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/iti/jets/app/client/img/NSherif.jpg")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/iti/jets/app/client/img/NSherif.jpg")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/iti/jets/app/client/img/NSherif.jpg")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/iti/jets/app/client/img/NSherif.jpg")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

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
