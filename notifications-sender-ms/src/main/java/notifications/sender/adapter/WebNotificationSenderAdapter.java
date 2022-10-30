package notifications.sender.adapter;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import notifications.sender.business.entity.Notification;
import notifications.sender.business.port.WebNotificationSenderPort;
import notifications.sender.client.UserSettingsClient;
import notifications.sender.exception.NoWebNotificationSubscriptionException;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import static java.util.Objects.isNull;

@Component
@AllArgsConstructor
public class WebNotificationSenderAdapter implements WebNotificationSenderPort {

    private final UserSettingsClient userSettingsClient;
    private final PushService pushService;

    @Override
    @SneakyThrows
    public void send(final Notification notification) {
        final Subscription webNotificationSubscription = userSettingsClient
                .getUserSettings(notification.getUserId())
                .getWebNotificationsSubscription();
        if (isNull(webNotificationSubscription)) throw new NoWebNotificationSubscriptionException();
        pushService.send(buildWebNotification(notification, webNotificationSubscription));
    }

    private nl.martijndwars.webpush.Notification buildWebNotification(
            final Notification notification,
            final Subscription webNotificationSubscription
    ) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        return new nl.martijndwars.webpush.Notification(webNotificationSubscription, notification.getMessage());
    }
}
