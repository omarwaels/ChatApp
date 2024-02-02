package iti.jets.app.shared.DTOs;

import lombok.*;

import java.io.Serializable;
import java.util.Arrays;


public class UserInvitationDto implements Serializable {
    private int invitationId;
    private String userName;
    private String phoneNumber;
    private int userId;
    private byte[] image;

    public UserInvitationDto(int invitationId, String userName, String phoneNumber, int userId, byte[] image) {
        this.invitationId = invitationId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.image = image;
    }

    public int getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(int invitationId) {
        this.invitationId = invitationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "UserInvitationDto{" +
                "invitationId=" + invitationId +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userId=" + userId +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
