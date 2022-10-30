package notifications.sender.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.martijndwars.webpush.Subscription;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserSettingsPayload {
    private String userId;
    private Boolean optInNotifications;
    private Subscription webNotificationsSubscription;
}
