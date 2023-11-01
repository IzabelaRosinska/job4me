package miwm.job4me.security;

import lombok.Getter;
import lombok.Setter;
import miwm.job4me.model.users.Person;
import org.springframework.context.ApplicationEvent;
import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private Person person;

    public OnRegistrationCompleteEvent(Person person, Locale locale, String appUrl) {
        super(person);

        this.person = person;
        this.locale = locale;
        this.appUrl = appUrl;
    }

}
