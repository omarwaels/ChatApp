package iti.jets.app.shared.Interfaces.server;
import java.rmi.Remote;
import java.rmi.RemoteException;

import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.DTOs.UserLoginDto;

public interface Server extends Remote {
    UserDto connect(UserLoginDto userLoginDto) throws RemoteException;
}
