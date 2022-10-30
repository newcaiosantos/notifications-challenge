package notifications.sender.business.entity;

import lombok.*;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@With
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class Notification {
    private final String id;
    private final String userId;
    private final String message;
    private final NotificationType type;
    private final ZonedDateTime sendAt;
    private final NotificationStatus status;
    private final ZonedDateTime createdAt;
}
