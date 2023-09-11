package Service;
import DAO.AccountDAO;
import Model.Account;
import io.javalin.http.Context;
import java.util.*;

public class AccountService {
    AccountDAO accountDAO = new AccountDAO();

    public boolean checkIfUsernameExists(String username) {
        List<String> usernames = this.accountDAO.getAllUsernames();
        return usernames.contains(username);
    }

    public boolean addAccount(Account user) {
        Account updatedAccount = this.accountDAO.create(user);
        if (updatedAccount != null) {
            user.setAccount_id(updatedAccount.getAccount_id());
            return true;
        } else {
            return false;
        }
    }

    public Account getAccountByUsername(Account user) {
        return this.accountDAO.getAccount(user);
    }

    public boolean accountExistsById(int id) {
        return this.accountDAO.accountExistsById(id);
    }

}
