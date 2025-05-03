package edsh.blps.service;

import edsh.blps.dto.NewPaymentDTO;
import edsh.blps.entity.primary.Order;
import edsh.blps.entity.secondary.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JTAConfirmService {
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory primaryEntityManagerFactory;
    @Setter(onMethod_ = {@Autowired, @Qualifier("secondaryEntityManagerFactory")})
    private EntityManagerFactory secondaryEntityManagerFactory;
    private final YookassaService yookassaService;

    private final ConcurrentHashMap<Long, CompletableFuture<Payment>> paymentCompletionMap = new ConcurrentHashMap<>();

    @Async
    @SneakyThrows
    public void createOrder(Order order, CompletableFuture<NewPaymentDTO> newPaymentAwait) {
        EntityManager primaryEntityManager = primaryEntityManagerFactory.createEntityManager();
        EntityManager secondaryEntityManager = secondaryEntityManagerFactory.createEntityManager();

        var status = transactionManager.getTransaction(null);
        try {
            primaryEntityManager.joinTransaction();
            secondaryEntityManager.joinTransaction();

            if (order.getDopInformation() != null) {
                primaryEntityManager.persist(order.getDopInformation());
            }
            primaryEntityManager.persist(order);
            System.out.println(order);

            var newPayment = yookassaService.createNewPaymentFor(order);
            newPaymentAwait.complete(newPayment);

            CompletableFuture<Payment> paymentFuture = new CompletableFuture<>();
            paymentCompletionMap.put(order.getId(), paymentFuture);

            var payment = paymentFuture.get(10, TimeUnit.MINUTES);
            if (!payment.getPaid()) throw new RuntimeException("Payment cancelled");

            secondaryEntityManager.persist(payment);

            order.setStatus(true);
            primaryEntityManager.persist(order);

            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        } finally {
            primaryEntityManager.close();
            secondaryEntityManager.close();
        }
    }

    public void onPaymentUpdate(Payment payment) {
        CompletableFuture<Payment> future = paymentCompletionMap.remove(payment.getOrderId());
        if (future == null) throw new RuntimeException("Order not found");
        future.complete(payment);
    }

}
