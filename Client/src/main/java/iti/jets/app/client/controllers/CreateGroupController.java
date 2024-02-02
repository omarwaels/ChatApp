package iti.jets.app.client.controllers;

import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.Interfaces.server.CreateGroupService;
import iti.jets.app.shared.Interfaces.server.ServerService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CreateGroupController implements Initializable {

    @FXML
    public Button btnCreate;

    @FXML
    public ListView<String> listOfConnections;

    @FXML
    public TextField txtFieldGroupName;

    public List<FriendInfoDto> friends;

    public int userId;

    List<String> groupMembers;


    public CreateGroupController() {
        groupMembers = new ArrayList<>();
    }

    @FXML
    public void btnCreateAction(ActionEvent event) throws IOException, NotBoundException {
        String groupName = txtFieldGroupName.getText();
        if (groupName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Group Name is Empty");
            alert.setContentText("Please Enter Group Name");
            alert.showAndWait();
        } else if (groupMembers.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Members Selected");
            alert.setContentText("Please Select Members");
            alert.showAndWait();
        } else {
            List<Integer> membersIds = new ArrayList<>();
            membersIds.add(this.userId);
            for (String member : groupMembers) {
                for (FriendInfoDto friend : friends) {
                    if (friend.getUserFriendName().equals(member)) {
                        membersIds.add(friend.getUserFriendID());
                        break;
                    }
                }
            }
            System.out.println(membersIds);
            ChatDto chatDto = createChatDto(groupName);
            Registry registry = LocateRegistry.getRegistry(8189);
            CreateGroupService createGroupService = ((ServiceFactory) registry.lookup("ServiceFactory")).getCreateGroupService();
            int ret = createGroupService.createGroup(chatDto, membersIds);
            System.out.println(ret);
            txtFieldGroupName.setText("");
            if (ret != -1) {
                chatDto.setChatId(ret);
                ServerService serverService = ((ServiceFactory) registry.lookup("ServiceFactory")).getServerService();
                serverService.addGroup(chatDto, membersIds);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Group Created Successfully");
                alert.setContentText("Group Created Successfully");
                alert.showAndWait();
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listOfConnections.setCellFactory(listView -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    CheckBox checkbox = new CheckBox(item);
                    checkbox.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (!groupMembers.contains(checkbox.getText()))
                                groupMembers.add(checkbox.getText());
                            else
                                groupMembers.remove(checkbox.getText());
                        }
                    });
                    setGraphic(checkbox);
                }
            }
        });
    }

    public void setData(List<FriendInfoDto> friends, int userId) {
        this.userId = userId;
        this.friends = friends;
        ObservableList<String> data = FXCollections.observableArrayList();
        for (FriendInfoDto friend : friends) {
            // TODO: replace the ID with the phone number
            data.add(friend.getUserFriendName());
        }
        listOfConnections.setItems(data);
    }

    private ChatDto createChatDto(String groupName) throws IOException {
        ChatDto chatDto = new ChatDto();
        chatDto.setChatName(groupName);
        chatDto.setAdminId(userId);
        File img = new File("D:\\ITI\\Project\\ChatApp\\Client\\src\\main\\resources\\iti\\jets\\app\\client\\img\\user.png");
        chatDto.setChatImage(Files.readAllBytes(img.toPath()));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LocalDateTime localDate = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = localDate.format(formatter);
        timestamp = Timestamp.valueOf(formattedTimestamp);
        chatDto.setCreatedAt(timestamp);
        return chatDto;
    }
}