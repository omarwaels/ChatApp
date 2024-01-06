package com.example.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("views/Login.fxml"));
        stage.setTitle("Chatting App");
        stage.setScene(new Scene(root));
        stage.show();

        /*FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Client!");
        stage.setScene(scene);
        stage.show();*/
    }
    public static void main(String[] args) {
        launch();
    }
}