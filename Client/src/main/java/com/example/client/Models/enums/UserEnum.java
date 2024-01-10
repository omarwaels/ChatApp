package com.example.client.Models.enums;

public enum UserEnum {
    PHONE_NUMBER("phoneNumber"),
    DISPLAY_NAME("displayName"),
    EMAIL("email"),
    IMG_SRC("imgSrc"),
    PASSWORD("password"),
    GENDER("gender"),
    COUNTRY("country"),
    DATE_OF_BIRTH("dateOfBirth"),
    BIO("bio"),
    STATUS("status");

    private String field;

    UserEnum(String field) {
        this.field = field;
    }
}
