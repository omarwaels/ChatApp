package iti.jets.app.server.Implementation;

import iti.jets.app.shared.DTOs.UserLoginDto;
import iti.jets.app.shared.Interfaces.LoginService;
import iti.jets.app.shared.models.entities.User;
import iti.jets.app.server.db.UserDao;

public class LoginServiceImpl implements LoginService {
    @Override
    public User login(UserLoginDto userLoginDto) {
        UserDao userDao = new UserDao();
        return userDao.getById(userLoginDto.getPhoneNumber());
    }
}
