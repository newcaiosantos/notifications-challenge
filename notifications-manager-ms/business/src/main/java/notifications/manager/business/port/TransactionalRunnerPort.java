package notifications.manager.business.port;

import java.util.function.Supplier;

public interface TransactionalRunnerPort<O> {
    O run(Supplier<O> supplier);
}
