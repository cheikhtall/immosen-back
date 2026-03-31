package sn.dev.ct.immosen.mapper;

import sn.dev.ct.immosen.dto.NotificationDto;
import sn.dev.ct.immosen.entity.Notification;

public class NotificationMapper {

    public static NotificationDto toDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .date(notification.getDate())
                .lue(notification.isLue())
                .build();
    }
}
