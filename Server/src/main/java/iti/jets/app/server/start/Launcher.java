package iti.jets.app.server.start;

import iti.jets.app.server.fxcontrollers.DashboardController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    private static double x = 0;
    private static double y = 0;

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {


        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("/views/server-dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        DashboardController controller = fxmlLoader.getController();
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x = event.getSceneX();
                y = event.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            }
        });
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