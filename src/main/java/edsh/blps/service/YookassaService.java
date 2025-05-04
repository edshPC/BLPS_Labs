package edsh.blps.service;

import edsh.blps.dto.NewPaymentDTO;
import edsh.blps.entity.primary.Order;
import edsh.blps.schedule.YookassaPollingJob;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.dynomake.yookassa.Yookassa;
import me.dynomake.yookassa.exception.BadRequestException;
import me.dynomake.yookassa.exception.UnspecifiedShopInformation;
import me.dynomake.yookassa.model.Amount;
import me.dynomake.yookassa.model.Confirmation;
import me.dynomake.yookassa.model.Payment;
import me.dynomake.yookassa.model.request.PaymentRequest;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class YookassaService {
    private final Scheduler scheduler;
    private final Yookassa yookassa;

    public NewPaymentDTO createNewPaymentFor(Order order) throws UnspecifiedShopInformation, BadRequestException, IOException {
        double price = order.getDopInformation() != null ? order.getDopInformation().getPrice() : 100;
        Amount amount = new Amount(Double.toString(price), "RUB");
        Payment payment = yookassa.createPayment(PaymentRequest.builder()
                .description("Payment for order #" + order.getId())
                .amount(amount)
                .confirmation(Confirmation.builder()
                        .type("redirect")
                        .returnUrl("")
                        .build())
                .build());

        var newPayment = new NewPaymentDTO(order.getId(),
                price,
                payment.getId(),
                payment.getConfirmation().getConfirmationUrl());
        startPaymentPolling(newPayment);
        return newPayment;
    }

    @SneakyThrows
    public void startPaymentPolling(NewPaymentDTO newPayment) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("data", newPayment);

        JobDetail jobDetail = JobBuilder.newJob(YookassaPollingJob.class)
                .withIdentity("paymentPollingJob_" + newPayment.getPaymentId())
                .usingJobData(jobDataMap)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("paymentPollingTrigger_" + newPayment.getPaymentId())
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

}
