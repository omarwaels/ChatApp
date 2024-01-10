package com.example.client.Models.enums;

public enum StatusEnum {
    ONLINE("Online"),
    OFFLINE("Offline");

    private String status;

    StatusEnum(String status) {
        this.status = status;
    }
}
