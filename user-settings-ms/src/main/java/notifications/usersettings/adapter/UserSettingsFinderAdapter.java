package notifications.usersettings.adapter;

import lombok.AllArgsConstructor;
import notifications.usersettings.business.entity.UserSettings;
import notifications.usersettings.business.ports.UserSettingsFinderPort;
import notifications.usersettings.data.document.UserSettingsDocument;
import notifications.usersettings.data.repository.UserSettingsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserSettingsFinderAdapter implements UserSettingsFinderPort {

    private final UserSettingsRepository userSettingsRepository;

    @Override
    public Optional<UserSettings> findByUserId(final String userId) {
        return userSettingsRepository
                .findById(userId)
                .map(UserSettingsDocument::toUserSettings);
    }
}
