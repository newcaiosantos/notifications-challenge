package notifications.manager.business.usecase.sendschedulednotifications;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SendScheduledNotificationsInput {
    private final long maxNotifications;
}
