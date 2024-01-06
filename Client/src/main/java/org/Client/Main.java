package org.Client;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.IOException;

/**
 *
 * @author Sigma
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Button myButton = new Button("Click me!");
        Label lb = new Label("Client");
        // Set up a layout pane (StackPane in this case)

        StackPane root = new StackPane();
        root.getChildren().add(lb);
        Scene scene = new Scene(root, 300, 250);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}