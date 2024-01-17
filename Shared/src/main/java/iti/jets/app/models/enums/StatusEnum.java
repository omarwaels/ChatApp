package iti.jets.app.models.enums;

public enum StatusEnum {
    ONLINE("Online"),
    OFFLINE("Offline");

    private String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
