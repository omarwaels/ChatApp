package iti.jets.app.server.db;



import javax.sql.DataSource;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import iti.jets.app.shared.models.entities.Chat;

public class ChatsDao implements Dao<Chat, Integer> {
    private DataSource dataSource;

    public ChatsDao() {
        dataSource = DataSourceFactory.getMySQLDataSource();
    }

    @Override
    public ResultSet select(Integer chat_id ) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = dataSource.getConnection().prepareStatement("SELECT * FROM chats WHERE chat_id = ?");

            preparedStatement.setInt(1, chat_id );

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return resultSet;

        }
    }

    @Override
    public int insert(Chat chat) {




        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "INSERT INTO chats (  chat_image, chat_name, created_at, admin_id ) " +
                "VALUES ( ?, ?, ?, ?)";


        try {
            preparedStatement = dataSource.getConnection().prepareStatement(query);

            Blob blob = dataSource.getConnection().createBlob();
            blob.setBytes(1, chat.getChatImage());


            preparedStatement.setBlob(1, blob);
            preparedStatement.setString(2, chat.getChatName());
            preparedStatement.setTimestamp(3, chat.getCreatedAt());
            preparedStatement.setInt(4,chat.getAdminId() );
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
    public int update(Chat chat) {


        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "UPDATE messages SET `chat_image`=?,`chat_name`=?,`created_at`=?, " +
                "`admin_id `=? WHERE chat_id  = ?";

        try {
            preparedStatement = dataSource.getConnection().prepareStatement(query);
            Blob blob = dataSource.getConnection().createBlob();
            blob.setBytes(1, chat.getChatImage());


            preparedStatement.setBlob(1, blob);
            preparedStatement.setString(2, chat.getChatName());
            preparedStatement.setTimestamp(3, chat.getCreatedAt());
            preparedStatement.setInt(4,chat.getAdminId() );
            preparedStatement.setInt(5,chat.getChatId() );
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
    public int delete(Integer chat_id) {


        PreparedStatement preparedStatement = null;
        int result = 0;
        String query = "DELETE FROM chats WHERE chat_id  = ?";

        try {
            preparedStatement = dataSource.getConnection().prepareStatement(query);

            preparedStatement.setInt(1, chat_id);
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

