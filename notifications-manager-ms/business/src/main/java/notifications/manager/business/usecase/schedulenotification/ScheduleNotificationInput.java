package notifications.manager.business.usecase.schedulenotification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import notifications.manager.business.entity.NotificationType;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class ScheduleNotificationInput {
    private final String userId;
    private final NotificationType notificationType;
    private final String message;
    private final ZonedDateTime sendAt;
}
