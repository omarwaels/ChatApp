package com.example.client.Models;

import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class UserDao {

    private UserDao userDao;

    private UserDao() {
    }

    public UserDao getInstance() {
        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String query = "SELECT * FROM user;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String phoneNumber = rs.getString(2);
                String displayName = rs.getString(3);
                String email = rs.getString(4);
                // String picture = rs.getString(5);
                String password = rs.getString(6);
                String gender = rs.getString(7);
                String country = rs.getString(8);
                Date dob = rs.getDate(9);
                String bio = rs.getString(10);
                users.add(new User.UserBuilder().setPhoneNumber(phoneNumber).setDisplayName(displayName).setEmail(email).setPassword(password).setGender(gender)
                        .setCountry(country).setDateOfBirth(dob).setBio(bio).createUser());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return users;
    }

    public boolean findUser(String phone) {
        Connection conn = DBConnection.getConnection();
        String query = "SELECT * FROM user WHERE phone_number = ?;";
        boolean exists = false;
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                exists = true;
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exists;
    }

    public int addUser(User user) {
        Connection conn = DBConnection.getConnection();
        String query = "INSERT INTO user values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int succeed = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            setData(user, ps);
            succeed = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return succeed;
        }
    }

    private void setData(User user, PreparedStatement ps) throws SQLException {
        ps.setString(2, user.getPhoneNumber());
        ps.setString(3, user.getDisplayName());
        ps.setString(4, "user.getPicture()");
        ps.setString(6, user.getPassword());
        ps.setString(7, user.getGender());
        ps.setString(8, user.getCountry());
        ps.setDate(9, user.getDateOfBirth());
        ps.setString(10, user.getBio());
    }
}
