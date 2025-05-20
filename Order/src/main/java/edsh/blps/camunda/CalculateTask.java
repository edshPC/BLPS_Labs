package edsh.blps.camunda;

import com.fasterxml.jackson.databind.ObjectMapper;
import edsh.blps.config.BeanProvider;
import edsh.blps.controller.MessageSender;
import edsh.blps.dto.OrderDTO;
import edsh.blps.entity.primary.User;
import edsh.blps.service.OrderService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CalculateTask implements JavaDelegate {
    private final ObjectMapper objectMapper;
    private final MessageSender messageSender;

    public CalculateTask() {
        messageSender = BeanProvider.getBean(MessageSender.class);
        objectMapper = BeanProvider.getBean(ObjectMapper.class);
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        var address = (String) delegateExecution.getVariable("address");
        var result = messageSender.sendCalculator(address);
        delegateExecution.setVariable("price", result);
    }

}
