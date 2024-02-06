package iti.jets.app.client.controllers;

import iti.jets.app.shared.DTOs.MessageDto;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.ResourceBundle;

public class FileSentController implements Initializable {
    @FXML
    public Circle img;
    @FXML
    public Text txt;
    @FXML
    public TextFlow txtFlow;
    @FXML
    public StackPane fileExploreContainer;

    private File fileInfo;

    private int fileChatId;

    public void setData(MessageDto msg, Image userImg, File fileInfo) throws IOException {
        //Image image = new Image(getClass().getResourceAsStream(user.getImgSrc()));
        this.fileInfo = fileInfo;
        this.fileChatId = msg.getChatId();
        txt.setText(msg.getMessageContent());
        txtFlow.setTextAlignment(TextAlignment.LEFT);
        if (userImg != null) {
            img.setFill(new ImagePattern(userImg));
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void showFileInExplorer() {
        try {
            Path filePath = fileInfo.toPath();
            if (Files.exists(filePath)) {
                String absolutePath = filePath.toAbsolutePath().toString();
                Desktop.getDesktop().open(new File(absolutePath));
            } else {
                showFileNotAvailableAlert();
            }
        } catch (IOException e) {

        }
    }

    public void mouseEnterEffect() {
        txtFlow.setStyle("-fx-background-color: rgba(153, 172, 255, 1);");


    }

    public void mouseExitEffect() {

        txtFlow.setStyle("-fx-background-color: rgba(15, 125, 242, 1);");
    }

    public void openFileInExplorer() {
        try {
            Path filePath = fileInfo.toPath();
            if (Files.exists(filePath)) {
                String parentPath = filePath.toAbsolutePath().getParent().toString();
                Desktop.getDesktop().open(new File(parentPath));
            } else {
                showFileNotAvailableAlert();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception
        }

    }

    private void showFileNotAvailableAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("File Not Available");
        alert.setHeaderText(null);
        alert.setContentText("The file is no longer available.");

        // Create a timeline to close the alert after 2 seconds
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                event -> alert.close()
        ));
        timeline.play();

        alert.showAndWait();
    }


}
