package iti.jets.app.shared.Interfaces.server;
import java.rmi.Remote;
import java.rmi.RemoteException;

import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.DTOs.UserFriendsDto;
import iti.jets.app.shared.DTOs.UserLoginDto;

public interface Server extends Remote {
    MessageDto sendMessage(MessageDto messageDto) throws RemoteException;




}
