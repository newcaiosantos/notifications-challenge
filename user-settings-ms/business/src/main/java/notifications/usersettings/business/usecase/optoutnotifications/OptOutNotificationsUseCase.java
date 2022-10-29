package notifications.usersettings.business.usecase.optoutnotifications;

import lombok.AllArgsConstructor;
import notifications.usersettings.business.usecase.UseCase;
import notifications.usersettings.business.entity.UserSettings;
import notifications.usersettings.business.ports.UserSettingsFinderPort;
import notifications.usersettings.business.ports.UserSettingsSaverPort;

@AllArgsConstructor
public class OptOutNotificationsUseCase implements UseCase<OptOutNotificationsInput, UserSettings> {

    private final UserSettingsFinderPort userSettingsFinder;
    private final UserSettingsSaverPort userSettingsSaver;

    @Override
    public UserSettings run(final OptOutNotificationsInput input) {
        final UserSettings userSettings = userSettingsFinder
                .findByUserId(input.getUserId())
                .orElse(new UserSettings(input.getUserId()))
                .withOptInNotifications(false);
        userSettingsSaver.save(userSettings);
        return userSettings;
    }
}
