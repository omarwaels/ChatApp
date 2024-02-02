package iti.jets.app.server.db;

import iti.jets.app.shared.DTOs.UserInvitationDto;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvitationRequestDAO {
    private DataSource dataSource;

    public InvitationRequestDAO() {
        dataSource = DataSourceFactory.getMySQLDataSource();
    }
    public List<UserInvitationDto> getInvitationsByReceiverId(int receiverId) {
        List<UserInvitationDto> invitations = new ArrayList<>();

        String sql = "SELECT i.invitation_id, u.display_name AS userName, u.phone_number, u.user_id, u.picture AS image " +
                "FROM Users u " +
                "JOIN Invitations i ON u.user_id = i.sender_id " +
                "WHERE i.receiver_id = ?";

        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, receiverId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int invitationId = resultSet.getInt("invitation_id");
                    String userName = resultSet.getString("userName");
                    String phoneNumber = resultSet.getString("phone_number");
                    int userId = resultSet.getInt("user_id");
                    byte[] image = resultSet.getBytes("image");

                    UserInvitationDto invitationDto = new UserInvitationDto(invitationId, userName, phoneNumber, userId, image);
                    invitations.add(invitationDto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        }

        return invitations;
    }
    public boolean acceptInvitation(int invitationId, int receiverId) {
        String selectInvitationQuery = "SELECT sender_id FROM Invitations WHERE invitation_id = ? AND receiver_id = ? AND sent_at IS NOT NULL LIMIT 1";
        String insertConnectionQuery = "INSERT INTO Connections (first_user_id, second_user_id, connected_since) VALUES (?, ?, ?)";
        String deleteInvitationQuery = "DELETE FROM Invitations WHERE invitation_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectInvitationStatement = connection.prepareStatement(selectInvitationQuery);
             PreparedStatement insertConnectionStatement = connection.prepareStatement(insertConnectionQuery);
             PreparedStatement deleteInvitationStatement = connection.prepareStatement(deleteInvitationQuery)) {

            // Set parameters for the selectInvitationQuery
            selectInvitationStatement.setInt(1, invitationId);
            selectInvitationStatement.setInt(2, receiverId);

            // Execute the selectInvitationQuery
            try (var resultSet = selectInvitationStatement.executeQuery()) {
                if (resultSet.next()) {
                    int senderId = resultSet.getInt("sender_id");

                    // Set parameters for the insertConnectionQuery
                    int firstUserId = receiverId; // Assuming the currently logged-in user's ID is used as the receiver
                    insertConnectionStatement.setInt(1, firstUserId);
                    insertConnectionStatement.setInt(2, senderId);
                    insertConnectionStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

                    // Execute the insertConnectionQuery
                    int rowsAffected = insertConnectionStatement.executeUpdate();

                    // Check if the insertion was successful
                    if (rowsAffected > 0) {
                        // Delete the invitation after accepting
                        deleteInvitationStatement.setInt(1, invitationId);
                        deleteInvitationStatement.executeUpdate();

                        return true;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        }

        // Return false if the invitation was not accepted
        return false;
    }

}
