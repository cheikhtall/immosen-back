package sn.dev.ct.immosen.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.dev.ct.immosen.dto.NotificationDto;
import sn.dev.ct.immosen.entity.Mensualite;
import sn.dev.ct.immosen.entity.Notification;
import sn.dev.ct.immosen.entity.Utilisateur;
import sn.dev.ct.immosen.exception.ResourceNotFoundException;
import sn.dev.ct.immosen.mapper.NotificationMapper;
import sn.dev.ct.immosen.repository.MensualiteRepository;
import sn.dev.ct.immosen.repository.NotificationRepository;
import sn.dev.ct.immosen.service.NotificationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final MensualiteRepository mensualiteRepository;
    @Override
    public void notifierRetards() {
        List<Mensualite> retards = mensualiteRepository.findMensualitesEnRetard();
        for(Mensualite m : retards){
            if(m.getDerniereNotification() != null && m.getDerniereNotification().isEqual(LocalDate.now())){
                continue;
            }
            Utilisateur locataire = m.getContrat().getLocataire();
            double restant = m.getMontantDu() - m.getMontantPaye();

            String message = String.format(
                    "⚠️ Retard de paiement (%d/%d) : restant %.2f FCFA",
                    m.getMois(),
                    m.getAnnee(),
                    restant
            );
            Notification notif = Notification.builder()
                    .message(message)
                    .date(LocalDateTime.now())
                    .utilisateur(locataire)
                    .build();
            notificationRepository.save(notif);
            m.setDerniereNotification(LocalDate.now());
            //Send notification mail ici.
            mensualiteRepository.save(m);
        }
    }

    @Override
    public List<NotificationDto> getByUser(Long userId) {
        return notificationRepository.findByUtilisateurIdOrderByDateDesc(userId)
                .stream()
                .map(NotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void marquerCommeLue(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                () -> new ResourceNotFoundException("Notification", notificationId)
        );
        notification.setLue(true);
        notificationRepository.save(notification);
    }
}
