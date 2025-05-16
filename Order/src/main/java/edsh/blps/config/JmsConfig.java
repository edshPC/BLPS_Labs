package edsh.blps.config;

import jakarta.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import jakarta.jms.ConnectionFactory;

@Configuration
@EnableJms
public class JmsConfig  {

    @Bean
    public ConnectionFactory connectionFactory() {
        return new org.apache.activemq.ActiveMQConnectionFactory("tcp://localhost:61616");
    }
    @Bean
    public Queue requestCalculator() {
        return new ActiveMQQueue("request.calculator");
    }

    @Bean
    public Queue responseCalculator() {
        return new ActiveMQQueue("response.calculator");
    }
    @Bean
    public Queue requestPickPoint() {
        return new ActiveMQQueue("request.PickPoint");
    }

    @Bean
    public Queue responsePickPoint() {
        return new ActiveMQQueue("response.PickPoint");
    }
    @Bean
    public DefaultJmsListenerContainerFactory myFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }
}
