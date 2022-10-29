package notifications.manager.business.usecase.schedulenotification;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notifications.manager.business.entity.NotificationType;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ScheduleNotificationInput {
    private final String userId;
    private final NotificationType notificationType;
    private final String message;
    private final ZonedDateTime sendAt;
}
