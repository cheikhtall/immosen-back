package sn.dev.ct.immosen.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sn.dev.ct.immosen.service.NotificationService;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 9 * * ?")
    public void envoyerNotifications() {
        notificationService.notifierRetards();
    }
}
