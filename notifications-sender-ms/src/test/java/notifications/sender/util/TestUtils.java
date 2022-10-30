package notifications.sender.util;

import org.testcontainers.utility.DockerImageName;

import static java.lang.String.format;

public class TestUtils {
    public static final DockerImageName RABBITMQ_IMAGE = DockerImageName.parse("rabbitmq:3-management");
    public static final int MOCK_SERVER_PORT = 1080;

    public static String buildUserSettingsResponse(final String userId) {
        return format("""
                {
                	"userId": "%s",
                	"optInNotifications": true,
                	"webNotificationsSubscription": {
                		"endpoint": "https://fcm.googleapis.com/fcm/send/fp7m3Z3uL2Q:APA91bHyjQq8FPVSkaHf2H_PiD_NiXB7Giz1cApsYyGU_x1N1RiiqIc9x81OKTmmCFW7bhgdmtK1BKLjjrKuiUO7ROMrDhNG3bLKmlTOulyV9r9NSwRg1-woDEd97yi5ibkA6FbawwO5",
                		"keys": {
                			"p256dh": "BBbGBeNPxn5Z407N7uS_02KF6XbhCEP4fGloDpA_ZmWgH4Qnm0JZ2BUkR0hs8sJ9U6TaX5NvuT-jjPZs6tuqiX4",
                			"auth": "LcUQczAwe0J1xgZrOdVqEQ"
                		}
                	}
                }""", userId);
    }

    public static String buildUserSettingsWithoutSubscriptionResponse(final String userId) {
        return format("""
                {
                	"userId": "%s",
                	"optInNotifications": true,
                	"webNotificationsSubscription": null
                }""", userId);
    }
}
