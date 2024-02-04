module shared {
    requires java.sql;
    requires java.rmi;
    requires static lombok;
    exports iti.jets.app.shared.Interfaces.client;
    exports iti.jets.app.shared.Interfaces.server;
    exports iti.jets.app.shared.DTOs;
    exports iti.jets.app.shared.enums;
    exports iti.jets.app.shared.utils;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires org.controlsfx.controls;
}