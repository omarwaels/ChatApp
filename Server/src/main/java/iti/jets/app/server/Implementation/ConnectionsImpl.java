package iti.jets.app.server.Implementation;

import iti.jets.app.server.Mappers.UserDtoMapper;
import iti.jets.app.server.db.UserDao;
import iti.jets.app.server.models.entities.User;
import iti.jets.app.shared.DTOs.ConnectionDto;
import iti.jets.app.shared.DTOs.MessageDto;
import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.Interfaces.client.Client;
import iti.jets.app.shared.Interfaces.server.Connection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ConnectionsImpl extends UnicastRemoteObject implements Connection {

    public HashMap<String, Client>  activeConnections = new HashMap<>();
    public ConnectionsImpl() throws RemoteException {
    }

    @Override
    public UserDto connect(ConnectionDto connectionDto) throws RemoteException {
        UserDao userDao = new UserDao();
        User userResult = userDao.getById(connectionDto.getUserLoginDto().getPhoneNumber());
        UserDto userDtoResult =null;
        System.out.println("im here");
        if(userResult != null){

            activeConnections.put(connectionDto.getUserLoginDto().getPhoneNumber(),connectionDto.getClient());
            userDtoResult = UserDtoMapper.UserToUserDto(userResult);
        }

        return userDtoResult;
    }
}
