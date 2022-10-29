package notifications.usersettings.business.usecase.setwebnotificationssubscription;

import lombok.AllArgsConstructor;
import notifications.usersettings.business.usecase.UseCase;
import notifications.usersettings.business.entity.UserSettings;
import notifications.usersettings.business.ports.UserSettingsFinderPort;
import notifications.usersettings.business.ports.UserSettingsSaverPort;

@AllArgsConstructor
public class SetWebNotificationsSubscriptionUseCase implements UseCase<SetWebNotificationsSubscriptionInput, UserSettings> {

    private final UserSettingsFinderPort userSettingsFinder;
    private final UserSettingsSaverPort userSettingsSaver;

    @Override
    public UserSettings run(final SetWebNotificationsSubscriptionInput input) {
        final UserSettings userSettings = userSettingsFinder
                .findByUserId(input.getUserId())
                .orElse(new UserSettings(input.getUserId()))
                .withWebNotificationsSubscription(input.getWebNotificationsSubscription());
        userSettingsSaver.save(userSettings);
        return userSettings;
    }
}
