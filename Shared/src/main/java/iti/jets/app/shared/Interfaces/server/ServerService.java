package iti.jets.app.shared.Interfaces.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.Interfaces.client.Client;

public interface ServerService extends Remote {
    void sendMessage(MessageDto messageDto) throws RemoteException;

    void register(Client c) throws RemoteException;

    void unregister(Client c) throws RemoteException;

    void updateStatus(ArrayList<Integer> friendsIds, int userId, boolean status) throws RemoteException;

}
