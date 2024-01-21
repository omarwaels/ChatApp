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
    @Override
    public Message getById(Integer messageId) {

        Message message = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = dataSource.getConnection().prepareStatement("SELECT * FROM messages WHERE message_id = ?");
            preparedStatement.setInt(1, messageId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                message = new Message(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getBoolean(4),resultSet.getString(5),resultSet.getTimestamp(6));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return message;

        }

    }

    @Override
    public int insert(Message message) {
        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "INSERT INTO messages (  sender_id, chat_id, contains_file, message_content, sent_at) " +
                "VALUES ( ?, ?, ?, ?, ?)";

        try {
            preparedStatement = dataSource.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, message.getSenderId());
            preparedStatement.setInt(2, message.getChatId());
            preparedStatement.setBoolean(3, message.isContainsFile());
            preparedStatement.setString(4, message.getMessageContent());
            preparedStatement.setTimestamp(5, message.getSentAt());
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
    public int update(Message message)  {

        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "UPDATE messages SET `sender_id`=?,`chat_id`=?,`contains_file`=?, " +
                "`message_content`=?,`sent_at`=? WHERE message_id = ?";

        try {
            preparedStatement = dataSource.getConnection().prepareStatement(query);

            preparedStatement.setInt(1, message.getSenderId());
            preparedStatement.setInt(2, message.getChatId());
            preparedStatement.setBoolean(3, message.isContainsFile());
            preparedStatement.setString(4, message.getMessageContent());
            preparedStatement.setTimestamp(5, message.getSentAt());
            preparedStatement.setInt(6, message.getMessageId());
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
    public int delete(Integer messageId) {

        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "DELETE FROM messages WHERE message_id = ?";

        try {
            preparedStatement = dataSource.getConnection().prepareStatement(query);

            preparedStatement.setInt(1, messageId);
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
}
