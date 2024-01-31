package iti.jets.app.server;

import iti.jets.app.server.db.UserDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("views/server-dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/iti/jets/app/server/style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.setMaxHeight(600);
        stage.setMaxWidth(1000);
        stage.show();
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();


        String imagePath = "C:\\Users\\ELGOHARY\\IdeaProjects\\ChatApp\\Server\\src\\main\\resources\\iti\\jets\\app\\server\\images\\user.png";

        try {
            byte[] newImageData = Files.readAllBytes(Paths.get(imagePath));

            int rowsUpdated = userDao.updateImage("1234567890", newImageData);

            if (rowsUpdated > 0) {
                System.out.println("Done");
            } else {
                System.out.println("Failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch();
    }
}