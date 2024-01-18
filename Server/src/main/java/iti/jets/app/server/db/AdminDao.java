package iti.jets.app.server.db;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {
    private DataSource dataSource;

    public AdminDao() {
        this.dataSource = DataSourceFactory.getMySQLDataSource();
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
