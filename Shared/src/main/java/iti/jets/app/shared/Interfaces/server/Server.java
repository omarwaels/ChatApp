package iti.jets.app.shared.Interfaces.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.DTOs.UserFriendsDto;
import iti.jets.app.shared.DTOs.UserLoginDto;
import iti.jets.app.shared.Interfaces.client.Client;

public interface Server extends Remote {
    void sendMessage(MessageDto messageDto) throws RemoteException;

    void register(Client c) throws RemoteException;

    void unregister(Client c) throws RemoteException;

}
