package edsh.blps.camunda;

import edsh.blps.config.BeanProvider;
import edsh.blps.dto.OrderDTO;
import edsh.blps.dto.UserDTO;
import edsh.blps.entity.primary.User;
import edsh.blps.service.OrderService;
import edsh.blps.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AuthTask implements JavaDelegate {

    private final UserService userService;

    public AuthTask() {
        userService = BeanProvider.getBean(UserService.class);
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var username = (String) delegateExecution.getVariable("username");
        var password = (String) delegateExecution.getVariable("password");
        UserDTO userDTO = new UserDTO(username,password,null);
        try {
            var result = userService.login(userDTO);
            delegateExecution.setVariable("auth", true);
            delegateExecution.setVariable("user", result);
        } catch (Exception e){
            delegateExecution.setVariable("auth", false);
        }
    }
}
