package com.example.client.Models;

import java.util.Date;

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

    private User(UserBuilder builder) {
        this.phoneNumber = builder.phoneNumber;
        this.displayName = builder.displayName;
        this.email = builder.email;
        this.picture = builder.picture;
        this.password = builder.password;
        this.confirmationPassword = builder.confirmationPassword;
        this.gender = builder.gender;
        this.country = builder.country;
        this.dateOfBirth = builder.dateOfBirth;
        this.bio = builder.bio;
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

        public UserBuilder(String phoneNumber, String displayName, String email) {
            this.phoneNumber = phoneNumber;
            this.displayName = displayName;
            this.email = email;
        }

        public UserBuilder picture(byte[] picture) {
            this.picture = picture;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder confirmationPassword(String confirmationPassword) {
            this.confirmationPassword = confirmationPassword;
            return this;
        }

        public UserBuilder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public UserBuilder country(String country) {
            this.country = country;
            return this;
        }

        public UserBuilder dateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public UserBuilder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    // Getters for each field

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
}
