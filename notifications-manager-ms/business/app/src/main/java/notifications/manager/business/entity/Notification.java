package notifications.manager.business.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@With
public class Notification {
    private final String id;
    private final String userId;
    private final String message;
    private final NotificationType type;
    private final ZonedDateTime sendAt;
    private final NotificationStatus status;
    private final ZonedDateTime createdAt;
}
