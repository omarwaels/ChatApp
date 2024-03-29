package iti.jets.app.server.db;

import iti.jets.app.server.models.entities.Chat;
import iti.jets.app.server.models.entities.ChatParticipant;
import iti.jets.app.server.models.entities.User;
import iti.jets.app.shared.DTOs.ChatDto;
import iti.jets.app.shared.DTOs.FriendInfoDto;
import iti.jets.app.shared.enums.ModeEnum;
import iti.jets.app.shared.enums.StatusEnum;
import iti.jets.app.shared.enums.UserEnum;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatParticipantDao implements Dao<ChatParticipant, Integer> {
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
        String query = "INSERT INTO chatparticipants (chat_id , participant_id , member_since) " +
                "VALUES ( ?, ?, ?)";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
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

    public int deleteChatParticipant(int userId, int chatId) {
        String query = "DELETE FROM chatparticipants WHERE participant_id = ? AND chat_id = ?";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, chatId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<Integer, Integer> getUserFriendsAndChatIDs(int userId, ArrayList<Integer> Friends) {
        String query = "SELECT * FROM chatparticipants INNER JOIN chats ON chatparticipants.chat_id = chats.chat_id WHERE chats.chat_id In (SELECT chats.chat_id FROM chatparticipants INNER JOIN chats ON chatparticipants.chat_id = chats.chat_id WHERE chats.admin_id is NULL and participant_id = ?) and chatparticipants.participant_id <> ?;";
        HashMap<Integer, Integer> userContacts = new HashMap<>();
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int userFriendId = resultSet.getInt("participant_id");
                    int chatId = resultSet.getInt("chat_id");
                    userContacts.put(userFriendId, chatId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userContacts;
    }

    public HashMap<Chat, ArrayList<User>> getUserGroups(int userId) {
        String query = "SELECT cp.chat_id FROM chatparticipants cp JOIN chats c WHERE cp.chat_id = c.chat_id AND participant_id = ? AND admin_id IS NOT NULL;";
        HashMap<Chat, ArrayList<User>> groupParticipants = new HashMap<>();
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int chatId = resultSet.getInt("chat_id");
                    ChatDao chatDao = new ChatDao();
                    Chat chat = chatDao.getById(chatId);
                    ArrayList<User> participants = getChatParticipants(chatId, userId);
                    groupParticipants.put(chat, participants);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groupParticipants;
    }

    public ArrayList<User> getChatParticipants(int chat_id, int userId) {
        ArrayList<User> participants = new ArrayList<>();
        String query = "SELECT u.* FROM users u JOIN chatparticipants cp WHERE u.user_id = cp.participant_id AND chat_id = ? AND u.user_id <> ?;";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, chat_id);
            preparedStatement.setInt(2, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                    participants.add(extractUser(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return participants;
    }

    public ArrayList<Integer> getChatParticipants(int chat_id) {
        ArrayList<Integer> participants = new ArrayList<>();
        String query = "SELECT u.user_id FROM users u JOIN chatparticipants cp WHERE u.user_id = cp.participant_id AND chat_id = ?;";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, chat_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                    participants.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return participants;
    }

    public int addGroupParticipants(int groupId, List<Integer> usersIds) {
        String query = "INSERT INTO chatparticipants (chat_id, participant_id, member_since) VALUES (?, ?, ?)";
        try (java.sql.Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            for (int userId : usersIds) {
                preparedStatement.setInt(1, groupId);
                preparedStatement.setInt(2, userId);
                preparedStatement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.addBatch();
            }
            return preparedStatement.executeBatch().length;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User extractUser(ResultSet resultSet) throws SQLException {
        return new User.UserBuilder().setPicture(resultSet.getBytes(UserEnum.PICTURE.getField()))
                .setDisplayName(resultSet.getString(UserEnum.DISPLAY_NAME.getField()))
                .setId(resultSet.getInt(UserEnum.ID.getField()))
                .setStatus(StatusEnum.valueOf(resultSet.getString(UserEnum.STATUS.getField()).toUpperCase()))
                .setMode(ModeEnum.valueOf(resultSet.getString(UserEnum.MODE.getField()).toUpperCase()))
                .build();
    }
}