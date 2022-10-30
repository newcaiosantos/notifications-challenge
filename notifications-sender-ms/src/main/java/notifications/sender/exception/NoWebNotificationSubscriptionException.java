package notifications.sender.exception;

public class NoWebNotificationSubscriptionException extends RuntimeException{
    public NoWebNotificationSubscriptionException(){
        super("no web notification subscription");
    }
}
