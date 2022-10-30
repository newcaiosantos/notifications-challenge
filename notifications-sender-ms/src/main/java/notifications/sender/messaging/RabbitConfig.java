package notifications.sender.messaging;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String DLX_MESSAGES_EXCHANGE = "DLX.MESSAGES.EXCHANGE";
    public static final String DLQ_MESSAGES_QUEUE = "notifications-dlq";
    public static final String MESSAGES_QUEUE = "notifications-queue";
    public static final String MESSAGES_EXCHANGE = "MESSAGES.EXCHANGE";
    public static final String ROUTING_KEY_MESSAGES_QUEUE = "ROUTING_KEY_MESSAGES_QUEUE";


    @Bean
    Queue messagesQueue() {
        return QueueBuilder.durable(MESSAGES_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_MESSAGES_EXCHANGE)
                .build();
    }

    @Bean
    DirectExchange messagesExchange() {
        return new DirectExchange(MESSAGES_EXCHANGE);
    }

    @Bean
    Binding bindingMessages() {
        return BindingBuilder.bind(messagesQueue()).to(messagesExchange()).with(ROUTING_KEY_MESSAGES_QUEUE);
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange(DLX_MESSAGES_EXCHANGE);
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(DLQ_MESSAGES_QUEUE).build();
    }

    @Bean
    Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }



//    @Bean
//    Queue notificationsQueue(
//            @Value("${app.notifications.queue}") final String queueName,
//            @Value("${app.notifications.dlq}") final String dlqName
//    ) {
//        return QueueBuilder
//                .nonDurable(queueName)
//                .deadLetterExchange("")
//                .deadLetterRoutingKey(dlqName)
//                .build();
//    }
//
//    @Bean
//    Queue notificationsDlq(
//            @Value("${app.notifications.dlq}") final String dlqName
//    ) {
//        return QueueBuilder
//                .nonDurable(dlqName)
//                .build();
//    }
}
