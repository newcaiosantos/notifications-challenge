package notifications.manager.business.exception;

public class MustScheduleForTheFutureException extends RuntimeException {
    public MustScheduleForTheFutureException() {
        super("must schedule for the future");
    }
}
