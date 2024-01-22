package iti.jets.app.client;

import iti.jets.app.client.services.LoginServices;
import iti.jets.app.shared.DTOs.UserLoginDto;
import iti.jets.app.shared.Interfaces.server.Server;
import iti.jets.app.shared.models.entities.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, NotBoundException {




        //stub.connect(new UserLoginDto("01154615235" , "66"));
//        UserLoginDto userLoginDto = new UserLoginDto("01154615235" , "66");
//        try {
//            User user = LoginServices.login(userLoginDto);
//            System.out.println(user);
//        } catch (RemoteException e) {
//            throw new RuntimeException(e);
//        }
        Parent root = FXMLLoader.load(getClass().getResource("views/sign-in.fxml"));
        stage.setTitle("Chatting App");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}