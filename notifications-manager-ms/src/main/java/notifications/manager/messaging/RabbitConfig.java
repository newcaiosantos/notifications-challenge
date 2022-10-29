package notifications.manager.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    Queue notificationsQueue(@Value("${app.notifications.queue}") final String queueName) {
        return new Queue(queueName, false);
    }
}
