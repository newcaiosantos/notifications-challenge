package notifications.manager.business.usecase.sendnotification;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import notifications.manager.business.entity.NotificationType;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class SendNotificationInput {
    private final String userId;
    private final String message;
    private final NotificationType type;
}
