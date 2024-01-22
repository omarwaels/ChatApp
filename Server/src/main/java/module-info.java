module server {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires java.rmi;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires shared;
    requires mysql.connector.j;
    exports iti.jets.app.server.Implementation;
    requires com.jfoenix;
    exports iti.jets.app.server;
    opens iti.jets.app.server.fxcontrollers to javafx.fxml;
    opens iti.jets.app.server to javafx.fxml;
    exports iti.jets.app.server.fxcontrollers to javafx.fxml;
}