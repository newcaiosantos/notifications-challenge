package notifications.manager.business.port;

import notifications.manager.business.entity.Notification;

import java.time.ZonedDateTime;
import java.util.List;

public interface ScheduledNotificationFinderPort {
    List<Notification> findCreatedToBeSentFrom(final ZonedDateTime from, final long limit);
}
