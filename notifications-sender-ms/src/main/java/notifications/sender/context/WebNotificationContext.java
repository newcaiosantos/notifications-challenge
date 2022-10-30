package notifications.sender.context;

import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.GeneralSecurityException;
import java.security.Security;

@Configuration
public class WebNotificationContext {
    @Bean
    PushService pushService(
            @Value("${app.notifications.web.private-key}") final String privateKey,
            @Value("${app.notifications.web.public-key}") final String publicKey
    ) throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        return new PushService(publicKey, privateKey);
    }
}
