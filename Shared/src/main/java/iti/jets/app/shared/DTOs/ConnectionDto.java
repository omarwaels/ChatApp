package iti.jets.app.shared.DTOs;

import iti.jets.app.shared.Interfaces.client.Client;

import java.io.Serializable;

public class ConnectionDto implements Serializable {

    private UserLoginDto userLoginDto;
//    private Client client;

    public UserLoginDto getUserLoginDto() {
        return userLoginDto;
    }

    public void setUserLoginDto(UserLoginDto userLoginDto) {
        this.userLoginDto = userLoginDto;
    }

//    public Client getClient() {
//        return client;
//    }
//
//    public void setClient(Client client) {
//        this.client = client;
//    }

    public ConnectionDto(UserLoginDto userLoginDto) {
        this.userLoginDto = userLoginDto;
//        this.client = client;
    }
}
