package iti.jets.app.server.Services;

import iti.jets.app.server.db.UserDao;
import iti.jets.app.server.models.entities.User;
import iti.jets.app.shared.DTOs.UpdateInfoDto;
import iti.jets.app.shared.Interfaces.server.UpdateInfoService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UpdateInfoServiceImpl extends UnicastRemoteObject implements UpdateInfoService {
    public UpdateInfoServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public int updateField(UpdateInfoDto updateInfoDto) throws Exception {
        UserDao userDao = new UserDao();
        if (updateInfoDto.getFieldName().equals("display_name")) {
            User user = userDao.getByUserName(updateInfoDto.getNewValue());
            if (user != null) {
                return 0;
            }
        }
        return userDao.updateField(updateInfoDto.getFieldName(), updateInfoDto.getNewValue(), updateInfoDto.getUserId());
    }

    @Override
    public int updateDOB(UpdateInfoDto updateInfoDto) throws Exception {
        UserDao userDao = new UserDao();
        return userDao.updateUserDob(updateInfoDto.getUserId(), updateInfoDto.getDob());
    }

    @Override
    public int updateImage(int userId, byte[] image) throws Exception {
        UserDao userDao = new UserDao();
        return userDao.updateImage(userId, image);
    }
}
