package notifications.usersettings.business.usecase.setwebnotificationssubscription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.martijndwars.webpush.Subscription;

@AllArgsConstructor
@Getter
public class SetWebNotificationsSubscriptionInput {
    private final String userId;
    private final Subscription webNotificationsSubscription;
}
