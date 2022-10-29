package notifications.usersettings.business.ports;

import notifications.usersettings.business.entity.UserSettings;

import java.util.Optional;

public interface UserSettingsFinderPort {
    Optional<UserSettings> findByUserId(final String userId);
}
