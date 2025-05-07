package edsh.blps.security;

import lombok.Setter;
import me.dynomake.yookassa.Yookassa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.jaas.JaasAuthenticationCallbackHandler;
import org.springframework.security.authentication.jaas.JaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.JaasNameCallbackHandler;
import org.springframework.security.authentication.jaas.JaasPasswordCallbackHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanProvider {
    @Setter
    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public BeanProvider(ApplicationContext applicationContext) {
        setApplicationContext(applicationContext);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider jaasAuthenticationProvider(JaasNameCallbackHandler jaasNameCallbackHandler,
                                                             JaasPasswordCallbackHandler jaasPasswordCallbackHandler) {
        var provider = new JaasAuthenticationProvider();
        provider.setLoginConfig(new ClassPathResource("jaas.config"));
        provider.setLoginContextName("LoginModule");
        provider.setCallbackHandlers(new JaasAuthenticationCallbackHandler[]
                {jaasNameCallbackHandler, jaasPasswordCallbackHandler});
        return provider;
    }

    @Bean
    public JaasNameCallbackHandler jaasNameCallbackHandler() {
        return new JaasNameCallbackHandler();
    }

    @Bean
    public JaasPasswordCallbackHandler jaasPasswordCallbackHandler() {
        return new JaasPasswordCallbackHandler();
    }

    @Bean
    public Yookassa yookassa(@Value("${yookassa.shopId}") int shopId,
                             @Value("${yookassa.shopKey}") String shopKey) {
        return Yookassa.initialize(shopId, shopKey);
    }

}