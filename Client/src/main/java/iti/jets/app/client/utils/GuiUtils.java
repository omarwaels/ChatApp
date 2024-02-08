package iti.jets.app.client.utils;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public interface GuiUtils {
    public static void setStageDraggable(Stage stage, Parent root){
        final double[] xy = new double[2];
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xy[0] = event.getSceneX();
                xy[1] = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xy[0]);
                stage.setY(event.getScreenY() - xy[1]);
            }
        });
    }

}
