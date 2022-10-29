package notifications.manager.business.port;

public interface UserSettingsFinderPort {
    boolean optedInToReceiveNotifications(final String userId);
}
