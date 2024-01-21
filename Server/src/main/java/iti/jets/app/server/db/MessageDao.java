package iti.jets.app.server.db;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import iti.jets.app.shared.models.entities.Message;

public class MessageDao implements Dao<Message, Integer> {
    private DataSource dataSource;

    public MessageDao() {
        dataSource = DataSourceFactory.getMySQLDataSource();
    }

    private Message extractMessage(ResultSet resultSet) throws SQLException {
        Message message = new Message(resultSet.getInt("message_id"));
        message.setSenderId(resultSet.getInt("sender_id"));
        message.setChatId(resultSet.getInt("chat_id"));
        message.setContainsFile(resultSet.getBoolean("contains_file"));
        message.setMessageContent(resultSet.getString("message_content"));
        message.setSentAt(resultSet.getTimestamp("sent_at"));
        return message;
    }
    @Override
    public Message getById(Integer messageId) {
        String query = "SELECT * FROM messages WHERE message_id = ?";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, messageId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractMessage(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int insert(Message message) {
        String query = "INSERT INTO messages (  sender_id, chat_id, contains_file, message_content, sent_at) " +
                "VALUES ( ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, message.getSenderId());
            preparedStatement.setInt(2, message.getChatId());
            preparedStatement.setBoolean(3, message.isContainsFile());
            preparedStatement.setString(4, message.getMessageContent());
            preparedStatement.setTimestamp(5, message.getSentAt());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Message message) {
        String query = "UPDATE messages SET `sender_id`=?,`chat_id`=?,`contains_file`=?, " +
                "`message_content`=?,`sent_at`=? WHERE message_id = ?";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, message.getSenderId());
            preparedStatement.setInt(2, message.getChatId());
            preparedStatement.setBoolean(3, message.isContainsFile());
            preparedStatement.setString(4, message.getMessageContent());
            preparedStatement.setTimestamp(5, message.getSentAt());
            preparedStatement.setInt(6, message.getMessageId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Integer messageId) {
        String query = "DELETE FROM messages WHERE message_id = ?";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, messageId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}