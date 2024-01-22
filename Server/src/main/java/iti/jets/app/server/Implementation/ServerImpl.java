package iti.jets.app.server.Implementation;

import iti.jets.app.server.db.UserDao;
import iti.jets.app.shared.DTOs.UserLoginDto;
import iti.jets.app.shared.Interfaces.server.Server;
import iti.jets.app.shared.models.entities.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server   {
    public ServerImpl() throws RemoteException {
    }

    @Override
    public User connect(UserLoginDto userLoginDto) throws RemoteException {
        System.out.println("checked");
        UserDao userDao = new UserDao();

        return userDao.getById(userLoginDto.getPhoneNumber());

    }
}
