package iti.jets.app.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Platform.setImplicitExit(true);
        Parent root = FXMLLoader.load(getClass().getResource("views/sign-in.fxml"));
        stage.setTitle("Chatting App");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}
