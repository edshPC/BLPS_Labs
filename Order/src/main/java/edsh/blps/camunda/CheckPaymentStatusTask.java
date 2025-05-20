package edsh.blps.camunda;

import edsh.blps.config.BeanProvider;
import edsh.blps.dto.NewPaymentDTO;
import edsh.blps.dto.PaymentDTO;
import edsh.blps.service.OrderService;
import edsh.blps.service.YookassaService;
import me.dynomake.yookassa.model.Payment;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CheckPaymentStatusTask implements JavaDelegate {
    private final OrderService orderService;
    private final YookassaService yookassaService;

    public CheckPaymentStatusTask() {
        orderService = BeanProvider.getBean(OrderService.class);
        yookassaService = BeanProvider.getBean(YookassaService.class);
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var newPayment = (NewPaymentDTO) delegateExecution.getVariable("newPayment");

        Payment payment = yookassaService.getPayment(newPayment.getPaymentId());
        delegateExecution.setVariable("status", payment.getStatus());
        if (payment.getStatus().equals("pending")) return;

        orderService.payForOrder(new PaymentDTO(
                newPayment.getOrderId(),
                newPayment.getPaymentId(),
                newPayment.getAmount(),
                payment.getStatus().equals("succeeded")
        ));

    }
}
