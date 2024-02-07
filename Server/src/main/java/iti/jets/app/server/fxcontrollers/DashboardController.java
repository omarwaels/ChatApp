package iti.jets.app.server.fxcontrollers;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXToggleButton;
import iti.jets.app.server.Network.ServerConnection;
import iti.jets.app.server.Services.ServerServiceImpl;
import iti.jets.app.shared.Interfaces.server.ServerService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
        loadPage("/views/announcements.fxml");
    }

    @FXML
    void statistics(MouseEvent event) {
        loadPage("/views/statistics.fxml");
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
    public void handleToggleButton() throws RemoteException, InterruptedException {
        if (toggleButton.isSelected()) {
            try {
                ServerConnection.openConnection();
                showNotification("Server is open now ....");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            ServerService serverService = new ServerServiceImpl();
            serverService.closeServer();
            Thread.sleep(200);
            ServerConnection.closeConnection();
        }
    }

    private void showNotification(String message) {
        Notifications notificationBuilder = Notifications.create()
                .title("Server")
                .text(message)
                .styleClass("jfx-notification")
                .graphic(null)
                .hideAfter(Duration.seconds(1))
                .position(javafx.geometry.Pos.BOTTOM_RIGHT)
                .owner(bp.getScene().getWindow());
        notificationBuilder.show();
    }

    public void minimizeWindow(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void closeDashboard(MouseEvent mouseEvent) throws RemoteException, InterruptedException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                " Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Server Dashboard");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            if (toggleButton.isSelected()) {
                toggleButton.setSelected(false);
                handleToggleButton();
            }
            System.exit(0);
        }
    }
}