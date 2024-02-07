module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires shared;
    requires java.rmi;
    requires static lombok;
    requires MaterialFX;
    requires chatter.bot.api;
    requires jakarta.mail;
    exports iti.jets.app.client.controllers;
    opens iti.jets.app.client.controllers to javafx.fxml;
    exports iti.jets.app.client.start;
    opens iti.jets.app.client.start to javafx.fxml;
}