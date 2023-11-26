package miwm.job4me.security;

import miwm.job4me.emails.EMailService;
import miwm.job4me.model.users.Person;
import miwm.job4me.services.users.UserAuthenticationService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import java.util.UUID;

import static miwm.job4me.messages.AppMessages.BACKEND_HOST_AZURE;
import static miwm.job4me.messages.AppMessages.REGISTRATION_URL;
import static miwm.job4me.messages.EmailMessages.confirmRegistrationEmailSubject;
import static miwm.job4me.messages.EmailMessages.confirmRegistrationEmailText;

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
        Person person = event.getPerson();
        String token = UUID.randomUUID().toString();
        userAuthenticationService.createVerificationToken(person, token);
        String recipientAddress = person.getEmail();
        String subject = confirmRegistrationEmailSubject();
        String confirmationUrl = event.getAppUrl() + REGISTRATION_URL + token;
        String text = confirmRegistrationEmailText() + BACKEND_HOST_AZURE + confirmationUrl;

        emailService.sendSimpleMessage(recipientAddress, subject, text);
    }
}
