package notifications.manager.adapter;

import lombok.AllArgsConstructor;
import notifications.manager.business.entity.Notification;
import notifications.manager.business.port.ScheduledNotificationSaverPort;
import notifications.manager.data.ScheduledNotificationEntity;
import notifications.manager.data.ScheduledNotificationRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ScheduledNotificationsSaverAdapter implements ScheduledNotificationSaverPort {

    private final ScheduledNotificationRepository scheduledNotificationRepository;

    @Override
    public void save(final Notification notification) {
        Optional.ofNullable(notification)
                .map(ScheduledNotificationEntity::from)
                .map(scheduledNotificationRepository::save)
                .orElseThrow(IllegalArgumentException::new);
    }
}
