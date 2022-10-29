package notifications.manager.business.usecase.sendschedulednotifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class SendScheduledNotificationsInput {
    private final long maxNotifications;
}
