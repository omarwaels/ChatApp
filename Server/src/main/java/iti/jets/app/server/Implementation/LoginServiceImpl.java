package iti.jets.app.server.Implementation;

import iti.jets.app.shared.DTOs.UserLoginDto;
import iti.jets.app.shared.Interfaces.LoginService;
import iti.jets.app.shared.models.entities.User;
import iti.jets.app.shared.models.enums.UserEnum;
import iti.jets.app.server.db.UserDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServiceImpl implements LoginService {
    @Override
    public User login(UserLoginDto userLoginDto) {
        UserDao userDao = new UserDao();
        ResultSet resultSet = userDao.select(userLoginDto.getPhoneNumber());
        User user = null;
        try {
            if (resultSet.next()) {
                user = extractUser(resultSet);
                // TODO: Validate the password
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return user;
        }
    }

    private User extractUser(ResultSet resultSet) {
        try {
            return new User.UserBuilder().setPhoneNumber(resultSet.getString(UserEnum.PHONE_NUMBER.getField()))
                    .setPassword(resultSet.getString(UserEnum.PASSWORD.getField())).setPicture(resultSet.getBytes(UserEnum.PICTURE.getField()))
                    .setDisplayName(resultSet.getString(UserEnum.DISPLAY_NAME.getField())).setBio(resultSet.getString(UserEnum.BIO.getField()))
                    .setId(resultSet.getInt(UserEnum.ID.getField())).setCountry(resultSet.getString(UserEnum.COUNTRY.getField()))
                    .setEmail(resultSet.getString(UserEnum.EMAIL.getField())).setGender(resultSet.getString(UserEnum.GENDER.getField()))
                    .setDateOfBirth(resultSet.getDate(UserEnum.DATE_OF_BIRTH.getField())).build();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
