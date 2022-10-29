package notifications.usersettings.business.usecase.optinnotifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class OptInNotificationsInput {
    private final String userId;
}
