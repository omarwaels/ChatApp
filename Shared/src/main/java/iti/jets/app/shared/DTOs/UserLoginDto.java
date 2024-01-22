package iti.jets.app.shared.DTOs;

import java.io.Serializable;

public class UserLoginDto implements Serializable {
    private String phoneNumber;
    private String password;

    public UserLoginDto() {
    }

    public UserLoginDto(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

}
