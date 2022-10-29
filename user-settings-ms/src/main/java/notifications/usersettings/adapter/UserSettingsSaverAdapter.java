package notifications.usersettings.adapter;

import lombok.AllArgsConstructor;
import notifications.usersettings.business.entity.UserSettings;
import notifications.usersettings.business.ports.UserSettingsSaverPort;
import notifications.usersettings.data.document.UserSettingsDocument;
import notifications.usersettings.data.repository.UserSettingsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserSettingsSaverAdapter implements UserSettingsSaverPort {

    private final UserSettingsRepository userSettingsRepository;

    @Override
    public void save(final UserSettings userSettings) {
        Optional.ofNullable(userSettings)
                .map(UserSettingsDocument::from)
                .map(userSettingsRepository::save)
                .orElseThrow(IllegalArgumentException::new);
    }
}
