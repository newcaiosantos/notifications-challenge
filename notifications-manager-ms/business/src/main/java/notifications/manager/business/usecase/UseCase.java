package notifications.manager.business.usecase;

public interface UseCase<I, O> {
    O run(final I input);
}
