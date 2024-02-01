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

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    public BorderPane bp;

    @FXML
    public AnchorPane ap;

    public JFXSnackbar snackbar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ServerConnection.openConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        toggleButton.setSelected(true);
        snackbar = new JFXSnackbar(bp);
    }


    @FXML
    public JFXToggleButton toggleButton;


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

    private void showSnackbar(String message) {
        Platform.runLater(() -> {
            JFXSnackbarLayout snackbarLayout = new JFXSnackbarLayout(message);
            snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarLayout));
            snackbarLayout.getStyleClass().add("jfx-snackbar");
            snackbar.setPrefWidth(180);
            snackbarLayout.setStyle("-fx-text-fill: white;");

            int displayDurationMillis = 400;

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(displayDurationMillis), event -> {
                snackbar.close();
            }));
            timeline.play();
        });
        snackbar.setVisible(true);
    }

    @FXML
    private void handleToggleButton() {
        if (toggleButton.isSelected()) {
            showSnackbar("Server is on");
        } else {
            showSnackbar("Server is off");
        }
    }





}