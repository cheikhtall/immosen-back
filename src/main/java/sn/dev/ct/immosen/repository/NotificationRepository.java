package sn.dev.ct.immosen.repository;

import sn.dev.ct.immosen.entity.Notification;

import java.util.List;

public interface NotificationRepository extends BaseRepository<Notification>{
    List<Notification> findByUtilisateurIdOrderByDateDesc(Long userId);
}
