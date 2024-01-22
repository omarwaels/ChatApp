package iti.jets.app.shared.Interfaces.server;
import java.rmi.Remote;
import java.rmi.RemoteException;

import iti.jets.app.shared.DTOs.UserLoginDto;
import iti.jets.app.shared.models.entities.User;
public interface Server extends Remote {
    User connect(UserLoginDto userLoginDto) throws RemoteException;
}
