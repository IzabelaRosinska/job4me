package miwm.job4me.model.token;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miwm.job4me.model.BaseEntity;
import miwm.job4me.model.users.Employee;
import miwm.job4me.model.users.Employer;
import miwm.job4me.model.users.Organizer;
import miwm.job4me.model.users.Account;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import static miwm.job4me.messages.AppMessages.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class VerificationToken extends BaseEntity {

    private static final int EXPIRATION = 60 * 24;
    private String token;

    @Builder
    public VerificationToken(Long id, String token, Account account) {
        super(id);
        this.token = token;
        if(account.getUserRole().getAuthority().equals(ROLE_EMPLOYEE)) {
            this.employee = (Employee) account;
        } else if(account.getUserRole().getAuthority().equals(ROLE_EMPLOYER)) {
            this.employer = (Employer) account;
        } else if(account.getUserRole().getAuthority().equals(ROLE_ORGANIZER)) {
            this.organizer = (Organizer) account;
        }
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    @OneToOne(targetEntity = Employee.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(targetEntity = Employer.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @OneToOne(targetEntity = Organizer.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    private Date expiryDate;

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
