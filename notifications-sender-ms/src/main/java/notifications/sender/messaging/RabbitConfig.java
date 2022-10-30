package notifications.sender.messaging;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String DLX_MESSAGES_EXCHANGE = "DLX.MESSAGES.EXCHANGE";
    public static final String MESSAGES_EXCHANGE = "MESSAGES.EXCHANGE";
    public static final String ROUTING_KEY_MESSAGES_QUEUE = "ROUTING_KEY_MESSAGES_QUEUE";

    @Bean
    Queue notificationsQueue(@Value("${app.notifications.queue}") final String notificationsQueue) {
        return QueueBuilder
                .durable(notificationsQueue)
                .withArgument("x-dead-letter-exchange", DLX_MESSAGES_EXCHANGE)
                .build();
    }

    @Bean
    Binding notificationsQueueBinding(@Qualifier("notificationsQueue") final Queue notificationsQueue) {
        return BindingBuilder
                .bind(notificationsQueue)
                .to(notificationsQueueExchange())
                .with(ROUTING_KEY_MESSAGES_QUEUE);
    }

    @Bean
    DirectExchange notificationsQueueExchange() {
        return new DirectExchange(MESSAGES_EXCHANGE);
    }

    @Bean
    Queue notificationsDlq(@Value("${app.notifications.dlq}") final String notificationsDlq) {
        return QueueBuilder.durable(notificationsDlq).build();
    }

    @Bean
    Binding notificationsDlqBinding(@Qualifier("notificationsDlq") final Queue notificationsDlq) {
        return BindingBuilder.bind(notificationsDlq).to(notificationsDlqExchange());
    }

    @Bean
    FanoutExchange notificationsDlqExchange() {
        return new FanoutExchange(DLX_MESSAGES_EXCHANGE);
    }
}
