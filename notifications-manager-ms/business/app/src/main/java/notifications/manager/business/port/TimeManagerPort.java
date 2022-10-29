package notifications.manager.business.port;

import java.time.ZonedDateTime;

public interface TimeManagerPort {
    ZonedDateTime now();
}
