package miwm.job4me.emails;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

import static miwm.job4me.messages.AppMessages.*;

public class EMailConfig {

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(MAIL_HOST);
        mailSender.setPort(MAIL_PORT);

        mailSender.setUsername(MAIL_USERNAME);
        mailSender.setPassword(MAIL_PASSWORD_PLACEHOLDER);

        Properties props = mailSender.getJavaMailProperties();
        props.put(MAIL_PROTOCOL_PARAM, MAIL_PROTOCOL);
        props.put(MAIL_AUTH_PARAM, MAIL_OPTION_ENABLE);
        props.put(MAIL_SMTP_PARAM, MAIL_OPTION_ENABLE);
        props.put(MAIL_DEBUG_PARAM, MAIL_OPTION_ENABLE);

        return mailSender;
    }
}
