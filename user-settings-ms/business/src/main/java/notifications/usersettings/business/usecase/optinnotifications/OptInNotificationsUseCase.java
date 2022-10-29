package notifications.usersettings.business.usecase.optinnotifications;

import lombok.AllArgsConstructor;
import notifications.usersettings.business.usecase.UseCase;
import notifications.usersettings.business.entity.UserSettings;
import notifications.usersettings.business.ports.UserSettingsFinderPort;
import notifications.usersettings.business.ports.UserSettingsSaverPort;

@AllArgsConstructor
public class OptInNotificationsUseCase implements UseCase<OptInNotificationsInput, UserSettings> {

    private final UserSettingsFinderPort userSettingsFinder;
    private final UserSettingsSaverPort userSettingsSaver;

    @Override
    public UserSettings run(final OptInNotificationsInput input) {
        final UserSettings userSettings = userSettingsFinder
                .findByUserId(input.getUserId())
                .orElse(new UserSettings(input.getUserId()))
                .withOptInNotifications(true);
        userSettingsSaver.save(userSettings);
        return userSettings;
    }
}
