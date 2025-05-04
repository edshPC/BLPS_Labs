package edsh.blps.schedule;

import edsh.blps.dto.NewPaymentDTO;
import edsh.blps.dto.PaymentDTO;
import edsh.blps.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.dynomake.yookassa.Yookassa;
import me.dynomake.yookassa.model.Payment;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class YookassaPollingJob implements Job {

    private final Yookassa yookassa;
    private final OrderService orderService;

    @Override
    @SneakyThrows
    public void execute(JobExecutionContext context) throws JobExecutionException {
        NewPaymentDTO newPayment = (NewPaymentDTO) context.getJobDetail().getJobDataMap().get("data");

        Payment payment = yookassa.getPayment(newPayment.getPaymentId());
        if (payment.getStatus().equals("pending")) return;

        orderService.payForOrder(new PaymentDTO(
                newPayment.getOrderId(),
                newPayment.getPaymentId(),
                newPayment.getAmount(),
                payment.getStatus().equals("succeeded")
        ));

        context.getScheduler().deleteJob(context.getJobDetail().getKey());
    }
}
