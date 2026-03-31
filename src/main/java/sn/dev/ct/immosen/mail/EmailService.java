package sn.dev.ct.immosen.mail;

import sn.dev.ct.immosen.dto.UtilisateurDTO;

public interface EmailService {
    void sendHtmlEmail(EmailRequestDto emailRequestDto);
    void sendWelcomeEmail(UtilisateurDTO utilisateurDTO, String password);
}
