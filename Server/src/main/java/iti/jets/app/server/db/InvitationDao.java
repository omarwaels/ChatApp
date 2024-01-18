package iti.jets.app.server.db;

import iti.jets.app.shared.models.entities.Invitation;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InvitationDao implements Dao<Invitation, Integer>{

    private DataSource dataSource;

    public InvitationDao() {
        this.dataSource = DataSourceFactory.getMySQLDataSource();
    }
    @Override
    public ResultSet select(Integer integer) {
        return null;
    }

    @Override
    public int insert(Invitation invitation) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = dataSource.getConnection().prepareStatement("INSERT INTO invitation (senderID, receiverID) VALUES (?, ?)");
            preparedStatement.setInt(1, invitation.getSenderID());
            preparedStatement.setInt(2, invitation.getReceiverID());
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = dataSource.getConnection().prepareStatement("DELETE FROM invitation WHERE senderID = ? AND receiverID = ?");
            preparedStatement.setInt(1, invitation.getSenderID());
            preparedStatement.setInt(2, invitation.getReceiverID());
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ResultSet getAllInvitations(int userID) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = dataSource.getConnection().prepareStatement("SELECT * FROM invitation WHERE receiverID = ?");
            preparedStatement.setInt(1, userID);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getInvitation(Invitation invitation) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = dataSource.getConnection().prepareStatement("SELECT * FROM invitation WHERE senderID = ? AND receiverID = ?");
            preparedStatement.setInt(1, invitation.getSenderID());
            preparedStatement.setInt(2, invitation.getReceiverID());
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
