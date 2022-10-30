package notifications.manager.scheduler;

import lombok.extern.slf4j.Slf4j;
import notifications.manager.business.usecase.sendschedulednotifications.SendScheduledNotificationsInput;
import notifications.manager.business.usecase.sendschedulednotifications.SendScheduledNotificationsOutput;
import notifications.manager.business.usecase.sendschedulednotifications.SendScheduledNotificationsUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationScheduler {

    private final SendScheduledNotificationsUseCase sendScheduledNotificationsUseCase;
    private final long maxNotifications;

    public NotificationScheduler(
            final SendScheduledNotificationsUseCase sendScheduledNotificationsUseCase,
            @Value("${app.notifications.scheduler.max-notifications-per-iteration}") final long maxNotifications
    ) {
        this.sendScheduledNotificationsUseCase = sendScheduledNotificationsUseCase;
        this.maxNotifications = maxNotifications;
    }

    @Scheduled(cron = "${app.notifications.scheduler.cron}")
    public void sendScheduledNotifications() {
        final var input = new SendScheduledNotificationsInput(maxNotifications);
        log.info("sending scheduled notifications with input {}...", input);
        final SendScheduledNotificationsOutput output = sendScheduledNotificationsUseCase.run(input);
        log.info("send scheduled notifications use case output: {}, input: {}", output, input);
    }
}
