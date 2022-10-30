package notifications.sender.business.exception;

import notifications.sender.business.entity.NotificationType;

import static java.lang.String.format;

public class MissingNotificationSenderException extends RuntimeException {
    public MissingNotificationSenderException(final NotificationType notificationType) {
        super(format("missing notification sender for type %s", notificationType.toString()));
    }
}
