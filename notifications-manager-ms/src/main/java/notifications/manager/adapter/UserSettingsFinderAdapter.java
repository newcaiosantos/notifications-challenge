package notifications.manager.adapter;

import lombok.AllArgsConstructor;
import notifications.manager.business.port.UserSettingsFinderPort;
import notifications.manager.client.UserSettingsClient;
import notifications.manager.client.UserSettingsPayload;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserSettingsFinderAdapter implements UserSettingsFinderPort {

    private final UserSettingsClient userSettingsClient;

    @Override
    public boolean optedInToReceiveNotifications(final String userId) {
        return Optional.ofNullable(userSettingsClient.getUserSettings(userId))
                .map(UserSettingsPayload::getOptInNotifications)
                .orElse(false);
    }
}
