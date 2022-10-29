package notifications.manager.business.port;

import notifications.manager.business.entity.Notification;

public interface NotificationPublisherPort {
    void publish(final Notification notification);
}
