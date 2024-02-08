package iti.jets.app.client.start;

import iti.jets.app.client.utils.ServerIPAddress;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ClientApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Platform.setImplicitExit(true);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/sign-in.fxml")));
        stage.setTitle("Chatting App");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0] != null ) {
            ServerIPAddress.setIp(args[0]);
            System.out.println("ServerIPAddress : " + args[0]);
            ServerIPAddress.setPort(Integer.parseInt(args[1]));
            System.out.println("ServerPort : "+Integer.parseInt(args[1]));
        }
        launch();
    }
}
