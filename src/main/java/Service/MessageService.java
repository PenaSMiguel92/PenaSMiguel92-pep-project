package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.*;

public class MessageService {
    MessageDAO messageDAO = new MessageDAO();

    public boolean addMessage(Message message) {
        if (this.messageDAO.create(message) != null)
            return true;

        return false;
    }

    public List<Message> getAllMessages() {
        return this.messageDAO.getAll();
    } 

    public Message getMessageById(int id) {
        return this.messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id) {
        return this.messageDAO.deleteMessageById(id);
    }

    public Message updateMessage(Message message) {
        return this.messageDAO.updateMessage(message);
    }

    public List<Message> getAllMessagesByAccountId(int id) {
        return this.messageDAO.getAllMessagesByUserId(id);
    }

}
