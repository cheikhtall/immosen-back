package sn.dev.ct.immosen.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.dev.ct.immosen.dto.NotificationDto;
import sn.dev.ct.immosen.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<NotificationDto>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getByUser(userId));
    }

    @PutMapping("/{id}/lue")
    public ResponseEntity<String> marquerCommeLue(@PathVariable Long id) {
        notificationService.marquerCommeLue(id);
        return ResponseEntity.ok("Notification marquée comme lue");
    }
}
