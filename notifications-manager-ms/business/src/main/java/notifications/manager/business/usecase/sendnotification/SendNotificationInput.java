package notifications.manager.business.usecase.sendnotification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import notifications.manager.business.entity.NotificationType;

@AllArgsConstructor
@Getter
public class SendNotificationInput {
    private final String userId;
    private final String message;
    private final NotificationType type;
}
