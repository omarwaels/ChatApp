package iti.jets.app.shared.enums;

public enum UserEnum {
    ID(1),
    PHONE_NUMBER(2),
    DISPLAY_NAME(3),
    EMAIL(4),
    PICTURE(5),
    PASSWORD(6),
    GENDER(7),
    COUNTRY(8),
    DATE_OF_BIRTH(9),
    BIO(10),
    STATUS(11);
    private int field;

    UserEnum(int field) {
        this.field = field;
    }

    public int getField() {
        return field;
    }
}
