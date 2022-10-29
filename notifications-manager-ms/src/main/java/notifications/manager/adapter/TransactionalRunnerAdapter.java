package notifications.manager.adapter;

import notifications.manager.business.port.TransactionalRunnerPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Component
public class TransactionalRunnerAdapter<T> implements TransactionalRunnerPort<T> {
    @Override
    @Transactional
    public T run(Supplier<T> supplier) {
        return supplier.get();
    }
}
