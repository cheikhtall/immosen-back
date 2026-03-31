package sn.dev.ct.immosen.customEvents.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sn.dev.ct.immosen.customEvents.event.AccountCreateEvent;
import sn.dev.ct.immosen.dto.UtilisateurDTO;
import sn.dev.ct.immosen.mail.EmailService;

@Component
public class AccountCreateEventListener {
    private final EmailService emailService;

    public AccountCreateEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async
    @EventListener
    public void handleUserCreated(AccountCreateEvent event) {
        UtilisateurDTO utilisateur = event.getUtilisateur();
        emailService.sendWelcomeEmail(utilisateur, event.getRawPassword());
    }
}
