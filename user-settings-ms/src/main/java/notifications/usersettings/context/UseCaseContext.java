package notifications.usersettings.context;

import notifications.usersettings.adapter.UserSettingsFinderAdapter;
import notifications.usersettings.adapter.UserSettingsSaverAdapter;
import notifications.usersettings.business.usecase.getusersettings.GetUserSettingsUseCase;
import notifications.usersettings.business.usecase.optinnotifications.OptInNotificationsUseCase;
import notifications.usersettings.business.usecase.optoutnotifications.OptOutNotificationsUseCase;
import notifications.usersettings.business.usecase.setwebnotificationssubscription.SetWebNotificationsSubscriptionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseContext {

    @Bean
    GetUserSettingsUseCase getUserSettingsUseCase(final UserSettingsFinderAdapter userSettingsFinderAdapter) {
        return new GetUserSettingsUseCase(userSettingsFinderAdapter);
    }

    @Bean
    OptInNotificationsUseCase optInNotificationsUseCase(
            final UserSettingsFinderAdapter userSettingsFinderAdapter,
            final UserSettingsSaverAdapter userSettingsSaverAdapter
    ) {
        return new OptInNotificationsUseCase(userSettingsFinderAdapter, userSettingsSaverAdapter);
    }

    @Bean
    OptOutNotificationsUseCase optOutNotificationsUseCase(
            final UserSettingsFinderAdapter userSettingsFinderAdapter,
            final UserSettingsSaverAdapter userSettingsSaverAdapter
    ) {
        return new OptOutNotificationsUseCase(userSettingsFinderAdapter, userSettingsSaverAdapter);
    }

    @Bean
    SetWebNotificationsSubscriptionUseCase setWebNotificationsSubscriptionUseCase(
            final UserSettingsFinderAdapter userSettingsFinderAdapter,
            final UserSettingsSaverAdapter userSettingsSaverAdapter
    ) {
        return new SetWebNotificationsSubscriptionUseCase(userSettingsFinderAdapter, userSettingsSaverAdapter);
    }
}
