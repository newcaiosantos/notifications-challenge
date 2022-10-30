package notifications.sender.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.martijndwars.webpush.Subscription;

@NoArgsConstructor
@Getter
public class UserSettingsPayload {
    private Subscription webNotificationsSubscription;
}
