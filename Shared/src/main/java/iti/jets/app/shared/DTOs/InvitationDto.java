package iti.jets.app.shared.DTOs;


import java.io.Serializable;

public class InvitationDto implements Serializable {
    private int invitationID;
    private int receiverID;
    private int senderID;

    private String senderName;
    private String senderPhone;

    private byte[] senderImage;

    public InvitationDto() {
    }

    public InvitationDto(int invitationID, int senderID, int receiverID, String senderName, String senderPhone, byte[] senderImage) {
        this.invitationID = invitationID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.senderName = senderName;
        this.senderPhone = senderPhone;
        this.senderImage = senderImage;
    }

    public int getInvitationID() {
        return invitationID;
    }

    public void setInvitationID(int invitationID) {
        this.invitationID = invitationID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public byte[] getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(byte[] senderImage) {
        this.senderImage = senderImage;
    }
}