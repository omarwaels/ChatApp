package iti.jets.app.shared.enums;

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
