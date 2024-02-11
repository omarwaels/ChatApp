package iti.jets.app.server.db;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import iti.jets.app.server.models.entities.Message;

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
        message.setFontWeight(resultSet.getString("font_weight"));
        message.setFontSize(resultSet.getInt("font_size"));
        message.setFontColor(resultSet.getString("font_color"));
        message.setFontFamily(resultSet.getString("font_family"));
        message.setFontStyle(resultSet.getString("font_style"));
        message.setUnderline(resultSet.getBoolean("underline"));
        return message;
    }

    @Override
    public Message getById(Integer messageId) {
        String query = "SELECT * FROM messages WHERE message_id = ?";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
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
        String query = "INSERT INTO messages (`sender_id`, `chat_id`, `contains_file`, `message_content`, `sent_at`, `font_weight`, `font_size`, `font_color`, `font_family`, `font_style`, `underline`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, message.getSenderId());
            preparedStatement.setInt(2, message.getChatId());
            preparedStatement.setBoolean(3, message.isContainsFile());
            preparedStatement.setString(4, message.getMessageContent());
            preparedStatement.setTimestamp(5, message.getSentAt());
            preparedStatement.setString(6, message.getFontWeight());
            preparedStatement.setInt(7, message.getFontSize());
            preparedStatement.setString(8, message.getFontColor());
            preparedStatement.setString(9, message.getFontFamily());
            preparedStatement.setString(10, message.getFontStyle());
            preparedStatement.setBoolean(11, message.getUnderline());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Message message) {
        String query = "UPDATE messages SET `sender_id`=?,`chat_id`=?,`contains_file`=?, " +
                "`message_content`=?,`sent_at`=? WHERE message_id = ?";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
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
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, messageId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteMessagesByChatId(Integer chatId) {
        String query = "DELETE FROM messages WHERE chat_id = ?";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, chatId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Message> getChatMessages(int chatId) {
        String query = "SELECT * FROM messages WHERE chat_id = ? ORDER BY sent_at ASC";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, chatId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ArrayList<Message> messages = new ArrayList<>();
                while (resultSet.next()) {
                    messages.add(extractMessage(resultSet));
                }
                return messages;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}