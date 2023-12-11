package miwm.job4me.emails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

import static miwm.job4me.messages.AppMessages.MAIL_NO_REPLY;

@Component
public class EMailService {
    @Autowired
    private JavaMailSender emailSender;

    private final TemplateEngine templateEngine;

    public EMailService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(MAIL_NO_REPLY);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendHtmlMessageWithTemplate(String to, String subject, String text) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

            try {
                helper.setFrom(MAIL_NO_REPLY);
                helper.setTo(to);
                helper.setSubject(subject);

                Context context = new Context();
                context.setVariables(Map.of("subject", subject));
                context.setVariables(Map.of("emailText", text));

                String htmlContent = templateEngine.process("standardEmail", context);

                helper.setText(htmlContent, true);

                emailSender.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (MessagingException e) {
            sendSimpleMessage(to, subject, text);
        }
    }

}
