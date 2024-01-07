package com.example.client.controllers;


import com.example.client.Models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChatScreen implements Initializable {
    @FXML
    public VBox connectionLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<User> connections = getConnections();

        for (User connection : connections) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/client/views/connection-item.fxml"));

            try {
                HBox hbox = fxmlLoader.load();
                ConnectionItem cic = fxmlLoader.getController();
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
                .setImgSrc("/com/example/client/img/OSherif.jpg")
                .setStatus(User.Status.Online)
                .createUser();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Ahmed Elsherif")
                .setImgSrc("/com/example/client/img/ASherif.jpg")
                .setStatus(User.Status.Offline)
                .createUser();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Sherif Elsherif")
                .setImgSrc("/com/example/client/img/SSherif.jpg")
                .setStatus(User.Status.Online)
                .createUser();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Youssef Elsherif")
                .setImgSrc("/com/example/client/img/YSherif.jpg")
                .setStatus(User.Status.Online)
                .createUser();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/com/example/client/img/NSherif.jpg")
                .setStatus(User.Status.Online)
                .createUser();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/com/example/client/img/NSherif.jpg")
                .setStatus(User.Status.Online)
                .createUser();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/com/example/client/img/NSherif.jpg")
                .setStatus(User.Status.Online)
                .createUser();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/com/example/client/img/NSherif.jpg")
                .setStatus(User.Status.Online)
                .createUser();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/com/example/client/img/NSherif.jpg")
                .setStatus(User.Status.Online)
                .createUser();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/com/example/client/img/NSherif.jpg")
                .setStatus(User.Status.Online)
                .createUser();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/com/example/client/img/NSherif.jpg")
                .setStatus(User.Status.Online)
                .createUser();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/com/example/client/img/NSherif.jpg")
                .setStatus(User.Status.Online)
                .createUser();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/com/example/client/img/NSherif.jpg")
                .setStatus(User.Status.Online)
                .createUser();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/com/example/client/img/NSherif.jpg")
                .setStatus(User.Status.Online)
                .createUser();
        ls.add(user);

        user = new User.UserBuilder().setDisplayName("Nour Elsherif")
                .setImgSrc("/com/example/client/img/NSherif.jpg")
                .setStatus(User.Status.Online)
                .createUser();
        ls.add(user);

        return ls;
    }
}
