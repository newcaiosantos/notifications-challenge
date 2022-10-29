package notifications.manager.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notifications.manager.business.entity.Notification;
import notifications.manager.business.entity.NotificationStatus;
import notifications.manager.business.entity.NotificationType;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

import static java.time.ZoneOffset.UTC;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledNotificationEntity {
    @Id
    private String id;
    private String userId;
    private String message;
    private NotificationType type;
    private Instant sendAt;
    private NotificationStatus status;
    private Instant createdAt;

    public static ScheduledNotificationEntity from(final Notification n) {
        if (isNull(n)) return null;
        return new ScheduledNotificationEntity(
                n.getId(),
                n.getUserId(),
                n.getMessage(),
                n.getType(),
                ofNullable(n.getSendAt()).map(Instant::from).orElse(null),
                n.getStatus(),
                ofNullable(n.getCreatedAt()).map(Instant::from).orElse(null)
        );
    }

    public Notification toNotification() {
        return new Notification(id, userId, message, type, sendAt.atZone(UTC), status, createdAt.atZone(UTC));
    }
}
