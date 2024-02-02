package iti.jets.app.client.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import iti.jets.app.shared.DTOs.InvitationDto;
import iti.jets.app.shared.Interfaces.server.InvitationService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
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

    public InvitationDto invitationDto;

    public InvitationRequestController invitationRequestController;

    public void setData(InvitationDto invitationDto, InvitationRequestController invitationRequestController) {
        this.invitationDto = invitationDto;
        this.invitationRequestController = invitationRequestController;
        Circle circle = new Circle(25, 25, 25);
        Image image = new Image(new ByteArrayInputStream(this.invitationDto.getSenderImage()));
        friendName.setText(this.invitationDto.getSenderName());
        this.friendImage.setImage(image);
        this.friendImage.setClip(circle);
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
    public void acceptInvitation(ActionEvent event) throws NotBoundException, RemoteException {
        getInvitationService().acceptInvitation(invitationDto);
        invitationRequestController.deleteInvitationCard(invitationDto);
    }

    @FXML
    public void declineInvitation(ActionEvent event) throws NotBoundException, RemoteException {
        getInvitationService().declineInvitation(invitationDto);
        invitationRequestController.deleteInvitationCard(invitationDto);
    }
}
