package iti.jets.app.server.db;



import iti.jets.app.server.models.entities.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardDao {
    private DataSource dataSource;

    public DashboardDao() {
        this.dataSource = DataSourceFactory.getMySQLDataSource();
    }


    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        User user = new User.UserBuilder()
                                .setId(resultSet.getInt("user_id"))
                                .setPhoneNumber(resultSet.getString("phone_number"))
                                .setDisplayName(resultSet.getString("display_name"))
                                .setEmail(resultSet.getString("email"))
                                .setPicture(resultSet.getBytes("picture"))
                                .setPassword(resultSet.getString("password"))
                                .setGender(resultSet.getString("gender"))
                                .setCountry(resultSet.getString("country"))
                                .setDateOfBirth(resultSet.getDate("date_of_birth"))
                                .setBio(resultSet.getString("bio"))
                                .build();

                        userList.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        return userList;
    }

    public Map<String, Integer> getMaleFemaleCount() {
        Map<String, Integer> countMap = new HashMap<>();

        try {
            Connection connection = dataSource.getConnection();
            String query = "SELECT gender, COUNT(*) as count FROM users GROUP BY gender";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String gender = resultSet.getString("gender");
                        int genderCount = resultSet.getInt("count");

                        countMap.put(gender, genderCount);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return countMap;
    }

    public Map<String, Integer> getUsersCountByCountry() {
        Map<String, Integer> countMap = new HashMap<>();

        try {
            Connection connection = dataSource.getConnection();
            String query = "SELECT country, COUNT(*) as count FROM users GROUP BY country";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String country = resultSet.getString("country");
                        int countryCount = resultSet.getInt("count");
                        countMap.put(country, countryCount);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countMap;
    }
    public Map<String, Integer> getUsersCountByStatus() {
        Map<String, Integer> countMap = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT UserStatus, COUNT(*) as count FROM users GROUP BY UserStatus";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String status = resultSet.getString("UserStatus");
                        int statusCount = resultSet.getInt("count");

                        countMap.put(status, statusCount);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countMap;
    }


}