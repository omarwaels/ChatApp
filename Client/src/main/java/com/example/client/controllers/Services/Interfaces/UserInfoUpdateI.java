package com.example.client.controllers.Services.Interfaces;

public interface UserInfoUpdateI {
    public boolean updateUserName(String phoneNumber, String userName);

    public boolean updatePassword(String phoneNumber, String password);

    public boolean updatePicture(String phoneNumber, byte[] picture);

    public boolean updateEmail(String phoneNumber, String email);

    public boolean updateBio(String phoneNumber, String bio);

    public boolean updateGender(String phoneNumber, String gender);

    public boolean updateCountry(String phoneNumber, String country);

    public boolean updateDate(String phoneNumber, String date);
}
