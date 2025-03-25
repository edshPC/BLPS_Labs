package edsh.blps.security;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.jaas.JaasAuthenticationCallbackHandler;
import org.springframework.security.authentication.jaas.JaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.JaasNameCallbackHandler;
import org.springframework.security.authentication.jaas.JaasPasswordCallbackHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BeanProvider {

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

}
