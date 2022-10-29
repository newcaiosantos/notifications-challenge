package notifications.manager.business.usecase.schedulenotification;

import lombok.AllArgsConstructor;
import notifications.manager.business.entity.Notification;
import notifications.manager.business.entity.NotificationStatus;
import notifications.manager.business.exception.MustScheduleForTheFutureException;
import notifications.manager.business.exception.NoOptInForNotificationsException;
import notifications.manager.business.port.ScheduledNotificationSaverPort;
import notifications.manager.business.port.TimeManagerPort;
import notifications.manager.business.port.UserSettingsFinderPort;
import notifications.manager.business.usecase.UseCase;

import java.time.ZonedDateTime;
import java.util.UUID;

@AllArgsConstructor
public class ScheduleNotificationUseCase implements UseCase<ScheduleNotificationInput, Notification> {

    private final ScheduledNotificationSaverPort scheduledNotificationSaver;
    private final UserSettingsFinderPort userSettingsFinder;
    private final TimeManagerPort timeManager;

    @Override
    public Notification run(final ScheduleNotificationInput input) {
        final ZonedDateTime now = timeManager.now();
        if (!input.getSendAt().isAfter(now)) throw new MustScheduleForTheFutureException();
        if (!userSettingsFinder.optedInToReceiveNotifications(input.getUserId())) {
            throw new NoOptInForNotificationsException();
        }
        final Notification notification = new Notification(
                UUID.randomUUID().toString(),
                input.getUserId(),
                input.getMessage(),
                input.getNotificationType(),
                input.getSendAt(),
                NotificationStatus.CREATED,
                now
        );
        scheduledNotificationSaver.save(notification);
        return notification;
    }
}
