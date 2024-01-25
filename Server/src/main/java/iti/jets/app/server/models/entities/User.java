package iti.jets.app.server.models.entities;

import iti.jets.app.shared.enums.ModeEnum;
import iti.jets.app.shared.enums.StatusEnum;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

public class User implements Serializable {
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

    public User(int id, String phoneNumber, String displayName, String email, byte[] picture, String password, String gender, String country, Date dateOfBirth, String bio) {
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

    public static class UserBuilder {
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


        public UserBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder setStatus(StatusEnum statusEnum) {
            this.statusEnum = statusEnum;
            return this;
        }

        public UserBuilder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPicture(byte[] picture) {
            this.picture = picture;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public UserBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public UserBuilder setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public UserBuilder setBio(String bio) {
            this.bio = bio;
            return this;
        }

        public UserBuilder setMode(ModeEnum userEnum) { // Setter for Mode
            this.userEnum = userEnum;
            return this;
        }

        public User build() {
            return new User(id, phoneNumber, displayName, email, picture, password, gender, country, dateOfBirth, bio);
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
