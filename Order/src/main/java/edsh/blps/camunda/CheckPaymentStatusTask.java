package edsh.blps.camunda;

import edsh.blps.config.BeanProvider;
import edsh.blps.dto.NewPaymentDTO;
import edsh.blps.dto.PaymentDTO;
import edsh.blps.service.OrderService;
import jakarta.annotation.Resource;
import jakarta.resource.cci.ConnectionFactory;
import me.dynomake.yookassa.Yookassa;
import me.dynomake.yookassa.model.Payment;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CheckPaymentStatusTask implements JavaDelegate {
    private final OrderService orderService;
    @Resource(name = "eis/YookassaConnectionFactory")
    private ConnectionFactory yookassaConnectionFactory;

    public CheckPaymentStatusTask() {
        orderService = BeanProvider.getBean(OrderService.class);
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Yookassa yookassa = (Yookassa) yookassaConnectionFactory.getConnection();
        var newPayment = (NewPaymentDTO) delegateExecution.getVariable("newPayment");

        Payment payment = yookassa.getPayment(newPayment.getPaymentId());
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
