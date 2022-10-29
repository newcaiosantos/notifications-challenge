package notifications.usersettings.business.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

@AllArgsConstructor
@Getter
@With
public class UserSettings {
    private final String userId;
    private final Boolean optInNotifications;
    private final String webNotificationsSubscription;

    public UserSettings(final String userId) {
        this.userId = userId;
        this.optInNotifications = null;
        this.webNotificationsSubscription = null;
    }
}
