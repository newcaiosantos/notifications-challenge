package notifications.usersettings.data.repository;

import notifications.usersettings.data.document.UserSettingsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserSettingsRepository extends MongoRepository<UserSettingsDocument, String> {
}
