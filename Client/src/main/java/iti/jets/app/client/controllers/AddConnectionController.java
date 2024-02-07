package iti.jets.app.client.controllers;

import iti.jets.app.client.utils.ServerIPAddress;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.DTOs.InvitationDto;
import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.Interfaces.server.InvitationService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddConnectionController {

    @FXML
    public ImageView addTextFieldIcon;

    @FXML
    public Button btnInvite;

    @FXML
    public ListView<TextField> listOfInvitations;

    UserDto user;

    ServiceFactory serviceFactory;

    ChatScreenController chatScreenController;

    @FXML
    public void addField(MouseEvent event) {
        TextField newField = new TextField();
        newField.setPromptText("Enter Phone Number");
        ((ObservableList<TextField>) listOfInvitations.getItems()).add(newField);
    }

    @FXML
    public void sendInvitation(ActionEvent event) throws IOException, SQLException, NotBoundException {
        ArrayList<String> users = new ArrayList<>();
        for (TextField field : listOfInvitations.getItems()) {
            String text = field.getText();
            if (text != null && !text.isEmpty()) {
                users.add(text);
            }
        }
        listOfInvitations.getItems().removeAll(listOfInvitations.getItems());
        List<Integer> ret = validateUsers(users);
        for (int i = 0; i < ret.size(); i++) {
            if (ret.get(i) == 1) {
                showNotRegisteredAlert(users.get(i));
            } else if (ret.get(i) == 2) {
                showAlreadyConnectedAlert(users.get(i));
            } else if (ret.get(i) == 3) {
                showAlreadyInvitedAlert(users.get(i));
            } else if (ret.get(i) == 4) {
                showSelfInviteAlert(users.get(i));
            } else if (ret.get(i) == 5) {
                deleteFromRequestsList(users.get(i));
                showAcceptInvitationAlert(users.get(i));
            } else if (ret.get(i) == 0) {
                showSuccessAlert(users.get(i));
            } else
                showFailureAlert(users.get(i));
        }
    }

    public void setData(UserDto user, ChatScreenController chatScreenController) throws RemoteException, NotBoundException {
        this.user = user;
        this.chatScreenController = chatScreenController;
        Registry registry = LocateRegistry.getRegistry(ServerIPAddress.getIp(),ServerIPAddress.getPort());
        serviceFactory = (ServiceFactory) registry.lookup("ServiceFactory");
    }

    private List<Integer> validateUsers(List<String> phoneNumbers) throws RemoteException {
        InvitationService invitationService = serviceFactory.getInvitationService();
        List<InvitationDto> sentInvitations = new ArrayList<>();
        for (String phoneNumber : phoneNumbers) {
            sentInvitations.add(createInvitationDto(phoneNumber));
        }
        return invitationService.sendInvitations(sentInvitations);
    }

    private InvitationDto createInvitationDto(String phoneNumber) {
        InvitationDto invitationDto = new InvitationDto();
        invitationDto.setSenderID(user.getId());
        invitationDto.setSenderPhone(user.getPhoneNumber());
        invitationDto.setSenderImage(user.getPicture());
        invitationDto.setSenderName(user.getDisplayName());
        invitationDto.setReceiverPhone(phoneNumber);
        return invitationDto;
    }

    public void showNotRegisteredAlert(String phoneNumber) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText("The user " + phoneNumber + " is not registered on the system.");

        alert.showAndWait();
    }

    public void showAlreadyConnectedAlert(String phoneNumber) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText("The user " + phoneNumber + " is already in your connections list.");

        alert.showAndWait();
    }

    public void showAlreadyInvitedAlert(String phoneNumber) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText("The user " + phoneNumber + " is already invited.");

        alert.showAndWait();
    }

    public void showSelfInviteAlert(String phoneNumber) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText("You cannot invite yourself.");

        alert.showAndWait();
    }

    public void showSuccessAlert(String phoneNumber) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Invitations sent to " + phoneNumber + " successfully.");

        alert.showAndWait();
    }

    public void showFailureAlert(String phoneNumber) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failure");
        alert.setHeaderText(null);
        alert.setContentText("Failed to send invitation to " + phoneNumber + ".");

        alert.showAndWait();
    }

    public void showAcceptInvitationAlert(String phoneNumber) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(btnInvite.getScene().getWindow());
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Invitation from " + phoneNumber + " accepted successfully.");
        alert.showAndWait();
    }

    public void deleteFromRequestsList(String phone) throws IOException, SQLException, NotBoundException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/invitations-screen.fxml"));
        loader.load();
        InvitationRequestController invitationRequestController = loader.getController();
        invitationRequestController.setData(user.getId(), chatScreenController);
        invitationRequestController.deleteInvitationCard(phone);
    }

}