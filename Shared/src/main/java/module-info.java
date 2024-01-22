module shared {
    requires java.sql;
    requires java.rmi;
    exports iti.jets.app.shared.Interfaces;
    exports iti.jets.app.shared.Interfaces.client;
    exports iti.jets.app.shared.Interfaces.server;
    exports iti.jets.app.shared.DTOs;
    exports iti.jets.app.shared.models.entities;
    exports iti.jets.app.shared.models.enums;

}