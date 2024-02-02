package iti.jets.app.client.controllers;

import iti.jets.app.shared.DTOs.UserInvitationDto;
import iti.jets.app.shared.Interfaces.server.InvitationService;
import iti.jets.app.shared.Interfaces.server.LoginService;
import iti.jets.app.shared.Interfaces.server.ServiceFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

public class InvitationRequestController implements Initializable {
    InvitationService getInvitationService() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(8189);
        return ((ServiceFactory) registry.lookup("ServiceFactory")).getInvitationService();
    }
    @FXML
    public ListView<UserInvitationDto> invitationList;




    public ObservableList<UserInvitationDto> generateDataObservable() {
        ObservableList<UserInvitationDto> observableList = FXCollections.observableArrayList();
        try {
            InvitationService invitationService = getInvitationService();
            List<UserInvitationDto> userRequests = invitationService.getUserRequests(1);
            observableList.addAll(userRequests);
        } catch (NotBoundException | RemoteException e) {
            e.printStackTrace();
        }

        return observableList;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<UserInvitationDto> data = generateDataObservable();
        invitationList.getItems().addAll(data);

        setRequestsList();
       // invitationList.setPrefHeight(dummyData.size() * 40 + 2);
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
                                Image userImage = new Image(new ByteArrayInputStream(item.getImage()), 72, 80, false, true);
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/iti/jets/app/client/views/invitation-card.fxml"));
                             Parent root;
                                try {
                                    root=loader.load();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                InvitationRequestCardController invitationRequestCardController = loader.getController();
                                invitationRequestCardController.setData(item.getUserName(),item.getPhoneNumber(),userImage,item.getUserId(),item);
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
}