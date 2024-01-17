module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires shared;
    requires server;
    opens iti.jets.app.client to javafx.fxml;
    exports iti.jets.app.client;
    exports iti.jets.app.client.controllers;
    exports iti.jets.app.models.entities;
    exports iti.jets.app.models.enums;
}