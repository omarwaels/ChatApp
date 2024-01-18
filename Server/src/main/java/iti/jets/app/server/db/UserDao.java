package iti.jets.app.server.db;

import iti.jets.app.shared.models.entities.User;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao implements Dao<User, String> {
    private DataSource dataSource;

    public UserDao() {
        dataSource = DataSourceFactory.getMySQLDataSource();
    }

    @Override
    public ResultSet select(String phoneNumber) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = dataSource.getConnection().prepareStatement("SELECT * FROM user WHERE phoneNumber = ?");
            preparedStatement.setString(1, phoneNumber);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return resultSet;
        }
    }

    @Override
    public int insert(User user) {
        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "INSERT INTO Users (phone_number, display_name, email, picture, password, gender, country, date_of_birth, bio) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
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
            System.out.println("hna");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return result;
        }
    }

    @Override
    public int update(User user) {
        PreparedStatement preparedStatement = null;
        int result = 0;
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
        try {
            preparedStatement = dataSource.getConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getDisplayName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setBytes(3, user.getPicture());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getGender());
            preparedStatement.setString(6, user.getCountry());
            preparedStatement.setDate(7, user.getDateOfBirth());
            preparedStatement.setString(8, user.getBio());
            preparedStatement.setString(9, user.getPhoneNumber());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    @Override
    public int delete(String phoneNumber) {
        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "DELETE FROM users WHERE phone_number = ?";
        try {
            preparedStatement = dataSource.getConnection().prepareStatement(query);
            preparedStatement.setString(1, phoneNumber);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return result;
        }
    }

    public int updateStatus(String phoneNumber, String status) {
        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "UPDATE users " +
                "SET status = ?" +
                "WHERE phone_number = ?;";
        try {
            preparedStatement = dataSource.getConnection().prepareStatement(query);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, phoneNumber);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return result;
        }
    }

    public int updateMode(String phoneNumber, String mode) {
        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "UPDATE users " +
                "SET mode = ?" +
                "WHERE phone_number = ?;";
        try {
            preparedStatement = dataSource.getConnection().prepareStatement(query);
            preparedStatement.setString(1, mode);
            preparedStatement.setString(2, phoneNumber);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return result;
        }
    }

    public ResultSet getAllUsers() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = dataSource.getConnection().prepareStatement("SELECT * FROM user");
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return resultSet;
        }
    }

}
