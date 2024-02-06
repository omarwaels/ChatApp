package iti.jets.app.client;

import iti.jets.app.client.utils.ServerIPAddress;
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
        if (args.length > 0 && args[0] != null ) {
            ServerIPAddress.setIp(args[0]);
            ServerIPAddress.setPort(Integer.parseInt(args[1]));
        }
        launch();
    }
}
