package notifications.sender.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notifications.sender.business.entity.Notification;
import notifications.sender.business.usecase.SendNotificationUseCase;
import notifications.sender.serialization.DataMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class SendNotificationListener {
    private final SendNotificationUseCase sendNotificationUseCase;
    private final DataMapper dataMapper;

    @RabbitListener(queues = "${app.notifications.queue}")
    public void sendNotification(final String message) {
        log.info("sending notification: {}", message);
        final Notification notification = dataMapper.deserialize(message, Notification.class);
        sendNotificationUseCase.run(notification);
        log.info("notification was sent: {}", message);
    }
}
