package notifications.manager.business.exception;

public class NoOptInForNotificationsException extends RuntimeException {
    public NoOptInForNotificationsException() {
        super("no opt-in for notifications");
    }
}
