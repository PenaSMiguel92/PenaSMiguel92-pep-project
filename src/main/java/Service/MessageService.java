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

}
