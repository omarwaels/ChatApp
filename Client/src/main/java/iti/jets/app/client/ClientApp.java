package iti.jets.app.client;

import iti.jets.app.client.utils.GuiUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Platform.setImplicitExit(true);
        Parent root = FXMLLoader.load(getClass().getResource("views/sign-in.fxml"));
        stage.setTitle("Chattily");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("img/applogo.png")));
        GuiUtils.setStageDraggable(stage, root);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
