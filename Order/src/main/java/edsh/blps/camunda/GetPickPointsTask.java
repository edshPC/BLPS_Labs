package edsh.blps.camunda;

import com.fasterxml.jackson.databind.ObjectMapper;
import edsh.blps.config.BeanProvider;
import edsh.blps.controller.MessageSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class GetPickPointsTask implements JavaDelegate {
    private final ObjectMapper objectMapper;
    private final MessageSender messageSender;

    public GetPickPointsTask() {
        messageSender = BeanProvider.getBean(MessageSender.class);
        objectMapper = BeanProvider.getBean(ObjectMapper.class);
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var result = messageSender.getAllPickPoint();
        var array = result.stream().map(pick -> pick.getAddress().getAddress()).toList();
        delegateExecution.setVariable("pickpoints", objectMapper.writeValueAsString(array));
    }
}
