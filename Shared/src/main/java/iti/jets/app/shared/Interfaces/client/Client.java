package iti.jets.app.shared.Interfaces.client;


import iti.jets.app.shared.DTOs.MessageDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote  {
    void receiveMessage(MessageDto messageDto)throws RemoteException;

    int getID() throws RemoteException;

    void updateFriendStatus(int friendId, boolean online) throws RemoteException;
}
