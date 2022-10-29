package notifications.usersettings.business.usecase;

public interface UseCase<I, O> {
    O run(final I input);
}
