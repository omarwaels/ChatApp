module iti.jets.app.server {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens iti.jets.app.server to javafx.fxml;
    opens iti.jets.app.server.fxcontrollers to javafx.fxml;
    exports iti.jets.app.server;
    exports iti.jets.app.server.fxcontrollers to javafx.fxml;


}