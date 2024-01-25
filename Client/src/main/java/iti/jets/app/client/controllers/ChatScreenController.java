package iti.jets.app.client.controllers;



import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.enums.StatusEnum;
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
        List<UserDto> connections = getConnections();

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
        UserDto user = new UserDto.UserDtoBuilder().setDisplayName("Omar Elsherif")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new UserDto.UserDtoBuilder().setDisplayName("Ahmed Elsherif")
                .setStatus(StatusEnum.OFFLINE)
                .build();
        ls.add(user);

        user = new UserDto.UserDtoBuilder().setDisplayName("Sherif Elsherif")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new UserDto.UserDtoBuilder().setDisplayName("Youssef Elsherif")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        user = new UserDto.UserDtoBuilder().setDisplayName("Nour Elsherif")
                .setStatus(StatusEnum.ONLINE)
                .build();
        ls.add(user);

        return ls;
    }
}
