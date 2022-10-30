package notifications.usersettings.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Subscription;
import notifications.usersettings.business.entity.UserSettings;
import notifications.usersettings.business.usecase.getusersettings.GetUserSettingsInput;
import notifications.usersettings.business.usecase.getusersettings.GetUserSettingsUseCase;
import notifications.usersettings.business.usecase.optinnotifications.OptInNotificationsInput;
import notifications.usersettings.business.usecase.optinnotifications.OptInNotificationsUseCase;
import notifications.usersettings.business.usecase.optoutnotifications.OptOutNotificationsInput;
import notifications.usersettings.business.usecase.optoutnotifications.OptOutNotificationsUseCase;
import notifications.usersettings.business.usecase.setwebnotificationssubscription.SetWebNotificationsSubscriptionInput;
import notifications.usersettings.business.usecase.setwebnotificationssubscription.SetWebNotificationsSubscriptionUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/user-settings")
@AllArgsConstructor
@Slf4j
public class UserSettingsController {

    private final GetUserSettingsUseCase getUserSettingsUseCase;
    private final OptInNotificationsUseCase optInNotificationsUseCase;
    private final OptOutNotificationsUseCase optOutNotificationsUseCase;
    private final SetWebNotificationsSubscriptionUseCase setWebNotificationsSubscriptionUseCase;

    @GetMapping("/{userId}")
    ResponseEntity<UserSettings> getUserSettings(@PathVariable(value = "userId") final String userId) {
        final GetUserSettingsInput input = new GetUserSettingsInput(userId);
        log.info("finding user settings with input {}...", input);
        final Optional<UserSettings> userSettings = getUserSettingsUseCase.run(new GetUserSettingsInput(userId));
        userSettings.ifPresentOrElse(
                it -> log.info("user settings with input {} were found", input),
                () -> log.warn("user settings with input {} were not found", input));
        return userSettings
                .map(it -> new ResponseEntity<>(it, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{userId}/notifications/opt-in")
    void optInNotifications(@PathVariable(value = "userId") final String userId) {
        final OptInNotificationsInput input = new OptInNotificationsInput(userId);
        log.info("opt in notifications with input {}...", input);
        optInNotificationsUseCase.run(input);
        log.info("opt in notifications with input {} was done", input);
    }

    @PostMapping("/{userId}/notifications/opt-out")
    void optOutNotifications(@PathVariable(value = "userId") final String userId) {
        final OptOutNotificationsInput input = new OptOutNotificationsInput(userId);
        log.info("opt out notifications with input {}...", input);
        optOutNotificationsUseCase.run(input);
        log.info("opt out notifications with input {} was done", input);
    }

    @PutMapping("/{userId}/notifications/web/subscription")
    void setWebNotificationsSubscription(
            @PathVariable(value = "userId") final String userId,
            @RequestBody final Subscription subscription
    ) {
        final var input = new SetWebNotificationsSubscriptionInput(userId, subscription);
        log.info("setting web notifications subscription with input {}...", input);
        setWebNotificationsSubscriptionUseCase.run(input);
        log.info("web notifications subscription was set with input {}", input);
    }
}
