package notifications.manager.controller;

import lombok.AllArgsConstructor;
import notifications.manager.business.entity.Notification;
import notifications.manager.business.usecase.schedulenotification.ScheduleNotificationInput;
import notifications.manager.business.usecase.schedulenotification.ScheduleNotificationUseCase;
import notifications.manager.business.usecase.sendnotification.SendNotificationInput;
import notifications.manager.business.usecase.sendnotification.SendNotificationUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notification")
@AllArgsConstructor
public class NotificationController {

    private final SendNotificationUseCase sendNotificationUseCase;
    private final ScheduleNotificationUseCase scheduleNotificationUseCase;

    @PostMapping
    ResponseEntity<Notification> sendNotification(@RequestBody final SendNotificationInput input) {
        return new ResponseEntity<>(sendNotificationUseCase.run(input), HttpStatus.CREATED);
    }

    @PostMapping
    ResponseEntity<Notification> scheduleNotification(@RequestBody final ScheduleNotificationInput input) {
        return new ResponseEntity<>(scheduleNotificationUseCase.run(input), HttpStatus.CREATED);
    }
}
