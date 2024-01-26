package iti.jets.app.shared.Interfaces.server;

import iti.jets.app.shared.DTOs.ConnectionDto;
import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.DTOs.UserLoginDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Connection extends Remote {
    UserDto connect(ConnectionDto connectionDto) throws RemoteException;
}
