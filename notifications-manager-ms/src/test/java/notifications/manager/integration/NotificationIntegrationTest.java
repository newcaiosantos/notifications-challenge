package notifications.manager.integration;

import notifications.manager.business.entity.Notification;
import notifications.manager.business.entity.NotificationStatus;
import notifications.manager.business.exception.NoOptInForNotificationsException;
import notifications.manager.business.usecase.schedulenotification.ScheduleNotificationInput;
import notifications.manager.business.usecase.sendnotification.SendNotificationInput;
import notifications.manager.data.ScheduledNotificationEntity;
import notifications.manager.data.ScheduledNotificationRepository;
import notifications.manager.errorhandling.Error;
import notifications.manager.scheduler.NotificationScheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;
import static java.time.ZonedDateTime.now;
import static java.util.Objects.requireNonNull;
import static notifications.manager.business.entity.NotificationType.WEB;
import static notifications.manager.util.TestUtils.*;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class NotificationIntegrationTest extends AbstractIntegrationTests {

    @Autowired
    protected NotificationScheduler notificationScheduler;

    @Autowired
    protected ScheduledNotificationRepository scheduledNotificationRepository;

    @Test
    @DisplayName("Should send notification to queue and return it with status SENT")
    void shouldSendNotification() {
        final String userId = UUID.randomUUID().toString();
        mockServer.when(request().withMethod("GET").withPath(format("/v1/user-settings/%s", userId))).respond(response().withStatusCode(200).withHeaders(new Header("Content-Type", "application/json; charset=utf-8")).withBody(buildUserSettingsResponse(userId)));
        final String url = buildAppUrl("v1/notification", port);
        final Notification notification = this.http.postForObject(url, new SendNotificationInput(userId, "hello", WEB), Notification.class);
        Assertions.assertEquals(NotificationStatus.SENT, notification.getStatus());
    }

    @Test
    @DisplayName("Should not send notification when user hasn't opted in")
    void shouldNotSendNotificationWhenUserHasNotOptedIn() {
        final String userId = UUID.randomUUID().toString();
        mockServer.when(request().withMethod("GET").withPath(format("/v1/user-settings/%s", userId))).respond(response().withStatusCode(200).withHeaders(new Header("Content-Type", "application/json; charset=utf-8")).withBody(buildUserSettingsResponseWithoutNotificationOptIn(userId)));
        final String url = buildAppUrl("v1/notification", port);
        final ResponseEntity<Error> error = this.http.postForEntity(url, new SendNotificationInput(userId, "hello", WEB), Error.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
        Assertions.assertEquals(new NoOptInForNotificationsException().getMessage(), requireNonNull(error.getBody()).getMessage());
    }

    @Test
    @DisplayName("Should schedule notification and return it with status CREATED")
    void shouldScheduleNotification() {
        final String userId = "caio";
        mockServer.when(request().withMethod("GET").withPath(format("/v1/user-settings/%s", userId))).respond(response().withStatusCode(200).withHeaders(new Header("Content-Type", "application/json; charset=utf-8")).withBody(buildUserSettingsResponse(userId)));
        final String url = buildAppUrl("v1/notification/scheduled", port);
        final var input = new ScheduleNotificationInput(userId, WEB, "hello", now().plusSeconds(1));
        final Notification notification = this.http.postForObject(url, input, Notification.class);
        Assertions.assertEquals(NotificationStatus.CREATED, notification.getStatus());
    }

    @Test
    @DisplayName("Should not schedule notifications to the past")
    void shouldNotScheduleNotificationsToThePast() {
        final String userId = UUID.randomUUID().toString();
        mockServer.when(request().withMethod("GET").withPath(format("/v1/user-settings/%s", userId))).respond(response().withStatusCode(200).withHeaders(new Header("Content-Type", "application/json; charset=utf-8")).withBody(buildUserSettingsResponse(userId)));
        final String url = buildAppUrl("v1/notification/scheduled", port);
        final var input = new ScheduleNotificationInput(userId, WEB, "hello", now().minusHours(1));
        final ResponseEntity<Error> res = this.http.postForEntity(url, input, Error.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        Assertions.assertEquals("must schedule for the future", requireNonNull(res.getBody()).getMessage());
    }

    @Test
    @DisplayName("Should send scheduled notifications")
    void shouldSendScheduledNotifications() {
        final String userId = UUID.randomUUID().toString();
        mockServer.when(request().withMethod("GET").withPath(format("/v1/user-settings/%s", userId))).respond(response().withStatusCode(200).withHeaders(new Header("Content-Type", "application/json; charset=utf-8")).withBody(buildUserSettingsResponse(userId)));

        final String notificationId = UUID.randomUUID().toString();
        final String message = "hi caio";
        final ZonedDateTime sendAt = ZonedDateTime.now().minusMinutes(1);
        final NotificationStatus status = NotificationStatus.CREATED;
        final ZonedDateTime createdAt = ZonedDateTime.now();
        final var notification = new Notification(notificationId, userId, message, WEB, sendAt, status, createdAt);

        scheduledNotificationRepository.save(requireNonNull(ScheduledNotificationEntity.from(notification)));
        notificationScheduler.sendScheduledNotifications();

        final Optional<Notification> sent = scheduledNotificationRepository
                .findById(notificationId)
                .map(ScheduledNotificationEntity::toNotification);
        Assertions.assertTrue(sent.isPresent());
        Assertions.assertEquals(NotificationStatus.SENT, sent.get().getStatus());
    }
}
