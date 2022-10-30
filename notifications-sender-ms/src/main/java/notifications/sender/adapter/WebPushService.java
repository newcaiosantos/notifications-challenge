package notifications.sender.adapter;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import notifications.sender.business.entity.Notification;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

@Service
@AllArgsConstructor
public class WebPushService {

    private final PushService pushService;

    @SneakyThrows
    public void send(final Notification notification, final Subscription webNotificationSubscription) {
        pushService.send(buildWebNotification(notification, webNotificationSubscription));
    }

    private nl.martijndwars.webpush.Notification buildWebNotification(final Notification notification, final Subscription webNotificationSubscription) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        return new nl.martijndwars.webpush.Notification(webNotificationSubscription, notification.getMessage());
    }
}
