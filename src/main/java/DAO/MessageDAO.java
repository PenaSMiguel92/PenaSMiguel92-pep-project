package DAO;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;

public class MessageDAO {

    public Message create(Message message) {
        //Message newMessage = null;
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message VALUES (DEFAULT, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();

            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_message_id = pkeyResultSet.getInt(1);
                message.setMessage_id(generated_message_id);
                return message;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Message";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                messages.add(new Message(rs.getInt("message_id"),
                                     rs.getInt("posted_by"), 
                                     rs.getString("message_text"), 
                                     rs.getLong("time_posted_epoch")
                                    )
                        );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public Message getMessageById(int id) {
        Message message = null;
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                message = new Message(rs.getInt("message_id"),
                                      rs.getInt("posted_by"), 
                                      rs.getString("message_text"), 
                                      rs.getLong("time_posted_epoch")
                                     );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    public boolean deleteMessageById(int id) {
        boolean success = false;
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected>0)
                success = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public Message updateMessageById(int id) {
        return null;
    }

    public List<Message> getAllMessagesByUserId(int id) {
        return null;
    }
}
