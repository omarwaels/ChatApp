package iti.jets.app.client.models.enums;

public enum StatusEnum {
    ONLINE("Online"),
    OFFLINE("Offline");

    private String status;

    StatusEnum(String status) {
        this.status = status;
    }
}
