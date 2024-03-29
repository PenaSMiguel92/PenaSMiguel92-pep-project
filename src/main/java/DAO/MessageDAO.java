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
            PreparedStatement ps = connection.prepareStatement(sql, 
                                              Statement.RETURN_GENERATED_KEYS);
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

    public Message deleteMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();
        Message message = null;
        try {
            message = getMessageById(id);
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected < 1) {
                message = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    public Message updateMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, message.getMessage_text());
            ps.setInt(2, message.getMessage_id());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) 
                return message;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getAllMessagesByUserId(int id) {
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
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
}
