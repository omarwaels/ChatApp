module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    opens iti.jets.app.client to javafx.fxml;
    exports iti.jets.app.client;
    exports iti.jets.app.client.controllers;
}