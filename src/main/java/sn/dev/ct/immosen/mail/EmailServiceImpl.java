package sn.dev.ct.immosen.mail;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import sn.dev.ct.immosen.dto.UtilisateurDTO;


@Service @AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendHtmlEmail(EmailRequestDto emailRequestDto) {
        try {
            var mimeMessage = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(emailRequestDto.getTo());
            helper.setSubject(emailRequestDto.getSubject());
            helper.setText(emailRequestDto.getContent(), true);

            mailSender.send(mimeMessage);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendWelcomeEmail(UtilisateurDTO utilisateurDTO, String password) {
        Context context = new Context();
        context.setVariable("username", utilisateurDTO.getUsername());
        context.setVariable("email", utilisateurDTO.getEmail());
        context.setVariable("password", password);

        String html = templateEngine.process("user-created-template", context);

        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setTo(utilisateurDTO.getEmail());
        emailRequestDto.setSubject("Bienvenue sur Immosen");
        emailRequestDto.setContent(html);

        sendHtmlEmail(emailRequestDto);
    }
}
