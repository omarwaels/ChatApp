package iti.jets.app.server.fxcontrollers;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXToggleButton;
import iti.jets.app.server.Network.ServerConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    public BorderPane bp;

    @FXML
    public AnchorPane ap;

    @FXML
    public JFXToggleButton toggleButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleButton.setSelected(false);
    }

    @FXML
    void home(MouseEvent event) {
        bp.setCenter(ap);
    }

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


    @FXML
    private void handleToggleButton() {
        if (toggleButton.isSelected()) {
            try {
                ServerConnection.openConnection();
                showNotification("Server is open now ....");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                ServerConnection.closeConnection();
                showNotification("Server Closed Successfully ....");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void showNotification(String message) {
        Notifications notificationBuilder = Notifications.create()
                .title("Server")
                .text(message)
                .graphic(null)
                .hideAfter(Duration.seconds(3))
                .position(javafx.geometry.Pos.BOTTOM_RIGHT)
                .owner(bp.getScene().getWindow());
        notificationBuilder.show();
    }
}