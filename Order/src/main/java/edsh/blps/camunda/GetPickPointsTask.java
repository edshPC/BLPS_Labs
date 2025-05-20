package edsh.blps.camunda;

import com.fasterxml.jackson.databind.ObjectMapper;
import edsh.blps.config.BeanProvider;
import edsh.blps.controller.MessageSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.camunda.spin.impl.SpinListImpl;

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
        var str = objectMapper.writeValueAsString(array);
        var list = Spin.JSON(str);
        System.out.println(list);
        delegateExecution.setVariable("pickpoints", list);
    }
}
