package notifications.manager.data;

import notifications.manager.business.entity.NotificationStatus;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

public interface ScheduledNotificationRepository extends CrudRepository<ScheduledNotificationEntity, String> {
    List<ScheduledNotificationEntity> findByStatusAndSendAtLessThanEqual(final NotificationStatus status, final Instant from);
}
