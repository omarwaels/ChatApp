module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
<<<<<<< Updated upstream

    opens com.example.client to javafx.fxml;
    exports com.example.client;

    exports com.example.client.controllers;

=======
    opens iti.jets.app.client to javafx.fxml;
    exports iti.jets.app.client;
    exports iti.jets.app.client.controllers;
>>>>>>> Stashed changes
}