package notifications.sender.adapter;

import lombok.AllArgsConstructor;
import nl.martijndwars.webpush.Subscription;
import notifications.sender.business.entity.Notification;
import notifications.sender.business.port.WebNotificationSenderPort;
import notifications.sender.client.UserSettingsClient;
import notifications.sender.exception.NoWebNotificationSubscriptionException;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@AllArgsConstructor
public class WebNotificationSenderAdapter implements WebNotificationSenderPort {

    private final UserSettingsClient userSettingsClient;
    private final WebPushService webPushService;

    @Override
    public void send(final Notification notification) {
        final Subscription webNotificationSubscription = userSettingsClient
                .getUserSettings(notification.getUserId())
                .getWebNotificationsSubscription();
        if (isNull(webNotificationSubscription)) throw new NoWebNotificationSubscriptionException();
        webPushService.send(notification, webNotificationSubscription);
    }
}
