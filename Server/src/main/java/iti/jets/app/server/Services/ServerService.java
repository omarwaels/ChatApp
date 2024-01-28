package iti.jets.app.server.Services;

import iti.jets.app.server.Mappers.UserArrayMapper;
import iti.jets.app.server.db.ConnectionDao;
import iti.jets.app.server.db.UserDao;
import iti.jets.app.server.models.entities.User;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.DTOs.UserFriendsDto;
import iti.jets.app.shared.Interfaces.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerService extends UnicastRemoteObject implements Server   {
    public ServerService() throws RemoteException {
    }


    @Override
    public MessageDto sendMessage(MessageDto messageDto) throws RemoteException {
        return null;
    }



}
