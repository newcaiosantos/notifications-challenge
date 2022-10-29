package notifications.usersettings.business.usecase.optoutnotifications;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OptOutNotificationsInput {
    private final String userId;
}
