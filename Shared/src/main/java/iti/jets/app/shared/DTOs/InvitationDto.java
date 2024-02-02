package iti.jets.app.shared.DTOs;


import java.io.Serializable;

public class InvitationDto implements Serializable {
    private int invitationID;
    private int senderID;
    private int receiverID;

    public InvitationDto() {
    }

    public InvitationDto(int invitationID, int senderID, int receiverID) {
        this.invitationID = invitationID;
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

    @Override
    public String toString() {
        return "InvitationDto{" +
                "invitationID=" + invitationID +
                ", senderID=" + senderID +
                ", receiverID=" + receiverID +
                '}';
    }
}