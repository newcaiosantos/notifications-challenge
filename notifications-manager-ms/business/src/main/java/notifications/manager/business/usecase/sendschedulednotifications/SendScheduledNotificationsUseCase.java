package notifications.manager.business.usecase.sendschedulednotifications;

import lombok.AllArgsConstructor;
import notifications.manager.business.entity.Notification;
import notifications.manager.business.entity.NotificationStatus;
import notifications.manager.business.port.*;
import notifications.manager.business.usecase.UseCase;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
public class SendScheduledNotificationsUseCase implements UseCase<SendScheduledNotificationsInput, SendScheduledNotificationsOutput> {

    private final ScheduledNotificationFinderPort scheduledNotificationFinder;
    private final TimeManagerPort timeManager;
    private final NotificationPublisherPort notificationPublisher;
    private final ScheduledNotificationSaverPort scheduledNotificationSaver;
    private final UserSettingsFinderPort userSettingsFinder;
    private final TransactionalRunnerPort<List<Notification>> transactionalRunner;

    @Override
    public SendScheduledNotificationsOutput run(final SendScheduledNotificationsInput input) {
        final ZonedDateTime now = timeManager.now();
        List<Notification> scheduled = transactionalRunner.run(() -> {
            List<Notification> notifications = scheduledNotificationFinder
                    .findCreatedToBeSentFrom(now, input.getMaxNotifications())
                    .stream()
                    .map(it -> {
                        final boolean optedIn = userSettingsFinder.optedInToReceiveNotifications(it.getUserId());
                        return it.withStatus(optedIn ? NotificationStatus.SENT : NotificationStatus.CANCELED);
                    })
                    .toList();
            notifications.stream()
                    .peek(scheduledNotificationSaver::save)
                    .filter(it -> NotificationStatus.SENT.equals(it.getStatus()))
                    .forEach(notificationPublisher::publish);
            return notifications;
        });
        return new SendScheduledNotificationsOutput(
                scheduled.stream().filter(it -> NotificationStatus.SENT.equals(it.getStatus())).count(),
                scheduled.stream().filter(it -> NotificationStatus.CANCELED.equals(it.getStatus())).count()
        );
    }
}
