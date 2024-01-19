package iti.jets.app.server.fxcontrollers;

import java.io.IOException;
import java.util.Objects;


import iti.jets.app.server.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class DashboardController {

    @FXML
    public BorderPane bp;

    @FXML
    public AnchorPane ap;

    @FXML
    void home(MouseEvent event) {
        bp.setCenter(ap);
    }

//    @FXML
//    void serverDashboard(MouseEvent event) {
//    	loadPage("server-dashboard");
//    }

    @FXML
    void announcements(MouseEvent event) {
        loadPage("/iti/jets/app/server/views/announcements.fxml");
    }

    @FXML
    void statistics(MouseEvent event) {
        loadPage("/iti/jets/app/server/views/statistics.fxml");
    }

    private void loadPage(String page) {
        Parent root = null;
        try {
            System.out.println("Page: " + page);
            if (page == null) {
                System.err.println("Page is null.");
                return;
            }
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bp.setCenter(root);
    }

}