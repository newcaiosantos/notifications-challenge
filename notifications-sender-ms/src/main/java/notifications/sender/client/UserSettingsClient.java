package notifications.sender.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${app.user-settings.feign-client.name}", url = "${app.user-settings.feign-client.url}")
public interface UserSettingsClient {
    @RequestMapping(method = RequestMethod.GET, value = "/v1/user-settings/{userId}", consumes = "application/json")
    UserSettingsPayload getUserSettings(@PathVariable("userId") final String userId);
}