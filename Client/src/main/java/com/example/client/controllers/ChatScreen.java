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
        System.out.println("Initializing");
        List<User> connections = getConnections();

        for (User connection : connections) {
            System.out.println("Beginnig of loop");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/client/views/connection-item.fxml"));

            try {
                HBox hbox = fxmlLoader.load();
                ConnectionItem cic = new ConnectionItem();
                cic.setData(connection);
                connectionLayout.getChildren().add(hbox);
            } catch (IOException e) {
                System.out.println("Error");
                //throw new RuntimeException(e);
            }
        }
    }

    private List<User> getConnections() {
        List<User> ls = new ArrayList<>();
        User user = new User();

        user.setName("Omar Elsherif");
        user.setImgSrc("E:\\ITI - EWD\\Courses\\Projects\\Chat-Rmi\\Client\\target\\classes\\com\\example\\client\\img\\user.png");
        user.setStatus(User.Status.Online);
        ls.add(user);

        user = new User();
        user.setName("Ahmed Elsherif");
        user.setImgSrc("E:\\ITI - EWD\\Courses\\Projects\\Chat-Rmi\\Client\\target\\classes\\com\\example\\client\\img\\user.png");
        user.setStatus(User.Status.Offline);
        ls.add(user);

        /*user = new User();
        user.setName("Sherif Elsherif");
        user.setImgSrc("../../../../../resources/com/example/client/img/user.png");
        user.setStatus(User.Status.Offline);
        ls.add(user);

        user = new User();
        user.setName("Youssef Elsherif");
        user.setImgSrc("../../../../../resources/com/example/client/img/user.png");
        user.setStatus(User.Status.Online);
        ls.add(user);

        user = new User();
        user.setName("Nour Elsherif");
        user.setImgSrc("../../../../../resources/com/example/client/img/user.png");
        user.setStatus(User.Status.Offline);
        ls.add(user);*/

        return ls;
    }
}
