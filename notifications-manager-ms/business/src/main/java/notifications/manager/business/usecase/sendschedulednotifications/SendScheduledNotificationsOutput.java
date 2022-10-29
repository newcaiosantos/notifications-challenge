package notifications.manager.business.usecase.sendschedulednotifications;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SendScheduledNotificationsOutput {
    private final long sent;
    private final long canceled;
}