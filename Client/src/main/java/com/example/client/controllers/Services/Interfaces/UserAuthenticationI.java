package com.example.client.controllers.Services.Interfaces;

import com.example.client.Models.User;

public interface UserAuthenticationI {
    public boolean signIn(String phoneNumber, String password);
}
