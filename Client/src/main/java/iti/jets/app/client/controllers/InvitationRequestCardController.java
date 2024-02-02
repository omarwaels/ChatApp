package iti.jets.app.client.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import iti.jets.app.shared.DTOs.UserInvitationDto;
import iti.jets.app.shared.Interfaces.server.InvitationService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class InvitationRequestCardController implements Initializable {
    @FXML
    public ImageView friendImage;

    @FXML
    public Label friendName;

    @FXML
    public Label friendPhone;

    @FXML
    public MFXButton acceptButton;
    @FXML
    public MFXButton declineButton;
    public   String name;
    public   String phone;
    public   Image image;
    public   int friendId;
    public UserInvitationDto userInvitationDto;

    public void setData(String friendName, String friendPhoneNumber, Image friendImage, int id, UserInvitationDto userInvitationDto) {
        this.friendId = id;
        this.name = friendName;
        this.phone = friendPhoneNumber;
        this.image = friendImage;
        this.userInvitationDto = userInvitationDto;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        friendName.setText(name);
        friendImage.setImage(image);
        Circle circle = new Circle(25, 25, 25);
        friendImage.setClip(circle);
        friendPhone.setText(phone);
    }

    InvitationService getInvitationService() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(8189);
        return ((ServiceFactory) registry.lookup("ServiceFactory")).getInvitationService();
    }

    @FXML
    public void acceptInvitation(ActionEvent event) throws NotBoundException, RemoteException {
        System.out.println("ddd");
        getInvitationService().acceptInvitation(userInvitationDto.getInvitationId(),1);
    }

    @FXML
    public void declineInvitation(ActionEvent event) {

    }
}
