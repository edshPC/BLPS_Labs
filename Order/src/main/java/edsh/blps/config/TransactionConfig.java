package edsh.blps.config;

import jakarta.annotation.Resource;
import jakarta.transaction.UserTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@RequiredArgsConstructor
public class TransactionConfig  {
    @Resource(lookup = "java:jboss/UserTransaction") // JNDI UserTransaction WildFly
    private UserTransaction userTransaction;

    @Bean
    public UserTransaction userTransaction() {
        return userTransaction;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JtaTransactionManager transactionManager = new JtaTransactionManager();
        transactionManager.setUserTransaction(userTransaction);
        return transactionManager;
    }

}
