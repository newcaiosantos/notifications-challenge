package notifications.manager.data;

import notifications.manager.business.entity.NotificationStatus;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.time.Instant;
import java.util.List;

public interface ScheduledNotificationRepository extends CrudRepository<ScheduledNotificationEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ScheduledNotificationEntity> findByStatusAndSendAtLessThanEqual(final NotificationStatus status, final Instant from);
}
