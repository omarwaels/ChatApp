package iti.jets.app.shared.Interfaces;

import iti.jets.app.shared.DTOs.UserRegisterDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegisterService extends Remote {
    int register(UserRegisterDto userRegisterDto) throws RemoteException;
}
