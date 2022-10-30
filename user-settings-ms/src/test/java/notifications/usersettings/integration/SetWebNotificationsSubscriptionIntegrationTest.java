package notifications.usersettings.integration;

import nl.martijndwars.webpush.Subscription;
import notifications.usersettings.business.entity.UserSettings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static java.lang.String.*;
import static notifications.usersettings.util.TestUtils.buildAppUrl;
import static notifications.usersettings.util.TestUtils.buildWebNotificationSubscription;

public class SetWebNotificationsSubscriptionIntegrationTest extends AbstractIntegrationTests {

    @Test
    @DisplayName("Should save web notification subscription")
    void shouldSaveWebNotificationSubscription() {
        final String userId = "caio";
        final String url = buildAppUrl(format("v1/user-settings/%s/notifications/web/subscription", userId), port);
        final Subscription subscription = buildWebNotificationSubscription();
        this.http.put(url, subscription);
        final String getUrl = buildAppUrl("v1/user-settings/caio", port);
        final UserSettings userSettings = this.http.getForEntity(getUrl, UserSettings.class).getBody();
        assert userSettings != null;
        Assertions.assertEquals(subscription.endpoint, userSettings.getWebNotificationsSubscription().endpoint);
    }

    @Test
    @DisplayName("Should save opt in notifications")
    void shouldSaveOptInNotifications() {
        final String userId = "caio";
        final String url = buildAppUrl(format("v1/user-settings/%s/notifications/opt-in", userId), port);
        this.http.postForObject(url, null, Object.class);
        final String getUrl = buildAppUrl(format("v1/user-settings/%s", userId), port);
        final UserSettings userSettings = this.http.getForEntity(getUrl, UserSettings.class).getBody();
        assert userSettings != null;
        Assertions.assertTrue(userSettings.getOptInNotifications());
    }

    @Test
    @DisplayName("Should save opt out notifications")
    void shouldSaveOptOutNotifications() {
        final String userId = "caio";
        final String url = buildAppUrl(format("v1/user-settings/%s/notifications/opt-out", userId), port);
        this.http.postForObject(url, null, Object.class);
        final String getUrl = buildAppUrl(format("v1/user-settings/%s", userId), port);
        final UserSettings userSettings = this.http.getForEntity(getUrl, UserSettings.class).getBody();
        assert userSettings != null;
        Assertions.assertFalse(userSettings.getOptInNotifications());
    }

    @Test
    @DisplayName("Should not find settings of unknown users")
    void shouldNotFindSettingsOfUnknownUsers() {
        final String userId = UUID.randomUUID().toString();
        final String getUrl = buildAppUrl(format("v1/user-settings/%s", userId), port);
        final ResponseEntity<UserSettings> userSettings = this.http.getForEntity(getUrl, UserSettings.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, userSettings.getStatusCode());
    }
}
