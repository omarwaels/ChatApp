package iti.jets.app.shared.DTOs;

import iti.jets.app.shared.enums.ModeEnum;
import iti.jets.app.shared.enums.StatusEnum;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

public class UserDto implements Serializable {
    private final int id;
    private final String phoneNumber;
    private String displayName;
    private String email;
    private byte[] picture;
    private String password;
    private String gender;
    private String country;
    private Date dateOfBirth;
    private String bio;
    private ModeEnum userEnum;
    private StatusEnum statusEnum;

    public UserDto(int id, String phoneNumber, String displayName, String email, byte[] picture, String password, String gender, String country, Date dateOfBirth, String bio) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.displayName = displayName;
        this.email = email;
        this.picture = picture;
        this.password = password;
        this.gender = gender;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    public static class UserDtoBuilder {
        private int id;
        private String phoneNumber;
        private String displayName;
        private String email;
        private byte[] picture;
        private String password;
        private String gender;
        private String country;
        private Date dateOfBirth;
        private String bio;
        private ModeEnum userEnum;
        private StatusEnum statusEnum;


        public UserDtoBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserDtoBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder setStatus(StatusEnum statusEnum) {
            this.statusEnum = statusEnum;
            return this;
        }

        public UserDtoBuilder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public UserDtoBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserDtoBuilder setPicture(byte[] picture) {
            this.picture = picture;
            return this;
        }

        public UserDtoBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserDtoBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public UserDtoBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public UserDtoBuilder setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public UserDtoBuilder setBio(String bio) {
            this.bio = bio;
            return this;
        }

        public UserDtoBuilder setMode(ModeEnum userEnum) { // Setter for Mode
            this.userEnum = userEnum;
            return this;
        }

        public UserDto build() {
            return new UserDto(id, phoneNumber, displayName, email, picture, password, gender, country, dateOfBirth, bio);
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getPicture() {
        return picture;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public StatusEnum getStatus() { // Getter for Status
        return statusEnum;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ModeEnum getMode() { // Getter for Mode
        return userEnum;
    }

    public void setMode(ModeEnum userEnum) { // Setter for Mode
        this.userEnum = userEnum;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", picture=" + Arrays.toString(picture) +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", country='" + country + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", bio='" + bio + '\'' +
                ", userEnum=" + userEnum +
                ", statusEnum=" + statusEnum +
                '}';
    }
}
