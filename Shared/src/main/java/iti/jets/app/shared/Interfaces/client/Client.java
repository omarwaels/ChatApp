package iti.jets.app.shared.Interfaces.client;


import iti.jets.app.shared.DTOs.MessageDto;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote  {
    void recieveMessage (MessageDto messageDto)throws RemoteException;
}
