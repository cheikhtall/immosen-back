package sn.dev.ct.immosen.customEvents.event;

import lombok.Getter;
import lombok.Setter;
import sn.dev.ct.immosen.dto.UtilisateurDTO;

@Getter @Setter
public class AccountCreateEvent {
    private final UtilisateurDTO utilisateur;
    private final String rawPassword;

    public AccountCreateEvent(UtilisateurDTO utilisateur, String rawPassword) {
        this.utilisateur = utilisateur;
        this.rawPassword = rawPassword;
    }
}
