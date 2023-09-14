package Service;
import DAO.AccountDAO;
import Model.Account;
import java.util.*;

public class AccountService {
    private static AccountDAO accountDAO = new AccountDAO();

    public boolean checkIfUsernameExists(String username) {
        List<String> usernames = accountDAO.getAllUsernames();
        return usernames.contains(username);
    }

    public boolean addAccount(Account user) {
        //pass by reference, changes to user here will carry over to calling method.
        if (user.getUsername() == null || user.getUsername().length() < 1)
            return false;
        if (user.getPassword().length() < 4) 
            return false;
        if (!checkIfUsernameExists(user.getUsername())) {
            Account updatedAccount = accountDAO.create(user);
            if (updatedAccount != null) {
                user.setAccount_id(updatedAccount.getAccount_id());
                return true;
            }
        }
        return false;
    }

    public boolean login(Account user) {
        if (checkIfUsernameExists(user.getUsername())) {
            Account existingUser = getAccountByUsername(user);
            if (existingUser.getPassword().equals(user.getPassword())) {
                user.setAccount_id(existingUser.getAccount_id());
                return true;
            }
        }
        return false;
    }

    public Account getAccountByUsername(Account user) {
        return accountDAO.getAccount(user);
    }

    public static boolean accountExistsById(int id) {
        return accountDAO.accountExistsById(id);
    }

}
