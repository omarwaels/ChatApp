package iti.jets.app.server.fxcontrollers;

import iti.jets.app.server.Services.ServerServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;


public class AnnouncementsController implements Initializable {

    ServerServiceImpl serverService;

    @FXML
    public TextArea ann1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            serverService = new ServerServiceImpl();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void sendAnnouncement() throws RemoteException {
        serverService.sendAnnouncement(ann1.getText());
    }
}
