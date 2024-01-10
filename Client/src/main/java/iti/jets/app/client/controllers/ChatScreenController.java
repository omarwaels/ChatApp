package iti.jets.app.client.controllers;


import iti.jets.app.client.models.entities.User;
import iti.jets.app.client.models.enums.StatusEnum;
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

public class ChatScreenController implements Initializable {
    @FXML
    public VBox connectionLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
}
