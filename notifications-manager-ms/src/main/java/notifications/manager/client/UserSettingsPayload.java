package notifications.manager.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserSettingsPayload {
    private String userId;
    private Boolean optInNotifications;
    private String webNotificationsSubscription;
}
