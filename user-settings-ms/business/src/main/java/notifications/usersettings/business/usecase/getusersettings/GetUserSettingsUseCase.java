package notifications.usersettings.business.usecase.getusersettings;

import lombok.AllArgsConstructor;
import notifications.usersettings.business.entity.UserSettings;
import notifications.usersettings.business.ports.UserSettingsFinderPort;
import notifications.usersettings.business.usecase.UseCase;

import java.util.Optional;

@AllArgsConstructor
public class GetUserSettingsUseCase implements UseCase<GetUserSettingsInput, Optional<UserSettings>> {

    private final UserSettingsFinderPort userSettingsFinder;

    @Override
    public Optional<UserSettings> run(final GetUserSettingsInput input) {
        return userSettingsFinder.findByUserId(input.getUserId());
    }
}
