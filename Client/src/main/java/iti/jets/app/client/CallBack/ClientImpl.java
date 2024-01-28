package iti.jets.app.client.CallBack;

import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.Interfaces.client.Client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client   {
    public ClientImpl() throws RemoteException {
    }

    @Override
    public void recieveMessage(MessageDto messageDto) {
        System.out.println(messageDto);
    }


}
