package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.*;

public class MessageService {
    private static MessageDAO messageDAO = new MessageDAO();

    public boolean addMessage(Message message) {
        if (message.getMessage_text() == null || 
            message.getMessage_text().length() < 1 || 
            message.getMessage_text().length() >= 255) {
            return false;
        }

        if (AccountService.accountExistsById(message.getPosted_by())) {
            Message updatedMessage = messageDAO.create(message);
            if (updatedMessage != null) {
                message.setMessage_id(updatedMessage.getMessage_id());
                return true;
            }
        }

        return false;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAll();
    } 

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id) {
        return messageDAO.deleteMessageById(id);
    }

    public boolean updateMessage(Message message, int message_id) {
        if (message.getMessage_text() == null || 
            message.getMessage_text().length() < 1 || 
            message.getMessage_text().length() >= 255) {
            return false;
        }

        Message existingMessage = getMessageById(message_id);
        if (existingMessage != null) {
            existingMessage.setMessage_text(message.getMessage_text());
            if (messageDAO.updateMessage(existingMessage) != null) {
                message.setMessage_id(message_id);
                message.setPosted_by(existingMessage.getPosted_by());
                message.setTime_posted_epoch(existingMessage.getTime_posted_epoch());
                return true;
            }
        }

        return false;
    }

    public List<Message> getAllMessagesByAccountId(int id) {
        return messageDAO.getAllMessagesByUserId(id);
    }

}
