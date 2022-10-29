package notifications.usersettings.business.usecase.getusersettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class GetUserSettingsInput {
    private final String userId;
}
