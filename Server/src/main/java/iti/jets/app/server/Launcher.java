package iti.jets.app.server;

<<<<<<< HEAD
import iti.jets.app.server.db.UserDao;
import iti.jets.app.server.models.entities.User;
=======
import iti.jets.app.server.Services.ConnectionService;
import iti.jets.app.shared.DTOs.*;
import iti.jets.app.shared.Interfaces.client.Client;
>>>>>>> 027b5f55f44c0b031cdeb961a7976d0e461f0ed8
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

<<<<<<< HEAD

=======
        ConnectionService connectionService = new ConnectionService();
        ChatScreenDto chatScreenDto =connectionService.connect( new ConnectionDto(new UserLoginDto("01154615235", "omar"), new Client() {
            @Override
            public void recieveMessage(MessageDto messageDto) throws RemoteException {
                System.out.println("asd");
            }
        }));
        System.out.println(chatScreenDto);
>>>>>>> 027b5f55f44c0b031cdeb961a7976d0e461f0ed8
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("views/server-dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/iti/jets/app/server/style/style.css").toExternalForm());

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.setMaxHeight(600);
        stage.setMaxWidth(1000);
<<<<<<< HEAD


        System.out.println(Naming.list("rmi://localhost:8090/stub").length);

=======
        //System.out.println( Naming.list("rmi://localhost:8090/stub" ).length);
>>>>>>> 027b5f55f44c0b031cdeb961a7976d0e461f0ed8
        stage.show();
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User user = userDao.getById("01095944926");
        if (user == null) {
            System.out.println("null");
        } else {
            System.out.println("not null");
        }
        launch();
    }
}