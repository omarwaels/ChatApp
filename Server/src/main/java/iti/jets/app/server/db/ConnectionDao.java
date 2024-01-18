package iti.jets.app.server.db;

import iti.jets.app.shared.models.entities.Connection;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionDao implements Dao<Connection, Integer> {

    DataSource dataSource = null;

    public ConnectionDao() {
        dataSource = DataSourceFactory.getMySQLDataSource();
    }

    @Override
    public ResultSet select(Integer integer) {
        return null;
    }

    public int insert(Connection connection) {
        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "INSERT INTO connections (first_user_id, second_user_id, connected_since) VALUES (?, ?, ?)";
        try {
            preparedStatement = dataSource.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, connection.getFirstUserId());
            preparedStatement.setInt(2, connection.getSecondUserId());
            preparedStatement.setTimestamp(3, connection.getConnectedSince());
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

    @Override
    public int update(Connection connection) {
        return 0;
    }

    @Override
    public int delete(Integer integer) {
        return 0;
    }

    public int delete(Connection connection) {
        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "DELETE FROM connections \n" +
                "WHERE (first_user_id = ? AND second_user_id = ?) OR (second_user_id = ? AND first_user_id = ?);";
        try {
            preparedStatement = dataSource.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, connection.getFirstUserId());
            preparedStatement.setInt(2, connection.getSecondUserId());
            preparedStatement.setInt(3, connection.getSecondUserId());
            preparedStatement.setInt(4, connection.getFirstUserId());
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

    public ResultSet getAllConnections(int userId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT second_user_id FROM connections WHERE first_user_id = ? \n" +
                "UNION\n" +
                "SELECT first_user_id FROM connections WHERE second_user_id = ?;";
        try {
            preparedStatement = dataSource.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);
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
