package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::userRegistration);
        app.post("/login", this::userLogin);
        
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}", this::updateMessageById);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserId);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }
    
    private void userRegistration(Context ctx) {
        Account user = ctx.bodyAsClass(Account.class);
        if (user.getUsername() == null || user.getUsername().length() < 1) {
            ctx.status(400);
            return;
        }
        if (user.getPassword().length() < 4) {
            ctx.status(400);
            return;
        }
        if (!accountService.checkIfUsernameExists(user.getUsername())) {
            if (accountService.addAccount(user)) {
                ctx.json(user);
                return;
            }
        }
           
        ctx.status(400);
    }

    private void userLogin(Context ctx) {
        Account user = ctx.bodyAsClass(Account.class);
        if (accountService.checkIfUsernameExists(user.getUsername())) {
            Account existingUser = accountService.getAccountByUsername(user);
            if (existingUser.getPassword().equals(user.getPassword())) {
                user.setAccount_id(existingUser.getAccount_id());
                ctx.json(user);
                return;
            }
        }
        ctx.status(401);
    }

    public void createMessage(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
        if (message.getMessage_text() == null || 
            message.getMessage_text().length() < 1 || 
            message.getMessage_text().length() >= 255) {
            ctx.status(400);
            return;
        }

        if (accountService.accountExistsById(message.getPosted_by())) {
            if (messageService.addMessage(message)) {
                ctx.json(message);
                return;
            }
        }

        ctx.status(400);
    }

    public void getAllMessages(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    public void getMessageById(Context ctx) {
        String paramValue = ctx.pathParam("message_id");
        int message_id = Integer.parseInt(paramValue);

        Message message = messageService.getMessageById(message_id);
        if (message != null)
            ctx.json(message);

    }

    public void deleteMessageById(Context ctx) {
        String paramValue = ctx.pathParam("message_id");
        int message_id = Integer.parseInt(paramValue);

        Message message = messageService.deleteMessageById(message_id);
        if (message != null)
            ctx.json(message);
    }

    public void updateMessageById(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
        
        if (message.getMessage_text() == null || 
            message.getMessage_text().length() < 1 || 
            message.getMessage_text().length() >= 255) {
                ctx.status(400);
                return;
        }

        String paramValue = ctx.pathParam("message_id");
        int message_id = Integer.parseInt(paramValue);

        Message existingMessage = messageService.getMessageById(message_id);
        if (existingMessage != null) {
            existingMessage.setMessage_text(message.getMessage_text());
            messageService.updateMessage(existingMessage);
            ctx.json(existingMessage);
            return;
        }

        ctx.status(400);
    }

    public void getAllMessagesByUserId(Context ctx) {
        String paramValue = ctx.pathParam("account_id");
        int account_id = Integer.parseInt(paramValue);

        List<Message> userMessages = messageService.getAllMessagesByAccountId(account_id);
        ctx.json(userMessages);
    }

}