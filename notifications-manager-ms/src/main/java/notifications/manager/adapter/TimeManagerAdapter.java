package notifications.manager.adapter;

import notifications.manager.business.port.TimeManagerPort;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class TimeManagerAdapter implements TimeManagerPort {
    @Override
    public ZonedDateTime now() {
        return ZonedDateTime.now();
    }
}
