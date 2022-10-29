package notifications.usersettings.business.ports;

import notifications.usersettings.business.entity.UserSettings;

public interface UserSettingsSaverPort {
    void save(final UserSettings userSettings);
}
