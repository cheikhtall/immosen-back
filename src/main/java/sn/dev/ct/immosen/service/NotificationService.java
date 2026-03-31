package sn.dev.ct.immosen.service;

import sn.dev.ct.immosen.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    void notifierRetards();
    List<NotificationDto> getByUser(Long userId);
    void marquerCommeLue(Long notificationId);
}
