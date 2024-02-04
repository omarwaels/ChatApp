package iti.jets.app.server.fxcontrollers;

import iti.jets.app.server.Services.ServerServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;


public class AnnouncementsController implements Initializable {

    ServerServiceImpl serverService;

    {
        try {
            serverService = new ServerServiceImpl();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    public  void  sendAnnouncement () {
        System.out.println("Sent");
        try {
            serverService.sendAnnouncement("hello world");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}
