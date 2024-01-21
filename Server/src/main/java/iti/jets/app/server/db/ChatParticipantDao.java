package iti.jets.app.server.db;

import iti.jets.app.shared.models.entities.ChatParticipant;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatParticipantDao implements Dao<ChatParticipant , Integer>{
    private DataSource dataSource;

    public ChatParticipantDao() {
        dataSource = DataSourceFactory.getMySQLDataSource();
    }

    @Override
    public ChatParticipant getById(Integer integer) {
        throw new IllegalArgumentException("Error");
    }

    @Override
    public int insert(ChatParticipant chatParticipant) {
        String query = "INSERT INTO chatparticipants (  chat_id , participant_id , member_since) " +
                "VALUES ( ?, ?, ?)";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, chatParticipant.getChatId());
            preparedStatement.setInt(2, chatParticipant.getParticipantId());
            preparedStatement.setTimestamp(3, chatParticipant.getMemberSince());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(ChatParticipant chatParticipant) {
        throw new IllegalArgumentException("Error");
    }

    @Override
    public int delete(Integer integer) {
        throw new IllegalArgumentException("Error");
    }

    public ResultSet selectByCompositeKey(Integer chat_id  , Integer participant_id ) {
        String query = "SELECT * FROM chatparticipants WHERE chat_id = ? and participant_id = ?";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, chat_id);
            preparedStatement.setInt(2, participant_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}