package edsh.blps.camunda;

import edsh.blps.config.BeanProvider;
import edsh.blps.dto.OrderDTO;
import edsh.blps.entity.primary.User;
import edsh.blps.service.OrderService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CreateOrderTask implements JavaDelegate {
    private final OrderService orderService;

    public CreateOrderTask() {
        orderService = BeanProvider.getBean(OrderService.class);
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var orderDTO = (OrderDTO) delegateExecution.getVariable("orderDTO");
        var user = (User) delegateExecution.getVariable("user");
        var result = orderService.createOrder(orderDTO, user);
        delegateExecution.setVariable("newPayment", result);
    }
}
