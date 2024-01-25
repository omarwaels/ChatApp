package iti.jets.app.server.Implementation;

import iti.jets.app.server.Mappers.UserDtoMapper;
import iti.jets.app.server.db.UserDao;
import iti.jets.app.shared.DTOs.UserDto;
import iti.jets.app.shared.DTOs.UserLoginDto;
import iti.jets.app.shared.Interfaces.server.Server;
import iti.jets.app.server.models.entities.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server   {
    public ServerImpl() throws RemoteException {
    }

    @Override
    public UserDto connect(UserLoginDto userLoginDto) throws RemoteException {
        System.out.println("checked");
        UserDao userDao = new UserDao();
        User  userResult = userDao.getById(userLoginDto.getPhoneNumber());
        UserDto userDtoResult = UserDtoMapper.UserToUserDto(userResult);
        return userDtoResult;

    }
}
