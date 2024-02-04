package iti.jets.app.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

//        String name = "hello";
//        Path path = Paths.get("Downloads/chat/omar.txt");
//        Files.createDirectories(path.getParent());
//        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//        fileChannel.write(ByteBuffer.wrap(name.getBytes()));
//        fileChannel.close();

        //Download Path



        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("views/server-dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/iti/jets/app/server/style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(1000);
        stage.setMaxHeight(600);
        stage.setMaxWidth(1000);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}