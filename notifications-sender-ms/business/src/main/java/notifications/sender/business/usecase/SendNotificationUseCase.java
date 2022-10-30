package notifications.sender.business.usecase;

import notifications.sender.business.entity.Notification;
import notifications.sender.business.entity.NotificationType;
import notifications.sender.business.exception.MissingNotificationSenderException;
import notifications.sender.business.port.NotificationSenderPort;
import notifications.sender.business.port.WebNotificationSenderPort;

import java.util.Map;

import static java.util.Optional.ofNullable;

public class SendNotificationUseCase {

    private final Map<NotificationType, NotificationSenderPort> senders;

    public SendNotificationUseCase(final WebNotificationSenderPort webNotificationSender) {
        this.senders = Map.of(NotificationType.WEB, webNotificationSender);
    }


    public void run(final Notification notification) {
        ofNullable(senders.get(notification.getType())).ifPresentOrElse(
                sender -> sender.send(notification),
                () -> {
                    throw new MissingNotificationSenderException(notification.getType());
                });
    }
}
