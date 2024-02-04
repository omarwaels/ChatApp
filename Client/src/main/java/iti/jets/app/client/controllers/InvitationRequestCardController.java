package iti.jets.app.client.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.DTOs.InvitationDto;
import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.Interfaces.server.InvitationService;
import iti.jets.app.shared.Interfaces.server.ServerService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
import iti.jets.app.shared.enums.ModeEnum;
import iti.jets.app.shared.enums.StatusEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class InvitationRequestCardController implements Initializable {
    @FXML
    public Circle friendImage;

    @FXML
    public Label friendName;

    @FXML
    public Label friendPhone;

    @FXML
    public MFXButton acceptButton;
    @FXML
    public MFXButton declineButton;

    public InvitationDto invitationDto;

    public UserDto userDto;

    public InvitationRequestController invitationRequestController;

    public ChatScreenController chatScreenController;

    public void setData(InvitationDto invitationDto, InvitationRequestController invitationRequestController, ChatScreenController chatScreenController) {
        this.invitationDto = invitationDto;
        this.invitationRequestController = invitationRequestController;
        this.chatScreenController = chatScreenController;
        this.userDto = chatScreenController.loginResultDto.getUserDto();
        Image image = new Image(new ByteArrayInputStream(this.invitationDto.getSenderImage()));
        friendName.setText(this.invitationDto.getSenderName());
        friendImage.setFill(new ImagePattern(image));
        this.friendPhone.setText(this.invitationDto.getSenderPhone());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    InvitationService getInvitationService() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(8189);
        return ((ServiceFactory) registry.lookup("ServiceFactory")).getInvitationService();
    }

    @FXML
    public void acceptInvitation(ActionEvent event) throws NotBoundException, IOException, SQLException {
        ChatDto chatDto = getInvitationService().acceptInvitation(invitationDto);
        invitationRequestController.deleteInvitationCard(invitationDto);
        chatScreenController.addNewFriendInContactList(invitationDtoToFriendInfoDto(invitationDto), chatDto);
        // Check validity of the following line
        ServerService serverService = chatScreenController.getServerService();
        serverService.addChatForNewFriend(invitationDto.getSenderID(), UserDtoToFriendInfoDto(userDto), chatDto);
    }

    private FriendInfoDto UserDtoToFriendInfoDto(UserDto userDto) {
        System.out.println(userDto.getMode());
        return new FriendInfoDto(userDto.getDisplayName(), userDto.getPicture(), userDto.getId(), userDto.getMode(), StatusEnum.ONLINE);
    }

    @FXML
    public void declineInvitation(ActionEvent event) throws NotBoundException, RemoteException {
        getInvitationService().declineInvitation(invitationDto);
        invitationRequestController.deleteInvitationCard(invitationDto);
    }

    private FriendInfoDto invitationDtoToFriendInfoDto(InvitationDto invitationDto) throws NotBoundException, RemoteException {
        StatusEnum userStatus = getInvitationService().getUserStatusById(invitationDto.getSenderID());
        ModeEnum userMode = getInvitationService().getUserModeById(invitationDto.getSenderID());
        return new FriendInfoDto(invitationDto.getSenderName(), invitationDto.getSenderImage(), invitationDto.getSenderID(), userMode, userStatus);
    }
}
