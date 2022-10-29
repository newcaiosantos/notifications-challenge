package notifications.manager.adapter;

import lombok.AllArgsConstructor;
import notifications.manager.business.port.UserSettingsFinderPort;
import notifications.manager.client.UserSettingsClient;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserSettingsFinderAdapter implements UserSettingsFinderPort {

    private final UserSettingsClient userSettingsClient;

    @Override
    public boolean optedInToReceiveNotifications(final String userId) {
        return userSettingsClient.getUserSettings(userId).getOptInNotifications();
    }
}
