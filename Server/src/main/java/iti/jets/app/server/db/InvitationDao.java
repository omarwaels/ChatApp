package iti.jets.app.server.db;

import iti.jets.app.server.models.entities.Invitation;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InvitationDao implements Dao<Invitation, Integer> {

    private DataSource dataSource;

    public InvitationDao() {
        this.dataSource = DataSourceFactory.getMySQLDataSource();
    }

    private Invitation extractInvitation(ResultSet resultSet) throws SQLException {
        Invitation invitation = new Invitation();
        invitation.setSenderID(resultSet.getInt("sender_id"));
        invitation.setReceiverID(resultSet.getInt("receiver_id"));
        return invitation;
    }

    @Override
    public Invitation getById(Integer integer) {
        return null;
    }

    @Override
    public int insert(Invitation invitation) {
        String query = "INSERT INTO invitations (sender_id , receiver_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, invitation.getSenderID());
            preparedStatement.setInt(2, invitation.getReceiverID());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Invitation invitation) {
        return 0;
    }

    @Override
    public int delete(Integer integer) {
        return 0;
    }

    public int delete(Invitation invitation) {
        String query = "DELETE FROM invitations WHERE sender_id = ? AND receiver_id  = ?";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, invitation.getSenderID());
            preparedStatement.setInt(2, invitation.getReceiverID());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Invitation> getAllInvitations(int userID) {
        String query = "SELECT * FROM invitations WHERE receiver_id  = ?";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, userID);
            ArrayList<Invitation> invitations = new ArrayList<>();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                invitations.add(extractInvitation(rs));
            }
            rs.close();
            return invitations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int acceptInvitation(Invitation invitation) {
        String insertQuery = "INSERT INTO connections (first_user_id, second_user_id, connected_since) VALUES (?, ?, ?)";
        delete(invitation);
        try (PreparedStatement insertStatement = dataSource.getConnection().prepareStatement(insertQuery);) {
            insertStatement.setInt(1, invitation.getReceiverID());
            insertStatement.setInt(2, invitation.getSenderID());
            insertStatement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            return insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int declineInvitation(Invitation invitation) {
        return delete(invitation);
    }

    public Invitation getInvitation(int senderId, int receiverId) {
        String query = "SELECT * FROM invitations WHERE sender_id = ? AND receiver_id = ?";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, senderId);
            preparedStatement.setInt(2, receiverId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractInvitation(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}