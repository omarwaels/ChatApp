package iti.jets.app.client.controllers;

import iti.jets.app.shared.DTOs.InvitationDto;
import iti.jets.app.shared.Interfaces.server.InvitationService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
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
import java.util.List;
import java.util.ResourceBundle;

public class InvitationRequestController implements Initializable {

    @FXML
    public ListView<InvitationDto> invitationList;

    public int userId;

    public void setData(int userId) {
        this.userId = userId;
    }

    InvitationService getInvitationService() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(8189);
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
        ObservableList<InvitationDto> data = generateDataObservable();
        invitationList.getItems().addAll(data);
        setRequestsList();
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
                                invitationRequestCardController.setData(item, InvitationRequestController.this);
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

    public void addInvitationCard(InvitationDto invitationDto) {
        invitationList.getItems().add(invitationDto);
    }
}