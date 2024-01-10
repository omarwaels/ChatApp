package iti.jets.app.client.models.entities;

public class Invitation {
    private int senderID;
    private int receiverID;

    public Invitation(int senderID, int receiverID) {
        this.senderID = senderID;
        this.receiverID = receiverID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getReceiverID() {
        return receiverID;
    }
}
