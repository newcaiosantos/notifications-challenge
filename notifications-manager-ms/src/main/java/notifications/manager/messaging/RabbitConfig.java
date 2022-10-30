package notifications.manager.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String DLX_MESSAGES_EXCHANGE = "DLX.MESSAGES.EXCHANGE";

    @Bean
    Queue notificationsQueue(@Value("${app.notifications.queue}") final String queueName) {
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", DLX_MESSAGES_EXCHANGE)
                .build();
    }
}
