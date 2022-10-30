package notifications.sender.integration;

import nl.martijndwars.webpush.PushService;
import notifications.sender.adapter.WebPushService;
import notifications.sender.business.entity.Notification;
import notifications.sender.business.entity.NotificationStatus;
import notifications.sender.business.entity.NotificationType;
import notifications.sender.business.exception.MissingNotificationSenderException;
import notifications.sender.exception.NoWebNotificationSubscriptionException;
import notifications.sender.listener.SendNotificationListener;
import notifications.sender.serialization.DataMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.ZonedDateTime;
import java.util.UUID;

import static java.lang.String.format;
import static notifications.sender.business.entity.NotificationType.PUSH;
import static notifications.sender.business.entity.NotificationType.WEB;
import static notifications.sender.util.TestUtils.buildUserSettingsResponse;
import static notifications.sender.util.TestUtils.buildUserSettingsWithoutSubscriptionResponse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class SendNotificationIntegrationTest extends AbstractIntegrationTests {

    @MockBean
    PushService pushServiceMock;

    @MockBean
    WebPushService webPushServiceMock;

    @Autowired
    SendNotificationListener sendNotificationListener;

    @Autowired
    DataMapper dataMapper;

    @Test
    @DisplayName("Given user that has web notifications subscription, notifications should be successfully sent to him/her")
    void sendNotificationTest() {
        final String userId = UUID.randomUUID().toString();
        mockServer.when(request().withMethod("GET").withPath(format("/v1/user-settings/%s", userId))).respond(response().withStatusCode(200).withHeaders(new Header("Content-Type", "application/json; charset=utf-8")).withBody(buildUserSettingsResponse(userId)));
        final String notificationId = UUID.randomUUID().toString();
        final String message = "hi caio";
        final ZonedDateTime sendAt = ZonedDateTime.now().minusMinutes(1);
        final NotificationStatus status = NotificationStatus.CREATED;
        final ZonedDateTime createdAt = ZonedDateTime.now();
        final var notification = new Notification(notificationId, userId, message, WEB, sendAt, status, createdAt);
        sendNotificationListener.sendNotification(dataMapper.serialize(notification));
    }

    @Test
    @DisplayName("Given user without web notifications subscription, an exception should be thrown when is tried to notify him/her")
    void sendNotificationWithoutWebNotificationsSubscriptionTest() {
        final String userId = UUID.randomUUID().toString();
        mockServer.when(request().withMethod("GET").withPath(format("/v1/user-settings/%s", userId))).respond(response().withStatusCode(200).withHeaders(new Header("Content-Type", "application/json; charset=utf-8")).withBody(buildUserSettingsWithoutSubscriptionResponse(userId)));
        final String notificationId = UUID.randomUUID().toString();
        final String message = "hi caio";
        final ZonedDateTime sendAt = ZonedDateTime.now().minusMinutes(1);
        final NotificationStatus status = NotificationStatus.CREATED;
        final ZonedDateTime createdAt = ZonedDateTime.now();
        final var notification = new Notification(notificationId, userId, message, WEB, sendAt, status, createdAt);
        final NoWebNotificationSubscriptionException thrown = assertThrows(
                NoWebNotificationSubscriptionException.class,
                () -> sendNotificationListener.sendNotification(dataMapper.serialize(notification))
        );
        assertTrue(thrown.getMessage().contains("no web notification subscription"));
    }

    @Test
    @DisplayName("Given unknown notification type, an exception should be thrown informing it")
    void unknownNotificationTypeTest() {
        final String userId = UUID.randomUUID().toString();
        mockServer.when(request().withMethod("GET").withPath(format("/v1/user-settings/%s", userId))).respond(response().withStatusCode(200).withHeaders(new Header("Content-Type", "application/json; charset=utf-8")).withBody(buildUserSettingsResponse(userId)));
        final String notificationId = UUID.randomUUID().toString();
        final String message = "hi caio";
        final NotificationType type = PUSH;
        final ZonedDateTime sendAt = ZonedDateTime.now().minusMinutes(1);
        final NotificationStatus status = NotificationStatus.CREATED;
        final ZonedDateTime createdAt = ZonedDateTime.now();
        final var notification = new Notification(notificationId, userId, message, type, sendAt, status, createdAt);
        final MissingNotificationSenderException thrown = assertThrows(
                MissingNotificationSenderException.class,
                () -> sendNotificationListener.sendNotification(dataMapper.serialize(notification))
        );
        assertTrue(thrown.getMessage().contains(String.format("missing notification sender for type %s", type)));
    }
}
