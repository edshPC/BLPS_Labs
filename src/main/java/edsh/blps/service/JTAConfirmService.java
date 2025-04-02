package edsh.blps.service;

import edsh.blps.dto.ApprovalDTO;
import edsh.blps.dto.OrderDTO;
import edsh.blps.entity.primary.DeliveryMethod;
import edsh.blps.entity.primary.DopInformation;
import edsh.blps.entity.primary.Order;
import edsh.blps.entity.primary.User;
import edsh.blps.entity.secondary.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

@Service
@RequiredArgsConstructor
public class JTAConfirmService {
    private final UserTransaction userTransaction;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory primaryEntityManagerFactory;
    private final ApplicationEventPublisher eventPublisher;
    @Setter(onMethod_ = {@Autowired, @Qualifier("secondaryEntityManagerFactory")})
    private EntityManagerFactory secondaryEntityManagerFactory;

    @SneakyThrows
    public void createOrder(Order order, Payment payment) {
        EntityManager primaryEntityManager = primaryEntityManagerFactory.createEntityManager();
        EntityManager secondaryEntityManager = secondaryEntityManagerFactory.createEntityManager();

        try {
            userTransaction.begin();

            primaryEntityManager.joinTransaction();
            secondaryEntityManager.joinTransaction();

            if (order.getDopInformation() != null) {
                primaryEntityManager.persist(order.getDopInformation());
            }
            primaryEntityManager.persist(order);

            secondaryEntityManager.persist(payment);
            eventPublisher.publishEvent(new Order());
            userTransaction.commit();
        } catch (RuntimeException e) {
            userTransaction.rollback();
            throw e;
        }
    }

}
