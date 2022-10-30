package notifications.usersettings.util;

import nl.martijndwars.webpush.Subscription;
import org.testcontainers.utility.DockerImageName;

import static java.lang.String.format;
import static java.lang.Thread.sleep;

public class TestUtils {
    public static final DockerImageName MONGODB_IMAGE = DockerImageName.parse("mongo:5.0");

    public static String buildAppUrl(final String endpoint, final int port) {
        return format("http://localhost:%s/%s", port, endpoint);
    }

    public static Subscription buildWebNotificationSubscription() {
        return new Subscription(
                "https://fcm.googleapis.com/fcm/send/fp7m3Z3uL2Q:APA91bHyjQq8FPVSkaHf2H_PiD_NiXB7Giz1cApsYyGU_x1N1RiiqIc9x81OKTmmCFW7bhgdmtK1BKLjjrKuiUO7ROMrDhNG3bLKmlTOulyV9r9NSwRg1-woDEd97yi5ibkA6FbawwO5",
                new Subscription.Keys("BBbGBeNPxn5Z407N7uS_02KF6XbhCEP4fGloDpA_ZmWgH4Qnm0JZ2BUkR0hs8sJ9U6TaX5NvuT-jjPZs6tuqiX4", "LcUQczAwe0J1xgZrOdVqEQ")
        );
    }
}
