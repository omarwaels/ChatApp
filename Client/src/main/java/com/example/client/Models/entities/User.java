package com.example.client.Models.entities;

import com.example.client.Models.enums.StatusEnum;

import java.sql.Date;

public class User {
    private final String phoneNumber;
    private String displayName;
    private String email;
    private String imgSrc;
    private String password;
    private String gender;
    private String country;
    private Date dateOfBirth;
    private String bio;

    private StatusEnum statusEnum;

    public User(String phoneNumber, String displayName, String email, String picture, String password, String gender, String country, Date dateOfBirth, String bio) {
        this.phoneNumber = phoneNumber;
        this.displayName = displayName;
        this.email = email;
        this.imgSrc = picture;
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
        private String imgSrc;
        private String password;
        private String gender;
        private String country;
        private Date dateOfBirth;
        private String bio;

        //TODO: modify the status
        private StatusEnum status;


        public UserBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder setStatus(StatusEnum statusEnum) {
            this.status = statusEnum;
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

        public UserBuilder setImgSrc(String picture) {
            this.imgSrc = picture;
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

        public User build() {
            return new User(phoneNumber, displayName, email, imgSrc, password, gender, country, dateOfBirth, bio);
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

    public String getImgSrc() {
        return imgSrc;
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

    public void setImgSrc(String picture) {
        this.imgSrc = picture;
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
}
