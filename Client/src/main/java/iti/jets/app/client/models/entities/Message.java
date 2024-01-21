package iti.jets.app.client.models.entities;

public class Message {
    private int type;
    private String message;


    public Message(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
