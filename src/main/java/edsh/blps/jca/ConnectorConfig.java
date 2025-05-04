package edsh.blps.jca;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectorConfig {

    @Value("${yookassa.shopId}") private int shopId;
    @Value("${yookassa.shopKey}") private String shopKey;

    @Bean(name = "eis/YookassaConnectionFactory")
    public YookassaConnectionFactory yookassaConnectionFactory() {
        var ymcf = new YookassaManagedConnectionFactory();
        ymcf.setShopId(shopId);
        ymcf.setShopKey(shopKey);
        return new YookassaConnectionFactory(ymcf);
    }

}
