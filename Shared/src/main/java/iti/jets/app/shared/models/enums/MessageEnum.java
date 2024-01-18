package iti.jets.app.shared.models.enums;

public enum MessageEnum {


    sender_id(1),
    chat_id(2),
    contains_file(3),
    message_content(4),
    sent_at(5);
    private int field;

    MessageEnum(int i) {
        this.field = 0;
    }
    public int getField() {
        return field;
    }
}
