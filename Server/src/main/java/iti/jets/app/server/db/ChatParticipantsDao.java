package iti.jets.app.server.db;


import iti.jets.app.shared.models.entities.ChatParticipant;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatParticipantsDao implements Dao<ChatParticipant , Integer>{
    private DataSource dataSource;
    public ChatParticipantsDao() {
        dataSource = DataSourceFactory.getMySQLDataSource();
    }
    @Override
    public ChatParticipant getById(Integer integer) {
        throw new IllegalArgumentException("Error");
    }

    @Override
    public int insert(ChatParticipant chatParticipant) {

        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "INSERT INTO chatparticipants (  chat_id , participant_id , member_since) " +
                "VALUES ( ?, ?, ?)";


        try {
            preparedStatement = dataSource.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, chatParticipant.getChatId());
            preparedStatement.setInt(2, chatParticipant.getParticipantId());
            preparedStatement.setTimestamp(3, chatParticipant.getMemberSince());

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
    public int update(ChatParticipant chatParticipant) {

        throw new IllegalArgumentException("Error");
    }

    @Override
    public int delete(Integer integer) {
        throw new IllegalArgumentException("Error");
    }

    public ResultSet selectByCompositeKey(Integer chat_id  , Integer participant_id ) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM chatparticipants WHERE chat_id = ? and participant_id = ?";


        try {
            preparedStatement = dataSource.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, chat_id);
            preparedStatement.setInt(2, participant_id);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {


            return resultSet;
        }
    }


}
