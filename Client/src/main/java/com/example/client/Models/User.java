package com.example.client.Models;

import java.sql.Date;

public class User {
    private String phoneNumber;
    private String displayName;
    private String email;
    private byte[] picture;
    private String password;
    private String confirmationPassword;
    private String gender;
    private String country;
    private Date dateOfBirth;
    private String bio;

    public User(String phoneNumber, String displayName, String email, byte[] picture, String password, String confirmationPassword, String gender, String country, Date dateOfBirth, String bio) {
        this.phoneNumber = phoneNumber;
        this.displayName = displayName;
        this.email = email;
        this.picture = picture;
        this.password = password;
        this.confirmationPassword = confirmationPassword;
        this.gender = gender;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }


    public static class UserBuilder {
        private String phoneNumber;
        private String displayName;
        private String email;
        private byte[] picture;
        private String password;
        private String confirmationPassword;
        private String gender;
        private String country;
        private Date dateOfBirth;
        private String bio;

        public UserBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
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

        public UserBuilder setConfirmationPassword(String confirmationPassword) {
            this.confirmationPassword = confirmationPassword;
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

        public User createUser() {
            return new User(phoneNumber, displayName, email, picture, password, confirmationPassword, gender, country, dateOfBirth, bio);
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

    public String getPassword() {
        return password;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
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
