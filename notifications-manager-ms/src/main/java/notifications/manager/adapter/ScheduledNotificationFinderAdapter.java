package notifications.manager.adapter;

import lombok.AllArgsConstructor;
import notifications.manager.business.entity.Notification;
import notifications.manager.business.entity.NotificationStatus;
import notifications.manager.business.port.ScheduledNotificationFinderPort;
import notifications.manager.data.ScheduledNotificationEntity;
import notifications.manager.data.ScheduledNotificationRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ScheduledNotificationFinderAdapter implements ScheduledNotificationFinderPort {

    private final ScheduledNotificationRepository scheduledNotificationRepository;

    @Override
    public List<Notification> findCreatedToBeSentFrom(final ZonedDateTime from, final long limit) {

        return scheduledNotificationRepository
                .findByStatusAndSendAtLessThanEqual(NotificationStatus.CREATED, Instant.from(from))
                .stream()
                .map(ScheduledNotificationEntity::toNotification)
                .collect(Collectors.toList());

    }
}
