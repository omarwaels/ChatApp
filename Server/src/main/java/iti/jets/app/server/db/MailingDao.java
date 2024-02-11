package iti.jets.app.server.db;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MailingDao {
    DataSource dataSource = null;

    public MailingDao() {
        dataSource = DataSourceFactory.getMySQLDataSource();
    }

    public String getEmailByUserId(int id) {
        String query = "SELECT email FROM users WHERE user_id = ?";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("email");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
