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
    requires com.jfoenix;
    requires com.zaxxer.hikari;
    opens iti.jets.app.server.fxcontrollers to javafx.fxml;
    exports iti.jets.app.server.fxcontrollers to javafx.fxml;
    exports iti.jets.app.server.Services;
    exports iti.jets.app.server.start;
    opens iti.jets.app.server.start to javafx.fxml;
}