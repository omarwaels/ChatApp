package iti.jets.app.server.fxcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AnnouncementsController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    public  void  sendAnnouncement () {
        System.out.println("Sent");
    }

}
