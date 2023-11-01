package miwm.job4me.security;

import miwm.job4me.emails.EMailService;
import miwm.job4me.model.users.Employee;
import miwm.job4me.services.users.UserAuthenticationService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {


    private final UserAuthenticationService userAuthenticationService;
    private final MessageSource messages;
    private final EMailService emailService;

    public RegistrationListener(UserAuthenticationService userAuthenticationService, MessageSource messages, EMailService emailService) {
        this.userAuthenticationService = userAuthenticationService;
        this.messages = messages;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Employee employee = event.getEmployee();
        String token = UUID.randomUUID().toString();
        userAuthenticationService.createVerificationToken(employee, token);
        String recipientAddress = employee.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        //String message = messages.getMessage("message.regSucc", null, event.getLocale());
        String text = "Welcome in Job4Me!\n Please click link below to verify your account.\n\n" + "http://localhost:8080" + confirmationUrl;

        emailService.sendSimpleMessage(recipientAddress, subject, text);
    }
}
