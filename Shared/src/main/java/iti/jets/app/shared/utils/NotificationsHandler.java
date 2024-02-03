package iti.jets.app.shared.utils;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationsHandler {

    public static void pushServerAnnouncement(String content) {
        Notifications notifications = Notifications.create()
                .title("          Server Announcements")
                .text(content)
                .graphic(new ImageView(new Image("C:\\Users\\ELGOHARY\\IdeaProjects\\ChatApp\\Shared\\src\\main\\java\\iti\\jets\\app\\shared\\images\\img.png")))
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_CENTER);
        notifications.show();
    }

}
