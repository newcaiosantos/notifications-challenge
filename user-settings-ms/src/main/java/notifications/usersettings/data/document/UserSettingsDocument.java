package notifications.usersettings.data.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import notifications.usersettings.business.entity.UserSettings;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static java.util.Objects.isNull;

@Getter
@AllArgsConstructor
@Document(collection = "userSettings")
public class UserSettingsDocument {
    @Id
    private final String userId;
    private final Boolean optInNotifications;
    private final String webNotificationsSubscription;

    public static UserSettingsDocument from(final UserSettings userSettings) {
        if (isNull(userSettings)) return null;
        return new UserSettingsDocument(
                userSettings.getUserId(),
                userSettings.getOptInNotifications(),
                userSettings.getWebNotificationsSubscription());
    }

    public UserSettings toUserSettings() {
        return new UserSettings(userId, optInNotifications, webNotificationsSubscription);
    }
}
