package iti.jets.app.server;

import iti.jets.app.server.Services.ConnectionService;
import iti.jets.app.shared.DTOs.*;
import iti.jets.app.shared.Interfaces.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        ConnectionService connectionService = new ConnectionService();
        ChatScreenDto chatScreenDto = connectionService.connect(new ConnectionDto(new UserLoginDto("01154615235", "omar"), new Client() {
            @Override
            public void recieveMessage(MessageDto messageDto) throws RemoteException {
                System.out.println("asd");
            }
        }));
        System.out.println(chatScreenDto);
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("views/server-dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/iti/jets/app/server/style/style.css").toExternalForm());

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.setMaxHeight(600);
        stage.setMaxWidth(1000);


        System.out.println(Naming.list("rmi://localhost:8090/stub").length);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}