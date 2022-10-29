package notifications.manager.adapter;

import notifications.manager.business.entity.Notification;
import notifications.manager.business.port.NotificationPublisherPort;
import notifications.manager.serialization.DataMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotificationPublisherAdapter implements NotificationPublisherPort {

    private final RabbitTemplate rabbitTemplate;
    private final String queue;
    private final DataMapper dataMapper;

    public NotificationPublisherAdapter(
            final RabbitTemplate rabbitTemplate,
            @Value("${app.notifications.queue}") final String queue,
            final DataMapper dataMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.dataMapper = dataMapper;
    }

    @Override
    public void publish(final Notification notification) {
        rabbitTemplate.convertAndSend(queue, dataMapper.serialize(notification));
    }
}

