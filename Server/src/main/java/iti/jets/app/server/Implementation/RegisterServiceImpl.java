package iti.jets.app.server.Implementation;

import iti.jets.app.shared.DTOs.UserRegisterDto;
import iti.jets.app.shared.Interfaces.RegisterService;
import iti.jets.app.server.Mappers.RegisterDtoMapper;
import iti.jets.app.server.db.UserDao;

public class RegisterServiceImpl implements RegisterService {
        @Override
        public int register(UserRegisterDto userRegisterDto) {
            UserDao userDao = new UserDao();
            // TODO: validate the information
            return userDao.insert(RegisterDtoMapper.registerDtoToUser(userRegisterDto));
        }
}
