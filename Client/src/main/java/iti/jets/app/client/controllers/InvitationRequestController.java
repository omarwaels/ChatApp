package iti.jets.app.client.controllers;

import iti.jets.app.client.utils.ServerIPAddress;
import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.DTOs.InvitationDto;
import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.Interfaces.server.InvitationService;
import iti.jets.app.shared.Interfaces.server.ServerService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
import iti.jets.app.shared.enums.ModeEnum;
import iti.jets.app.shared.enums.StatusEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class InvitationRequestController implements Initializable {

    @FXML
    public ListView<InvitationDto> invitationList;

    public int userId;

    public ChatScreenController chatScreenController;

    public void setData(int userId, ChatScreenController chatScreenController) {
        this.userId = userId;
        this.chatScreenController = chatScreenController;
        ObservableList<InvitationDto> data = generateDataObservable();
        invitationList.getItems().addAll(data);
        setRequestsList();
    }

    InvitationService getInvitationService() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ServerIPAddress.getIp(),ServerIPAddress.getPort());
        return ((ServiceFactory) registry.lookup("ServiceFactory")).getInvitationService();
    }

    public ObservableList<InvitationDto> generateDataObservable() {
        ObservableList<InvitationDto> observableList = FXCollections.observableArrayList();
        try {
            InvitationService invitationService = getInvitationService();
            List<InvitationDto> userRequests = invitationService.getUserRequests(userId);

            observableList.addAll(userRequests);
        } catch (NotBoundException | RemoteException e) {
            e.printStackTrace();
        }
        return observableList;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setRequestsList() {
        invitationList.setCellFactory(new Callback<ListView<InvitationDto>, ListCell<InvitationDto>>() {
            public ListCell<InvitationDto> call(ListView<InvitationDto> param) {
                final ListCell<InvitationDto> cell = new ListCell<InvitationDto>() {
                    @Override
                    public void updateItem(InvitationDto item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            if (item != null) {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/iti/jets/app/client/views/invitation-card.fxml"));
                                Parent root;
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                InvitationRequestCardController invitationRequestCardController = loader.getController();
                                invitationRequestCardController.setData(item, InvitationRequestController.this, chatScreenController);
                                try {
                                    setGraphic(root);
                                } catch (Exception e) {
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

    public void deleteInvitationCard(InvitationDto invitationDto) {
        invitationList.getItems().remove(invitationDto);
    }

    public void deleteInvitationCard(String phone) throws NotBoundException, IOException, SQLException {
        for (InvitationDto invitationDto : invitationList.getItems()) {
            if (invitationDto.getSenderPhone().equals(phone)) {
                invitationList.getItems().remove(invitationDto);
                deleteInvitationCard(invitationDto);
                ChatDto chatDto = getInvitationService().acceptInvitation(invitationDto);
                chatScreenController.addNewFriendInContactList(invitationDtoToFriendInfoDto(invitationDto), chatDto);
                ServerService serverService = chatScreenController.getServerService();
                serverService.addChatForNewFriend(invitationDto.getSenderID(), UserDtoToFriendInfoDto(chatScreenController.loginResultDto.getUserDto()), chatDto);
                break;
            }
        }
    }

    public void addInvitationCard(InvitationDto invitationDto) {
        invitationList.getItems().add(invitationDto);
    }

    private FriendInfoDto invitationDtoToFriendInfoDto(InvitationDto invitationDto) throws NotBoundException, RemoteException {
        StatusEnum userStatus = getInvitationService().getUserStatusById(invitationDto.getSenderID());
        ModeEnum userMode = getInvitationService().getUserModeById(invitationDto.getSenderID());
        return new FriendInfoDto(invitationDto.getSenderName(), invitationDto.getSenderImage(), invitationDto.getSenderID(), userMode, userStatus);
    }

    private FriendInfoDto UserDtoToFriendInfoDto(UserDto userDto) {
        return new FriendInfoDto(userDto.getDisplayName(), userDto.getPicture(), userDto.getId(), userDto.getMode(), StatusEnum.ONLINE);
    }

}