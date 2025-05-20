package edsh.blps.camunda;

import edsh.blps.config.BeanProvider;
import edsh.blps.controller.MessageSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CalculateTask implements JavaDelegate {
    private final MessageSender messageSender;

    public CalculateTask() {
        messageSender = BeanProvider.getBean(MessageSender.class);
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        var address = (String) delegateExecution.getVariable("address");
        var result = messageSender.sendCalculator(address);
        delegateExecution.setVariable("price", result);
    }

}
