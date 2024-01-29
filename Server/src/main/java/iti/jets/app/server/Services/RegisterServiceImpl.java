package iti.jets.app.server.Services;

import iti.jets.app.server.models.entities.User;
import iti.jets.app.shared.DTOs.UserRegisterDto;
import iti.jets.app.shared.Interfaces.RegisterService;
import iti.jets.app.server.Mappers.RegisterDtoMapper;
import iti.jets.app.server.db.UserDao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RegisterServiceImpl extends UnicastRemoteObject implements RegisterService {
    public RegisterServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public int register(UserRegisterDto userRegisterDto) {
        UserDao userDao = new UserDao();
        User user = userDao.getById(userRegisterDto.getPhoneNumber());
        if (user != null) {
            return 0;
        }
        user = userDao.getByEmail(userRegisterDto.getEmail());
        if (user != null) {
            return 0;
        }
        return userDao.insert(RegisterDtoMapper.registerDtoToUser(userRegisterDto));
    }
}
