package miwm.job4me.security;

import lombok.Getter;
import lombok.Setter;
import miwm.job4me.model.users.Account;
import org.springframework.context.ApplicationEvent;
import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private Account account;

    public OnRegistrationCompleteEvent(Account account, Locale locale, String appUrl) {
        super(account);

        this.account = account;
        this.locale = locale;
        this.appUrl = appUrl;
    }

}
