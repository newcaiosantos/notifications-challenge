package notifications.manager.business.usecase.sendnotification;

import lombok.AllArgsConstructor;
import notifications.manager.business.entity.Notification;
import notifications.manager.business.entity.NotificationStatus;
import notifications.manager.business.exception.NoOptInForNotificationsException;
import notifications.manager.business.port.NotificationPublisherPort;
import notifications.manager.business.port.TimeManagerPort;
import notifications.manager.business.port.UserSettingsFinderPort;
import notifications.manager.business.usecase.UseCase;

import java.time.ZonedDateTime;
import java.util.UUID;

@AllArgsConstructor
public class SendNotificationUseCase implements UseCase<SendNotificationInput, Notification> {

    private final NotificationPublisherPort notificationPublisher;
    private final UserSettingsFinderPort userSettingsFinder;
    private final TimeManagerPort timeManager;

    @Override
    public Notification run(final SendNotificationInput input) {
        if (!userSettingsFinder.optedInToReceiveNotifications(input.getUserId())) {
            throw new NoOptInForNotificationsException();
        }
        final ZonedDateTime now = timeManager.now();
        final Notification notification = new Notification(
                UUID.randomUUID().toString(),
                input.getUserId(),
                input.getMessage(),
                input.getType(),
                now,
                NotificationStatus.SENT,
                now
        );
        notificationPublisher.publish(notification);
        return notification;
    }
}
