package iti.jets.app.client.controllers;

import iti.jets.app.shared.DTOs.UserInvitationDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class InvitationRequestController implements Initializable {

    @FXML
    public ListView<UserInvitationDto> invitationList;

    public static byte[] getImageBytes(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[]{};
        }
    }

    public ObservableList<UserInvitationDto> generateDummyData() {
        UserInvitationDto user1 = new UserInvitationDto("John Doe", "+123456789", 1, getImageBytes("D:\\ITI\\Project\\ChatApp\\Client\\src\\main\\resources\\iti\\jets\\app\\client\\img\\user.png"));
        UserInvitationDto user2 = new UserInvitationDto("Alice Wonderland", "+987654321", 2, getImageBytes("D:\\ITI\\Project\\ChatApp\\Client\\src\\main\\resources\\iti\\jets\\app\\client\\img\\user.png"));
        UserInvitationDto user3 = new UserInvitationDto("Bob Builder", "+111223344", 3, getImageBytes("D:\\ITI\\Project\\ChatApp\\Client\\src\\main\\resources\\iti\\jets\\app\\client\\img\\user.png"));
        return FXCollections.observableArrayList(user1, user2, user3);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<UserInvitationDto> dummyData = generateDummyData();
        invitationList.getItems().addAll(dummyData);
        setRequestsList();
        invitationList.setPrefHeight(dummyData.size() * 40 + 2);
    }

    public void setRequestsList() {
        invitationList.setCellFactory(new Callback<ListView<UserInvitationDto>, ListCell<UserInvitationDto>>() {
            public ListCell<UserInvitationDto> call(ListView<UserInvitationDto> param) {
                final ListCell<UserInvitationDto> cell = new ListCell<UserInvitationDto>() {
                    @Override
                    public void updateItem(UserInvitationDto item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            if (item != null) {
                                Image userImage = new Image(new ByteArrayInputStream(item.getImage()), 30, 30, false, true);
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/iti/jets/app/client/views/invitation-card.fxml"));
                                InvitationRequestCardController requestCardController = new InvitationRequestCardController(item.getUserName(), item.getPhoneNumber(), userImage, item.getUserId());
                                loader.setController(requestCardController);
                                try {
                                    setGraphic(loader.load());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        } else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };
                cell.setStyle("-fx-background-color: #FFFFFF;");
                return cell;
            }
        });
    }
}