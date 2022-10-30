package notifications.sender.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class SendNotificationDlqListener {

    @RabbitListener(queues = "${app.notifications.dlq}")
    void dlqListener(final String message) {
        log.info("dlq message: {}", message);
    }
}
