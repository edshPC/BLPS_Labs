package edsh.blps.security;

import edsh.blps.entity.User;
import edsh.blps.service.UserService;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.util.Map;

public class JaasLoginModule implements LoginModule {

    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, ?> sharedState;
    private Map<String, ?> options;
    private boolean success;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
    }

    @Override
    public boolean login() throws LoginException {

        try {
            NameCallback nameCallback = new NameCallback("username");
            PasswordCallback passwordCallback = new PasswordCallback("password", false);
            callbackHandler.handle(new Callback[]{nameCallback, passwordCallback});

            String username = nameCallback.getName();
            char[] password = passwordCallback.getPassword();

            UserService userService = BeanProvider.getBean(UserService.class);
            User user = userService.findByUsername(username);

            System.out.println(username + " " + new String(password) + " " + user.getTelephone());

            // Здесь можно добавить логику для проверки пользователя и пароля
            // Например, проверка в базе данных или файле

            success = false;
            return success;
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }
    }

    @Override
    public boolean commit() throws LoginException {
        if (success) {
            // Здесь можно добавить логику для добавления принципалов в Subject
            // Например, добавление ролей
        }
        return success;
    }

    @Override
    public boolean abort() throws LoginException {
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        return true;
    }
}
