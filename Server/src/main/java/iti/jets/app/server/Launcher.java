package iti.jets.app.server;

import iti.jets.app.server.Network.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.Naming;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("views/server-dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/iti/jets/app/server/style/style.css").toExternalForm());

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.setMaxHeight(600);
        stage.setMaxWidth(1000);


            System.out.println( Naming.list("rmi://localhost:8090/stub" ).length);



        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}