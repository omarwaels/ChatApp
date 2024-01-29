package iti.jets.app.shared.DTOs;

import iti.jets.app.shared.enums.ModeEnum;
import iti.jets.app.shared.enums.StatusEnum;

import java.io.Serializable;
import java.util.Arrays;

public class FriendInfoDto implements Serializable {
    private String userFriendName;
    private byte[] userFriendPhoto;
    private int userFriendID;
    private ModeEnum userFriendMode;
    private StatusEnum userFriendStatus;

    public FriendInfoDto() {
    }

    public FriendInfoDto(String userFriendName, byte[] userFriendPhoto, int userFriendID, ModeEnum userFriendMode, StatusEnum userFriendStatus) {
        this.userFriendName = userFriendName;
        this.userFriendPhoto = userFriendPhoto;
        this.userFriendID = userFriendID;
        this.userFriendMode = userFriendMode;
        this.userFriendStatus = userFriendStatus;
    }

    public int getUserFriendID() {
        return userFriendID;
    }

    public void setUserFriendID(int userFriendID) {
        this.userFriendID = userFriendID;
    }


    public String getUserFriendName() {
        return userFriendName;
    }

    public void setUserFriendName(String userFriendName) {
        this.userFriendName = userFriendName;
    }

    public byte[] getUserFriendPhoto() {
        return userFriendPhoto;
    }

    public void setUserFriendPhoto(byte[] userFriendPhoto) {
        this.userFriendPhoto = userFriendPhoto;
    }

    public ModeEnum getUserFriendMode() {
        return userFriendMode;
    }

    public void setUserFriendMode(ModeEnum userFriendMode) {
        this.userFriendMode = userFriendMode;
    }

    public StatusEnum getUserFriendStatus() {
        return userFriendStatus;
    }

    public void setUserFriendStatus(StatusEnum userFriendStatus) {
        this.userFriendStatus = userFriendStatus;
    }

    @Override
    public String toString() {
        return "FriendInfoDto{" +
                "userFriendName='" + userFriendName + '\'' +
                ", userFriendPhoto=" + Arrays.toString(userFriendPhoto) +
                ", userFriendID=" + userFriendID +
                ", userFriendMode=" + userFriendMode +
                ", userFriendStatus=" + userFriendStatus +
                '}';
    }
}
