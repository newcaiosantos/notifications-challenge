package notifications.usersettings.business.entity;


import lombok.*;
import nl.martijndwars.webpush.Subscription;

@AllArgsConstructor
@Getter
@With
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
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
