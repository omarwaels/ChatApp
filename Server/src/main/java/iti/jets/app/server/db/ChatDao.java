package iti.jets.app.server.db;

import javax.sql.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import iti.jets.app.server.models.entities.Chat;
import iti.jets.app.server.models.entities.Connection;

public class ChatDao implements Dao<Chat, Integer> {
    private DataSource dataSource;

    public ChatDao() {
        dataSource = DataSourceFactory.getMySQLDataSource();
    }

    @Override
    public Chat getById(Integer chat_id) {
        String query = "SELECT * FROM chats WHERE chat_id = ?";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, chat_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Chat(resultSet.getInt(1), resultSet.getBytes(2), resultSet.getString(3), resultSet.getTimestamp(4), resultSet.getInt(5));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(Chat chat) {
        String query = "INSERT INTO chats (chat_image, chat_name, created_at, admin_id) " +
                "VALUES ( ?, ?, ?, ?)";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setBytes(1, chat.getChatImage());
            preparedStatement.setString(2, chat.getChatName());
            preparedStatement.setTimestamp(3, chat.getCreatedAt());
            preparedStatement.setInt(4, chat.getAdminId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Chat chat) {
        String query = "UPDATE messages SET `chat_image`=?,`chat_name`=?,`created_at`=?, " +
                "`admin_id `=? WHERE chat_id  = ?";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setBytes(1, chat.getChatImage());
            preparedStatement.setString(2, chat.getChatName());
            preparedStatement.setTimestamp(3, chat.getCreatedAt());
            preparedStatement.setInt(4, chat.getAdminId());
            preparedStatement.setInt(5, chat.getChatId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Integer chat_id) {
        String query = "DELETE FROM chats WHERE chat_id  = ?";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, chat_id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int createGroupChat(Chat chat) {
        String query = "INSERT INTO chats (chat_image, chat_name, created_at, admin_id) " +
                "VALUES ( ?, ?, ?, ?)";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setBytes(1, chat.getChatImage());
            preparedStatement.setString(2, chat.getChatName());
            preparedStatement.setTimestamp(3, chat.getCreatedAt());
            preparedStatement.setInt(4, chat.getAdminId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getGroupChatId(Chat chat) {
        String query = "SELECT chat_id FROM chats WHERE chat_name = ? AND admin_id = ? AND created_at = ?";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, chat.getChatName());
            preparedStatement.setInt(2, chat.getAdminId());
            preparedStatement.setTimestamp(3, chat.getCreatedAt());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int createPrivateChat(Chat chat) throws SQLException {
        String query = "INSERT INTO chats (chat_name, created_at) " +
                "VALUES (?, ?)";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, chat.getChatName());
            preparedStatement.setTimestamp(2, chat.getCreatedAt());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPrivateChatId(Chat chat) {
        String query = "SELECT chat_id FROM chats WHERE chat_name = ? AND created_at = ?";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, chat.getChatName());
            preparedStatement.setTimestamp(2, chat.getCreatedAt());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}