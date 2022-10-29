package notifications.usersettings.business.usecase.setwebnotificationssubscription;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SetWebNotificationsSubscriptionInput {
    private final String userId;
    private final String webNotificationsSubscription;
}
