package iti.jets.app.shared.Interfaces.server;

import iti.jets.app.shared.DTOs.LoginResultDto;
import iti.jets.app.shared.DTOs.UserLoginDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginService extends Remote {
    LoginResultDto login(UserLoginDto userLoginDto) throws RemoteException;

    boolean userExists(String phoneNumber) throws RemoteException;

    int logOut(String phoneNumber) throws RemoteException;
}
