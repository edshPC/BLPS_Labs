package edsh.blps.camunda;

import edsh.blps.config.BeanProvider;
import edsh.blps.dto.DopInformationDTO;
import edsh.blps.dto.OrderDTO;
import edsh.blps.entity.primary.DeliveryMethod;
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
        var method = (String) delegateExecution.getVariable("method");
        var orderDTO = OrderDTO.builder()
                .address((String) delegateExecution.getVariable("address"))
                .deliveryMethod(DeliveryMethod.valueOf(method))
                .build();
        if (orderDTO.getDeliveryMethod() == DeliveryMethod.courier) {
            orderDTO.setDopInformationDTO(DopInformationDTO.builder()
                    .flat((String) delegateExecution.getVariable("flat"))
                    .floor((String) delegateExecution.getVariable("floor"))
                    .comment_to_the_courier((String) delegateExecution.getVariable("comment_to_the_courier"))
                    .entrance((String) delegateExecution.getVariable("entrance"))
                    .intercom_system((String) delegateExecution.getVariable("intercom_system"))
                    .build());
        }

        var user = (User) delegateExecution.getVariable("user");
        var result = orderService.createOrder(orderDTO, user);
        delegateExecution.setVariable("newPayment", result);
        delegateExecution.setVariable("link", result.getPaymentUrl());
    }
}
