package notifications.usersettings.controller.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class SetWebNotificationsPayload {
    private final String webNotificationsSubscription;
}