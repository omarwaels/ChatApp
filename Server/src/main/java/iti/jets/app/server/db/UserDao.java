package iti.jets.app.server.db;

import iti.jets.app.server.models.entities.User;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.enums.ModeEnum;
import iti.jets.app.shared.utils.*;
import iti.jets.app.shared.enums.StatusEnum;
import iti.jets.app.shared.enums.UserEnum;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class UserDao implements Dao<User, String> {
    private DataSource dataSource;

    public UserDao() {
        dataSource = DataSourceFactory.getMySQLDataSource();
    }

    private User extractUser(ResultSet resultSet) throws SQLException {
        return new User.UserBuilder().setPhoneNumber(resultSet.getString(UserEnum.PHONE_NUMBER.getField()))
                .setPassword(resultSet.getString(UserEnum.PASSWORD.getField())).setPicture(resultSet.getBytes(UserEnum.PICTURE.getField()))
                .setDisplayName(resultSet.getString(UserEnum.DISPLAY_NAME.getField())).setBio(resultSet.getString(UserEnum.BIO.getField()))
                .setId(resultSet.getInt(UserEnum.ID.getField())).setCountry(resultSet.getString(UserEnum.COUNTRY.getField()))
                .setEmail(resultSet.getString(UserEnum.EMAIL.getField())).setGender(resultSet.getString(UserEnum.GENDER.getField()))
                .setDateOfBirth(resultSet.getDate(UserEnum.DATE_OF_BIRTH.getField()))
                .setStatus(StatusEnum.valueOf(resultSet.getString(UserEnum.STATUS.getField()).toUpperCase()))
                .setMode(ModeEnum.valueOf(resultSet.getString(UserEnum.MODE.getField()).toUpperCase()))
                .build();
    }

    @Override
    public User getById(String phoneNumber) {
        ResultSet resultSet = null;
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE phone_number = ?")) {
            preparedStatement.setString(1, phoneNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return extractUser(resultSet);
            } else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public User getByEmail(String email) {
        ResultSet resultSet = null;

        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return extractUser(resultSet);
            } else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public User getByUserName(String userName) {
        ResultSet resultSet = null;

        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE display_name = ?")) {
            preparedStatement.setString(1, userName);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return extractUser(resultSet);
            } else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public User getByIntegerId(int Id) {
        ResultSet resultSet = null;

        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE user_id = ?")) {
            preparedStatement.setInt(1, Id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return extractUser(resultSet);
            } else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int insert(User user) {
        PreparedStatement preparedStatement = null;
        java.sql.Connection conn = null;
        int result = 0;
        String query = "INSERT INTO Users (phone_number, display_name, email, picture, password, gender, country, date_of_birth, bio) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(query);
            preparedStatement = dataSource.getConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getPhoneNumber());
            preparedStatement.setString(2, user.getDisplayName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setBytes(4, user.getPicture());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getGender());
            preparedStatement.setString(7, user.getCountry());
            preparedStatement.setDate(8, user.getDateOfBirth());
            preparedStatement.setString(9, user.getBio());
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return result;
        }

    }

    @Override
    public int update(User user) {
        String query = "UPDATE users " +
                "SET display_name = ?" +
                ", email = ?" +
                ", picture = ?" +
                ", password = ?" +
                ", gender = ?" +
                ", country = ?" +
                ", date_of_birth = ?" +
                ", bio = ?" +
                "WHERE phone_number = ?;";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, user.getDisplayName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setBytes(3, user.getPicture());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getGender());
            preparedStatement.setString(6, user.getCountry());
            preparedStatement.setDate(7, user.getDateOfBirth());
            preparedStatement.setString(8, user.getBio());
            preparedStatement.setString(9, user.getPhoneNumber());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(String phoneNumber) {
        String query = "DELETE FROM users WHERE phone_number = ?";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, phoneNumber);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateStatus(String phoneNumber, String status) {
        String query = "UPDATE users " +
                "SET status = ?" +
                "WHERE phone_number = ?;";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, phoneNumber);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateMode(String phoneNumber, String mode) {
        String query = "UPDATE users " +
                "SET mode = ?" +
                "WHERE phone_number = ?;";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, mode);
            preparedStatement.setString(2, phoneNumber);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateUserDob(int userID, Date date) {
        String query = "UPDATE users " +
                "SET date_of_birth = ?" +
                "WHERE user_id = ?;";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setDate(1, Utils.getSqlDate(date));
            preparedStatement.setInt(2, userID);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateImage(int userId, byte[] newImage) {
        String query = "UPDATE users SET picture = ? WHERE user_id = ?";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setBytes(1, newImage);
            preparedStatement.setInt(2, userId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateField(String fieldName, String value, int userId) throws SQLException {
        String query = "UPDATE users SET " + fieldName + " = ? WHERE user_id = ?";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, userId);
            return preparedStatement.executeUpdate();
        }
    }
}
