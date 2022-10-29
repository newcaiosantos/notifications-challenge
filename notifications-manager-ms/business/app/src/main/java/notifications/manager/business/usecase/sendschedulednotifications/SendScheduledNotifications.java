package notifications.manager.business.usecase.sendschedulednotifications;

import lombok.AllArgsConstructor;
import notifications.manager.business.entity.Notification;
import notifications.manager.business.entity.NotificationStatus;
import notifications.manager.business.port.*;
import notifications.manager.business.usecase.UseCase;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
public class SendScheduledNotifications implements UseCase<SendScheduledNotificationsInput, SendScheduledNotificationsOutput> {

    private final ScheduledNotificationFinderPort scheduledNotificationFinder;
    private final TimeManagerPort timeManager;
    private final NotificationPublisherPort notificationPublisher;
    private final ScheduledNotificationSaverPort scheduledNotificationSaver;
    private final UserSettingsFinderPort userSettingsFinder;

    @Override
    public SendScheduledNotificationsOutput run(final SendScheduledNotificationsInput input) {
        final ZonedDateTime now = timeManager.now();
        final List<Notification> scheduled = scheduledNotificationFinder
                .findCreatedToBeSentFrom(now, input.getMaxNotifications())
                .stream()
                .map(it -> {
                    final boolean optedIn = userSettingsFinder.optedInToReceiveNotifications(it.getUserId());
                    return it.withStatus(optedIn ? NotificationStatus.SENT : NotificationStatus.CANCELED);
                })
                .toList();

        scheduled.stream()
                .peek(scheduledNotificationSaver::save)
                .filter(it -> NotificationStatus.SENT.equals(it.getStatus()))
                .forEach(notificationPublisher::publish);

        return new SendScheduledNotificationsOutput(
                scheduled.stream().filter(it -> NotificationStatus.SENT.equals(it.getStatus())).count(),
                scheduled.stream().filter(it -> NotificationStatus.CANCELED.equals(it.getStatus())).count()
        );
    }
}
