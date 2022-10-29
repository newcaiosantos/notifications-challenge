package notifications.manager.business.port;

import notifications.manager.business.entity.Notification;

public interface ScheduledNotificationSaverPort {
    void save(final Notification notification);
}
