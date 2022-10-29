package notifications.manager.context;

import notifications.manager.adapter.*;
import notifications.manager.business.usecase.schedulenotification.ScheduleNotificationUseCase;
import notifications.manager.business.usecase.sendnotification.SendNotificationUseCase;
import notifications.manager.business.usecase.sendschedulednotifications.SendScheduledNotificationsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseContext {
    @Bean
    ScheduleNotificationUseCase scheduleNotificationUseCase(
            final ScheduledNotificationsSaverAdapter scheduledNotificationsSaverAdapter,
            final UserSettingsFinderAdapter userSettingsFinderAdapter,
            final TimeManagerAdapter timeManagerAdapter
    ) {
        return new ScheduleNotificationUseCase(
                scheduledNotificationsSaverAdapter,
                userSettingsFinderAdapter,
                timeManagerAdapter
        );
    }

    @Bean
    SendNotificationUseCase sendNotificationUseCase(
            final NotificationPublisherAdapter notificationPublisherAdapter,
            final UserSettingsFinderAdapter userSettingsFinderAdapter,
            final TimeManagerAdapter timeManagerAdapter
    ) {
        return new SendNotificationUseCase(
                notificationPublisherAdapter,
                userSettingsFinderAdapter,
                timeManagerAdapter
        );
    }

    @Bean
    SendScheduledNotificationsUseCase sendScheduledNotifications(
            final ScheduledNotificationFinderAdapter scheduledNotificationFinderAdapter,
            final TimeManagerAdapter timeManagerAdapter,
            final NotificationPublisherAdapter notificationPublisherAdapter,
            final ScheduledNotificationsSaverAdapter scheduledNotificationsSaverAdapter,
            final UserSettingsFinderAdapter userSettingsFinderAdapter
    ) {
        return new SendScheduledNotificationsUseCase(
                scheduledNotificationFinderAdapter,
                timeManagerAdapter,
                notificationPublisherAdapter,
                scheduledNotificationsSaverAdapter,
                userSettingsFinderAdapter
        );
    }
}
