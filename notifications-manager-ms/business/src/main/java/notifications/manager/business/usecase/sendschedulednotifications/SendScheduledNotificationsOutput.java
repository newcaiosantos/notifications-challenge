package notifications.manager.business.usecase.sendschedulednotifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class SendScheduledNotificationsOutput {
    private final long sent;
    private final long canceled;
}
