package notifications.sender.business.port;

import notifications.sender.business.entity.Notification;

public interface NotificationSenderPort {
    void send(final Notification notification);
}
