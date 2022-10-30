package notifications.usersettings.business.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import nl.martijndwars.webpush.Subscription;

@AllArgsConstructor
@Getter
@With
public class UserSettings {
    private final String userId;
    private final Boolean optInNotifications;
    private final Subscription webNotificationsSubscription;

    public UserSettings(final String userId) {
        this.userId = userId;
        this.optInNotifications = null;
        this.webNotificationsSubscription = null;
    }
}
