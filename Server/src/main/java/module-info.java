module server {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires shared;
    requires mysql.connector.java;

    opens iti.jets.app.server to javafx.fxml;
    exports iti.jets.app.server.Implementation;
    exports iti.jets.app.server;
}