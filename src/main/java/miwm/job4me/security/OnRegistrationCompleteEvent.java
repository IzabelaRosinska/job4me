package miwm.job4me.security;

import lombok.Getter;
import lombok.Setter;
import miwm.job4me.model.users.Employee;
import org.springframework.context.ApplicationEvent;
import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private Employee employee;

    public OnRegistrationCompleteEvent(Employee employee, Locale locale, String appUrl) {
        super(employee);

        this.employee = employee;
        this.locale = locale;
        this.appUrl = appUrl;
    }

}
