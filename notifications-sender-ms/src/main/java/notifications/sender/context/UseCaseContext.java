package notifications.sender.context;

import notifications.sender.adapter.WebNotificationSenderAdapter;
import notifications.sender.business.usecase.SendNotificationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseContext {
    @Bean
    SendNotificationUseCase sendNotificationUseCase(final WebNotificationSenderAdapter webNotificationSenderAdapter) {
        return new SendNotificationUseCase(webNotificationSenderAdapter);
    }
}
