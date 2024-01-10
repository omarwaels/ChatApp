package iti.jets.app.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< Updated upstream:Client/src/main/java/com/example/client/HelloApplication.java
        Parent root = FXMLLoader.load(getClass().getResource("views/chat-screen.fxml"));
        stage.setTitle("Chatting App");
        stage.setScene(new Scene(root));
        stage.show();

=======
       Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/chat-screen.fxml"));
        stage.setTitle("Chatting App");
        stage.setScene(new Scene(root));
        stage.show();
>>>>>>> Stashed changes:Client/src/main/java/iti/jets/app/client/HelloApplication.java
    }

    public static void main(String[] args) {
        launch();
    }
}