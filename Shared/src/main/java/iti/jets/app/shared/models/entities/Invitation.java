package iti.jets.app.shared.models.entities;

public class Invitation {
    private int invitationID;
    private int senderID;
    private int receiverID;

    public Invitation() {
    }
    public Invitation(int senderID, int receiverID) {
        this.senderID = senderID;
        this.receiverID = receiverID;
    }

    public int getInvitationID() {
        return invitationID;
    }

    public void setInvitationID(int invitationID) {
        this.invitationID = invitationID;
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
